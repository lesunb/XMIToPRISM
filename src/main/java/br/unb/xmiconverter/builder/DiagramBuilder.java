package br.unb.xmiconverter.builder;

import java.util.Arrays;
import java.util.List;

import com.sdmetrics.model.MetaModel;
import com.sdmetrics.model.MetaModelElement;
import com.sdmetrics.model.Model;
import com.sdmetrics.model.ModelElement;

import br.unb.dali.models.agg.AbstractAggModel;
import br.unb.dali.models.agg.uml.ActivityDiagram;
import br.unb.dali.models.agg.uml.SequenceDiagram;
import br.unb.dali.models.agg.uml.ad.edges.ControlFlow;
import br.unb.dali.models.agg.uml.ad.nodes.control.DecisionNode;
import br.unb.dali.models.agg.uml.ad.nodes.control.FinalNode;
import br.unb.dali.models.agg.uml.ad.nodes.control.InitialNode;
import br.unb.dali.models.agg.uml.ad.nodes.control.MergeNode;
import br.unb.dali.models.agg.uml.ad.nodes.executable.ExecutableNode;
import br.unb.dali.models.agg.uml.sd.Lifeline;

public class DiagramBuilder {

	// Different type names for the diagram elements. May differ depending on the modeling tool
	static String[] adTypes = { "uml:Activity" };
	static String[] sdTypes = { "uml:Interaction" };
	static String[] initialNodeTypes = { "initial", "uml:InitialNode" };
	static String[] executableNodeTypes = { "uml:OpaqueAction" };
	static String[] junctionNodeTypes = { "junction", "uml:DecisionNode", "uml:MergeNode" };
	static String[] decisionNodeTypes = { "uml:DecisionNode" };
	static String[] mergeNodeTypes = { "uml:MergeNode" };
	static String[] finalNodeTypes = { "activityfinal", "uml:ActivityFinalNode" };

	public static AbstractAggModel buildDiagram(MetaModel metaModel, Model model) {
		AbstractAggModel diagram = null;
		boolean elementAdditionSuccess = true;

		for (MetaModelElement type : metaModel) {
			String elementType = type.getName();
			List<ModelElement> elements;
			// DIAGRAMS
			if (elementType.equals("diagram")) {
				elements = model.getAcceptedElements(type);
				ModelElement me = elements.get(0);
				if (modelElementIsOfType(me, adTypes)) {
					diagram = new ActivityDiagram(me.getXMIID(), me.getName());
				} else if (modelElementIsOfType(me, sdTypes)) {
					diagram = new SequenceDiagram(me.getXMIID(), me.getName());
				} else {
					System.out.println("No diagram type found.");
					return null;
				}
				// AD NODES
			} else if (elementType.equals("node")) {
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					if (modelElementIsOfType(me, initialNodeTypes)) {
						((ActivityDiagram) diagram).addInitialNode(new InitialNode(me.getXMIID(), (ActivityDiagram) diagram));
					} else if (modelElementIsOfType(me, executableNodeTypes)) {
						((ActivityDiagram) diagram).addExecutableNode(new ExecutableNode(me.getXMIID(), (ActivityDiagram) diagram));
					} else if (modelElementIsOfType(me, junctionNodeTypes)) {
						if (modelElementIsOfType(me, decisionNodeTypes) || me.getSetAttribute("incomingEdges").size() == 1) {
							((ActivityDiagram) diagram).addDecisionNode(new DecisionNode(me.getXMIID(), (ActivityDiagram) diagram));
						} else {
							((ActivityDiagram) diagram).addMergeNode(new MergeNode(me.getXMIID(), (ActivityDiagram) diagram));
						}
					} else if (modelElementIsOfType(me, finalNodeTypes)) {
						((ActivityDiagram) diagram).addFinalNode(new FinalNode(me.getXMIID(), (ActivityDiagram) diagram));
					} else {
						System.out.println("Found node of unknown type.");
						return null;
					}
				}
				// AD EDGES
			} else if (elementType.equals("controlflow")) {
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					ControlFlow cf = new ControlFlow(me.getXMIID(), me.getPlainAttribute("source"), me.getPlainAttribute("target"), (ActivityDiagram) diagram);
					Double prob = null;
					try {
						prob = Double.parseDouble(me.getPlainAttribute("probability"));
					} catch (Exception e) {
						System.out.println("Found edge without associated probability or wrong number format. Fix the UML model.");
						elementAdditionSuccess = false;
					}

					if (elementAdditionSuccess) {
						cf.setPTS(prob);
						((ActivityDiagram) diagram).addControlFlow(cf);
					} else {
						return null;
					}
				}
				// SD LIFELINES
			} else if (elementType.equals("lifeline")) {
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					Lifeline lifeline = new Lifeline(me.getXMIID(), me.getName(), (SequenceDiagram) diagram);
					Double prob = null;
					try {
						prob = Double.parseDouble(me.getPlainAttribute("BCompRel"));
					} catch (Exception e) {
						System.out.println("Found Lifeline without associated probability or wrong number format. Fix the UML model.");
						elementAdditionSuccess = false;
					}

					if (elementAdditionSuccess) {
						lifeline.setBCompRel(prob);
						((SequenceDiagram) diagram).addLifeline(lifeline);
					} else {
						return null;
					}
				}
				// SD ASYNCHRONOUS MESSAGES
			} else if (elementType.equals("asynchronousmessage")) {
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					((SequenceDiagram) diagram).addAsyncMessage(me.getXMIID(), me.getPlainAttribute("source"), me.getPlainAttribute("target"), "DefaultSignal");
				}
			}

		}
		return diagram;
	}

	private static boolean modelElementIsOfType(ModelElement me, String[] typeList) {
		if (Arrays.stream(typeList).parallel().anyMatch(me.getPlainAttribute("type")::contains)) {
			return true;
		}
		return false;
	}

}
