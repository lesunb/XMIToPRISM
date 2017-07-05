package br.unb.xmiimport;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.sdmetrics.model.MetaModel;
import com.sdmetrics.model.MetaModelElement;
import com.sdmetrics.model.Model;
import com.sdmetrics.model.ModelElement;
import com.sdmetrics.model.XMIReader;
import com.sdmetrics.model.XMITransformations;
import com.sdmetrics.util.XMLParser;

import br.unb.dali.models.agg.uml.ActivityDiagram;
import br.unb.dali.models.agg.uml.SequenceDiagram;
import br.unb.dali.models.agg.uml.ad.edges.ControlFlow;
import br.unb.dali.models.agg.uml.ad.nodes.control.DecisionNode;
import br.unb.dali.models.agg.uml.ad.nodes.control.FinalNode;
import br.unb.dali.models.agg.uml.ad.nodes.control.InitialNode;
import br.unb.dali.models.agg.uml.ad.nodes.control.MergeNode;
import br.unb.dali.models.agg.uml.ad.nodes.executable.ExecutableNode;
import br.unb.dali.models.agg.uml.sd.Lifeline;
import br.unb.dali.util.prism.PRISMModel;

public class ModelConverter {

	private static ModelConverter instance = null;
	private MetaModel metaModel = new MetaModel();
	private Model model = null;
	private XMLParser parser = null;
	private XMIReader xmiReader = null;
	private XMITransformations transformation = null;

	private ActivityDiagram ad = null;
	private SequenceDiagram sd = null;
	private PRISMModel prismModel = null;
	private long startTime = 0;
	private long endTime = 0;

	private ModelConverter() {
	}

	public static ModelConverter getInstance() {
		if (instance == null) {
			instance = new ModelConverter();
		}
		return instance;
	}

	protected void convert(String metaModelDefinitionURL, String xmiTransformationURL, String xmiModelURL) {

		createParser();
		readMetamodelDefinition(metaModelDefinitionURL);
		transformation = new XMITransformations(metaModel);
		readXMITransformation(xmiTransformationURL);
		model = new Model(metaModel);
		xmiReader = new XMIReader(transformation, model);
		readXMIFile(xmiModelURL);

		// optionally, specify element filters to get rid of standard libraries or 3rd party APIs
		String[] filters = { "#.java", "#.javax", "#.org.xml" };
		model.setFilter(filters, false, true);

		iterateOverModel();
		transformToPrism();
		printResultOnConsole(prismModel.toString(), getTimeInMilliseconds(startTime, endTime));
		createOuputFile(prismModel.toString(), xmiModelURL);
	}

	private void createParser() {
		try {
			parser = new XMLParser();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	private void readMetamodelDefinition(String metaModelDefinitionURL) {
		try {
			parser.parse(metaModelDefinitionURL, metaModel.getSAXParserHandler());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void readXMITransformation(String xmiTransformationURL) {
		try {
			parser.parse(xmiTransformationURL, transformation.getSAXParserHandler());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void readXMIFile(String xmiModelURL) {
		try {
			parser.parse(xmiModelURL, xmiReader);
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof FileNotFoundException) {
				System.out.println("File was not found. Program terminated.");
				System.exit(0);
			}
		}
	}

	private void iterateOverModel() {
		// iterate over each type and in each type iterate over each model element of that type
		for (MetaModelElement type : metaModel) {
			List<ModelElement> elements;

			// TODO tirar
			String s1 = type.getName().toLowerCase();
			switch (s1) {
			case "diagram":
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					if(me.getPlainAttribute("type").equals("uml:Activity")) {
						ad = new ActivityDiagram(me.getXMIID(), me.getName());
					} else {
						sd = new SequenceDiagram(me.getXMIID(), me.getName());
					}
				}
				break;

			// AD's Executable Action
			case "action":
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					ad.addExecutableNode(new ExecutableNode(me.getXMIID(), ad));
				}
				break;
			// AD's Initial, Final, Decision and Merge nodes.
			case "node":
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					String s = me.getPlainAttribute("kind").toLowerCase();
					switch (s) {
					case "executable":
					case "uml:opaqueaction":
						ad.addExecutableNode(new ExecutableNode(me.getXMIID(), ad));
						break;
					case "initial":
					case "uml:initialnode":
						ad.addInitialNode(new InitialNode(me.getXMIID(), ad));
						break;
					case "activityfinal":
					case "uml:activityfinalnode":
						ad.addFinalNode(new FinalNode(me.getXMIID(), ad));
						break;

					case "uml:decisionnode":
					case "uml:mergenode":
					case "junction":
						Collection<?> incomingNodes = me.getSetAttribute("incoming");
						// difference between merge and decision nodes is checked with the number of incoming edges
						if (incomingNodes.size() > 1) {
							ad.addMergeNode(new MergeNode(me.getXMIID(), ad));
						} else {
							ad.addDecisionNode(new DecisionNode(me.getXMIID(), ad));
						}
						break;
					default:
						System.out.println("Found ControlNode of unknown kind.");
						break;
					}
				}
				break;
			// AD's Edges
			case "controlflow":
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					ControlFlow cf = new ControlFlow(me.getXMIID(), me.getPlainAttribute("source"), me.getPlainAttribute("target"), ad);
					Double prob = null;
					try {
						prob = Double.parseDouble(me.getPlainAttribute("probability"));
					} catch (Exception e) {
						System.out.println("Found edge without associated probability or wrong number format. Fix the UML model.");
						System.exit(0);
					}
					cf.setPTS(prob);
					ad.addControlFlow(cf);
				}
				break;
			case "lifeline":
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					Lifeline lifeline = new Lifeline(me.getXMIID(), me.getName(), sd);
					Double prob = null;
					try {
						prob = Double.parseDouble(me.getPlainAttribute("BCompRel"));
					} catch (Exception e) {
						System.out.println("Found Lifeline without associated probability or wrong number format. Fix the UML model.");
						System.exit(0);
					}
					lifeline.setBCompRel(prob);
					sd.addLifeline(lifeline);
				}
				break;
			// SD's Asynchronous Message
			case "asynchronousmessage":
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					sd.addAsyncMessage(me.getXMIID(), me.getPlainAttribute("source"), me.getPlainAttribute("target"), "DefaultSignal");
				}
				break;
			default:
				break;
			}
		}
	}

	private void transformToPrism() {
		if (ad != null || sd != null) {
			try {
				if (ad != null) {
					startTime = System.nanoTime();
					prismModel = ad.toDTMC().toPRISM();
					endTime = System.nanoTime();
				} else if (sd != null) {
					startTime = System.nanoTime();
					prismModel = sd.toDTMC().toPRISM();
					endTime = System.nanoTime();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void createOuputFile(String transformationResult, String xmiModelFilePath) {
		String s1[] = xmiModelFilePath.split("/");
		String s2[] = s1[1].split("\\.");
		Path path = Paths.get("output/".concat(s2[0] + ".pm"));

		try (BufferedWriter bw = Files.newBufferedWriter(path)) {
			bw.write(transformationResult);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private double getTimeInMilliseconds(long startTime, long endTime) {
		return (endTime - startTime) / 1000000.0;
	}

	private void printResultOnConsole(String transformationResult, double conversionTime) {
		DecimalFormat numberFormat = new DecimalFormat("#.0");
		System.out.println("Conversion time (ms): " + numberFormat.format(conversionTime));
		System.out.println(transformationResult);
	}

}
