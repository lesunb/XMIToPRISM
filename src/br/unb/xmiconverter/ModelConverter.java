package br.unb.xmiconverter;

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

	private XMLParser parser = null;
	private MetaModel metaModel = new MetaModel();
	private Model model = null;
	private XMITransformations transformation = null;
	private XMIReader xmiReader = null;
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

	protected void convert(String sdmetricsMetaModelPath, String xmiTransformationPath, String xmiModelPath) {
		// Read the metamodel
		readMetamodel(sdmetricsMetaModelPath);

		// Read the XMI transformation file
		transformation = new XMITransformations(metaModel);
		readXMITransformation(xmiTransformationPath);

		// Read the XMI file with the UML model
		model = new Model(metaModel);
		xmiReader = new XMIReader(transformation, model);
		readXMIFile(xmiModelPath);

		// Optionally, specify element filters to get rid of standard libraries or 3rd party APIs
		String[] filters = { "#.java", "#.javax", "#.org.xml" };
		model.setFilter(filters, false, true);

		// Access the UML model, type by type, and construct the diagram with UnB-DALi's classes
		iterateOverModelAndBuildDiagram();

		// Transform UML diagrams to DTMCs in PRISM language
		transformToPrism();

		// Show the results on console and creates an output file on the same folder of the model
		printResultOnConsole(prismModel.toString(), getTimeInMilliseconds(startTime, endTime));
		createOuputFile(prismModel.toString(), xmiModelPath);
	}

	private void readMetamodel(String sdmetricsMetaModelPath) {
		try {
			parser = new XMLParser();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		try {
			parser.parse(sdmetricsMetaModelPath, metaModel.getSAXParserHandler());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void readXMITransformation(String xmiTransformationPath) {
		try {
			parser.parse(xmiTransformationPath, transformation.getSAXParserHandler());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void readXMIFile(String xmiModelPath) {
		try {
			parser.parse(xmiModelPath, xmiReader);
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof FileNotFoundException) {
				System.out.println("File was not found. Program terminated.");
				System.exit(0);
			}
		}
	}

	private void iterateOverModelAndBuildDiagram() {
		for (MetaModelElement type : metaModel) {
			List<ModelElement> elements;

			String elementType = type.getName();
			switch (elementType) {

			// Activity or Sequence diagrams.
			case "diagram":
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					String diagramType = me.getPlainAttribute("type");

					switch (diagramType) {
					case "Activity Diagram":
					case "uml:Activity":
						ad = new ActivityDiagram(me.getXMIID(), me.getName());
						break;
					case "Sequence Diagram":
					case "uml:Interaction":
						sd = new SequenceDiagram(me.getXMIID(), me.getName());
						break;
					default:
						System.out.println("No diagram type found.");
						System.exit(0);
						break;
					}
				}
				break;

			// Activity Diagram's - Executable Action
			case "action":
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					ad.addExecutableNode(new ExecutableNode(me.getXMIID(), ad));
				}
				break;

			// Activity Diagram's - Initial, Final, Decision and Merge nodes.
			case "node":
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					String nodeKind = me.getPlainAttribute("kind");
					switch (nodeKind) {
					case "executable":
					case "uml:OpaqueAction":
						ad.addExecutableNode(new ExecutableNode(me.getXMIID(), ad));
						break;
					case "initial":
					case "uml:InitialNode":
						ad.addInitialNode(new InitialNode(me.getXMIID(), ad));
						break;
					case "activityfinal":
					case "uml:ActivityFinalNode":
						ad.addFinalNode(new FinalNode(me.getXMIID(), ad));
						break;

					case "uml:DecisionNode":
					case "uml:MergeNode":
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
						System.out.println("Found Node of unknown kind.");
						break;
					}
				}
				break;

			// Activity Diagram's - Edges
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

			// Sequence Diagram's - Lifelines
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

			// Sequence Diagram's - Asynchronous Messages
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

	private void createOuputFile(String transformationResult, String xmiModelPath) {
		String outputPath = xmiModelPath.substring(0, xmiModelPath.lastIndexOf('.')) + ".pm";
		Path path = Paths.get(outputPath);

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
