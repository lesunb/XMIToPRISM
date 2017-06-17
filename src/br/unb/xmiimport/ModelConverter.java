package br.unb.xmiimport;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

	private ModelConverter() {
	}

	public static ModelConverter getInstance() {
		if (instance == null) {
			instance = new ModelConverter();
		}
		return instance;
	}

	protected void convert(String metaModelDefinitionURL, String xmiTransformationURL, String xmiModelFilePath) {
		// read the metamodel
		XMLParser parser = null;
		try {
			parser = new XMLParser();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		MetaModel metaModel = new MetaModel();
		try {
			parser.parse(metaModelDefinitionURL, metaModel.getSAXParserHandler());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// read the XMI transformation file
		XMITransformations transformation = new XMITransformations(metaModel);
		try {
			parser.parse(xmiTransformationURL, transformation.getSAXParserHandler());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// read the XMI file with the UML model
		Model model = new Model(metaModel);
		XMIReader xmiReader = new XMIReader(transformation, model);
		try {
			parser.parse(xmiModelFilePath, xmiReader);
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof FileNotFoundException) {
				// TODO possível com botao de path direto?
				System.out.println("File was not found. Program terminated.");
				System.exit(0);
			}
		}

		// optionally, specify element filters to get rid of standard libraries or 3rd party APIs
		String[] filters = { "#.java", "#.javax", "#.org.xml" };
		model.setFilter(filters, false, true);

		ActivityDiagram ad = null;
		SequenceDiagram sd = null;
		// iterate over each type and in each type iterate over each model element of that type
		for (MetaModelElement type : metaModel) {
			List<ModelElement> elements;

			switch (type.getName().toLowerCase()) {
			// Activity Diagram (AD)
			case "activity":
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					ad = new ActivityDiagram(me.getXMIID(), me.getName());
				}
				break;
			// AD's Executable Action
			case "action":
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					ad.addExecutableNode(new ExecutableNode(me.getXMIID(), ad));
				}
				break;
			// AD's Initial, Decision and Merge nodes.
			case "controlnode":
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {

					switch (me.getPlainAttribute("kind").toLowerCase()) {
					case "initial":
						ad.addInitialNode(new InitialNode(me.getXMIID(), ad));
						break;
					case "activityfinal":
						ad.addFinalNode(new FinalNode(me.getXMIID(), ad));
						break;
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
						// e.printStackTrace();
						System.out.println("Found edge without associated probability or wrong number format. Fix the UML model.");
						System.exit(0);
					}
					cf.setPTS(prob);
					ad.addControlFlow(cf);
				}
				break;
			// Sequence Diagram (SD)
			case "sequence":
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					sd = new SequenceDiagram(me.getXMIID(), me.getName());
				}
				break;
			// SD's Lifeline
			case "lifeline":
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					Lifeline ll = new Lifeline(me.getXMIID(), me.getName(), sd);
					Double prob = null;
					try {
						prob = Double.parseDouble(me.getPlainAttribute("BCompRel"));
					} catch (Exception e) {
						System.out.println("Found Lifeline without associated probability or wrong number format. Fix the UML model.");
						System.exit(0);
					}
					ll.setBCompRel(prob);
					sd.addLifeline(ll);
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

		// transform ADs and/or SD to DTMC in PRISM language
		if (ad != null || sd != null) {
			PRISMModel prismModel = null;
			long startTime = 0;
			long endTime = 0;

			try {
				if (ad != null) {
					// measure time start
					startTime = System.nanoTime();
					// convert
					prismModel = ad.toDTMC().toPRISM();
					// measure time finish
					endTime = System.nanoTime();
				} else if (sd != null) {
					// measure time start
					startTime = System.nanoTime();
					// convert
					prismModel = sd.toDTMC().toPRISM();
					// measure time finish
					endTime = System.nanoTime();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// output on console and file
			printResultOnConsole(prismModel.toString(), getConversionTime(startTime, endTime));
			createOuputFile(prismModel.toString(), xmiModelFilePath);
		}
	}

	private static void createOuputFile(String transformationResult, String xmiModelFilePath) {
		String s1[] = xmiModelFilePath.split("/");
		String s2[] = s1[1].split("\\.");
		Path path = Paths.get("output/".concat(s2[0] + ".pm"));

		// write result to a new file in the output folder
		try (BufferedWriter bw = Files.newBufferedWriter(path)) {
			bw.write(transformationResult);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private long getConversionTime(long startTime, long endTime) {
		return endTime - startTime;
	}

	private void printResultOnConsole(String transformationResult, long conversionTime) {
		System.out.println("Conversion time (ms): " + (conversionTime / 1000000));
		System.out.println(transformationResult);
	}

}
