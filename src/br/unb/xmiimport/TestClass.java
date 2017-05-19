package br.unb.xmiimport;

// how to parse an XMI file and access the model elements

// step 1: Required imports
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

import br.unb.dali.models.agg.markovchains.DTMC;
import br.unb.dali.models.agg.uml.ActivityDiagram;
import br.unb.dali.models.agg.uml.SequenceDiagram;
import br.unb.dali.models.agg.uml.ad.edges.ControlFlow;
import br.unb.dali.models.agg.uml.ad.nodes.control.DecisionNode;
import br.unb.dali.models.agg.uml.ad.nodes.control.FinalNode;
import br.unb.dali.models.agg.uml.ad.nodes.control.InitialNode;
import br.unb.dali.models.agg.uml.ad.nodes.control.MergeNode;
import br.unb.dali.models.agg.uml.ad.nodes.executable.ExecutableNode;
import br.unb.dali.util.io.IOHelper;

public class TestClass {
	public static void main(String[] args) {

		// step 2: Have your metamodel, XMI transformation, and XMI input files ready:
		String metaModelURL = "sdmetrics-metamodel-and-transformationfile/unb-dali-metamodel-elements.xml";
		String xmiTransURL = "sdmetrics-metamodel-and-transformationfile/unb-dali-astah-transformation-file.xml";

		String file = new String();
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the name of the file inside the \"tests\" folder: ");
		file = input.nextLine().concat(".xml");
		input.close();
		String xmiFile = "tests/".concat(file);

		// step 3: Read the metamodel
		// You do not have to use the SAX parser provided by class XMLParser, you may just as well use a org.xml.sax.XMLReader that you created
		// yourself.
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

		// step 4: Read the XMI transformation file
		XMITransformations transformation = new XMITransformations(metaModel);
		try {
			parser.parse(xmiTransURL, transformation.getSAXParserHandler());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// step 5: Read the XMI file with the UML model
		Model model = new Model(metaModel);
		XMIReader xmiReader = new XMIReader(transformation, model);
		try {
			parser.parse(xmiFile, xmiReader);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// step 6: Optionally, specify element filters to get rid of standard libraries or 3rd party APIs
		String[] filters = { "#.java", "#.javax", "#.org.xml" };
		model.setFilter(filters, false, true);

		// step 7: Access the UML model
		// The following example writes all model elements accepted by the element filter to the console, along with the values of their attributes.

		ActivityDiagram ad = null;
		SequenceDiagram sd = null;
		// iterate over each type and in each type iterate over each model element of that type
		for (MetaModelElement type : metaModel) {
			List<ModelElement> elements;
			System.out.println("Elements of type: " + type.getName());
			switch (type.getName().toLowerCase()) {

			// activity diagram (AD)
			case "activity":
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					System.out.println("  Element: " + me.getFullName());
					ad = new ActivityDiagram(me.getXMIID(), me.getName());
					System.out.println("Activity Diagram [" + ad.getId() + "] and name " + ad.getName() + " created");
				}
				break;

			// sequence diagram (SD)
			case "sequence":
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					System.out.println("  Element: " + me.getFullName());
					sd = new SequenceDiagram(me.getXMIID(), me.getName());
					// faltando método de acesso ao nome do sequence diagram
					System.out.println("Sequence Diagram [" + sd.getId() + "] created");
				}
				break;

			// AD's Executable Action
			case "action":
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					System.out.println("  Element: " + me.getFullName());
					// achar o AD ao qual se refere
					ad.addExecutableNode(new ExecutableNode(me.getXMIID(), ad));
					System.out.println("Action [" + me.getXMIID() + "] and name " + me.getName() + " created");
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
						break;
					}
					System.out.println("ControlNode [" + me.getXMIID() + "] and name " + me.getName() + " created");
				}
				break;

			// AD's Edges
			case "controlflow":
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					System.out.println("  Element: " + me.getFullName());
					// achar o AD ao qual se refere
					ad.addControlFlow(new ControlFlow(me.getXMIID(), me.getPlainAttribute("source"), me.getPlainAttribute("target"), ad)
							.setPTS(Double.parseDouble(me.getPlainAttribute("probability"))));
					System.out.println("Edge [" + me.getXMIID() + "] and name " + me.getName() + " created");
				}

				break;

			// default case is an element not found
			default:
				System.out.println("Element type not defined.");
				break;
			}
		}

		try {
			DTMC dtmc = ad.toDTMC();
			System.out.println(dtmc.toPRISM());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
