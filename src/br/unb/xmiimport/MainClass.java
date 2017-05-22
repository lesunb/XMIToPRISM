package br.unb.xmiimport;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

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

public class MainClass {
	public static void main(String[] args) {

		// metamodel, XMI transformation, and XMI input files
		String metaModelURL = "sdmetrics-metamodel-and-transformation-files/unb-dali-metamodel-elements.xml";
		String xmiTransURL = "sdmetrics-metamodel-and-transformation-files/modeling-tools/astah/transformation-file.xml";
		String file = new String();
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the name of the file inside the \"xmi-files\" folder (without the extension): ");
		file = input.nextLine().concat(".xml");
		input.close();
		String xmiFile = "tests/".concat(file);

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
			parser.parse(metaModelURL, metaModel.getSAXParserHandler());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// read the XMI transformation file
		XMITransformations transformation = new XMITransformations(metaModel);
		try {
			parser.parse(xmiTransURL, transformation.getSAXParserHandler());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// read the XMI file with the UML model
		Model model = new Model(metaModel);
		XMIReader xmiReader = new XMIReader(transformation, model);
		try {
			parser.parse(xmiFile, xmiReader);
		} catch (Exception e) {
			// e.printStackTrace();
			if (e instanceof FileNotFoundException) {
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
			System.out.println("Elements of type: " + type.getName());
			List<ModelElement> elements;
			switch (type.getName().toLowerCase()) {
			// activity diagram (AD)
			case "activity":
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					ad = new ActivityDiagram(me.getXMIID(), me.getName());
					System.out.println("\tAD id [" + ad.getId() + "]\tname [" + ad.getName() + "]");
				}
				break;
			// AD's Executable Action
			case "action":
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					// TODO achar o AD ao qual o elemento se refere
					ad.addExecutableNode(new ExecutableNode(me.getXMIID(), ad));
					System.out.println("\tAction id [" + me.getXMIID() + "]\tname [" + me.getName() + "]");
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
						if (incomingNodes.size() > 1) {
							ad.addMergeNode(new MergeNode(me.getXMIID(), ad));
						} else {
							ad.addDecisionNode(new DecisionNode(me.getXMIID(), ad));

						}
						break;
					default:
						System.out.println("ControlNode of unknown kind.");
						break;
					}
					System.out.println(
							"\tControlNode id [" + me.getXMIID() + "]\tname [" + me.getName() + "]\tkind [" + me.getPlainAttribute("kind") + "]");
				}
				break;
			// AD's Edges
			case "controlflow":
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					// TODO achar o AD ao qual se refere
					ad.addControlFlow(new ControlFlow(me.getXMIID(), me.getPlainAttribute("source"), me.getPlainAttribute("target"), ad)
							.setPTS(Double.parseDouble(me.getPlainAttribute("probability"))));
					System.out.println("\tEdge source [" + me.getPlainAttribute("source") + "] target [" + me.getPlainAttribute("target") + "]");
				}
				break;
			// sequence diagram (SD)
			case "sequence":
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					sd = new SequenceDiagram(me.getXMIID(), me.getName());
					// TODO método de acesso ao nome do sequence diagram
					System.out.println("\tSD id [" + sd.getId() + "]\tname []");
				}
				break;
			// SD's Lifeline
			case "lifeline":
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					sd.addLifeline(new Lifeline(me.getXMIID(), me.getName(), sd).setBCompRel(Double.parseDouble(me.getPlainAttribute("BCompRel"))));
					System.out.println("\tLifeline id [" + me.getXMIID() + "]\tname [" + me.getName() + "]");
				}
				break;
			// SD's Asynchronous Message
			case "asynchronousmessage":
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					// TODO signal da async message
					sd.addAsyncMessage(me.getXMIID(), me.getPlainAttribute("source"), me.getPlainAttribute("target"), "DefaultSignal");
					System.out.println(
							"\tAsyncMessage source [" + me.getPlainAttribute("source") + "] target [" + me.getPlainAttribute("target") + "]");
				}
				break;
			// default case is an element type not found
			default:
				break;
			}
		}

		// transform ADs and/or SD to DTMC in PRISM language
		if (ad != null) {
			try {
				ad.toDTMC().toPRISM();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (sd != null) {
			try {
				sd.toDTMC().toPRISM();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
