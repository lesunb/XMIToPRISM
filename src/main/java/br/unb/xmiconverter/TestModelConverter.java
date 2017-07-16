package br.unb.xmiconverter;

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

public class TestModelConverter {

	private static TestModelConverter instance = null;
	static String currentPath = System.getProperty("user.dir");

	private XMLParser parser = null;
	private String sdmetricsMetaModelPath = currentPath + "/JAR-File-And-Scripts/configuration-scripts/metamodel-unb_dali.xml";
	private MetaModel metaModel = new MetaModel();
	private Model model = null;
	private XMITransformations transformation = null;
	private XMIReader xmiReader = null;
	private ActivityDiagram ad = null;
	private SequenceDiagram sd = null;
	private PRISMModel prismModel = null;
	private long startTime = 0;
	private long endTime = 0;

	private TestModelConverter() {
	}

	public static TestModelConverter getInstance() {
		if (instance == null) {
			instance = new TestModelConverter();
		}
		return instance;
	}

	/*-
	 * Main method. The one the calls all the other methods necessary to accesss the information on the XMI file in order. The steps are:
	 * 
	 * 1. Read the metamodel
	 * 2. Read the XMI transformation file
	 * 3. Read the XMI file with the UML model
	 * 4. Optionally, specify element filters to get rid of standard libraries or 3rd party APIs
	 * 5. Access the UML model, type by type, and construct the diagram with the support of UnB-DALi's classes
	 * 6. Convert UML diagrams to DTMCs in PRISM language
	 * 7. Show the results on console and creates an output file on the same folder of the model
	 * */
	protected boolean convert(String umlTool, String xmiFile) {
		readMetamodel(sdmetricsMetaModelPath);

		transformation = new XMITransformations(metaModel);
		readTransformation(currentPath + "/JAR-File-And-Scripts/configuration-scripts/transformations/astah-transformations.xml");

		model = new Model(metaModel);
		xmiReader = new XMIReader(transformation, model);
		readXMIFile(xmiFile);

		String[] filters = { "#.java", "#.javax", "#.org.xml" };
		model.setFilter(filters, false, true);

		iterateOverModelAndBuildDiagram();

		boolean conversionSuccess = convertToPrism();

		String transformationResult = prismModel.toString();
		createOuputFile(transformationResult, xmiFile);
		printResultOnConsole(transformationResult, getTimeInMilliseconds(startTime, endTime));

		return conversionSuccess;
	}

	private void readMetamodel(String metaModelPath) {
		try {
			parser = new XMLParser();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		try {
			parser.parse(metaModelPath, metaModel.getSAXParserHandler());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void readTransformation(String xmiTransformationPath) {
		try {
			parser.parse(xmiTransformationPath, transformation.getSAXParserHandler());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void readXMIFile(String xmiFile) {
		try {
			parser.parse(xmiFile, xmiReader);
		} catch (Exception e) {
			e.printStackTrace();
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
				ModelElement diagramElement = elements.get(0);
				String diagramType = diagramElement.getPlainAttribute("type");

				switch (diagramType) {
				case "Activity Diagram":
				case "uml:Activity":
					ad = new ActivityDiagram(diagramElement.getXMIID(), diagramElement.getName());
					break;
				case "Sequence Diagram":
				case "uml:Interaction":
					sd = new SequenceDiagram(diagramElement.getXMIID(), diagramElement.getName());
					break;
				default:
					System.out.println("No diagram type found.");
					break;
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
					}
					if (prob != null) {
						cf.setPTS(prob);
					}
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
					}
					if (prob != null) {
						lifeline.setBCompRel(prob);
					}
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

	private boolean convertToPrism() {
		boolean result = false;
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
				result = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private void createOuputFile(String transformationResult, String xmiFile) {
		FileWriter.writePrismFile(transformationResult, xmiFile);
	}

	private double getTimeInMilliseconds(long startTime, long endTime) {
		return (endTime - startTime) / 1000000.0;
	}

	private void printResultOnConsole(String transformationResult, double conversionTime) {
		MessageController.printString(transformationResult);
		MessageController.printConversionTime(conversionTime);
		MessageController.printCompletionMessage();
	}
}
