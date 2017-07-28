package br.unb.xmiconverter.builder;

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
import br.unb.xmiconverter.util.ElementUtils;

public class DiagramBuilder {

	public static AbstractAggModel buildDiagram(MetaModel metaModel, Model model) {
		ElementUtils eu = new ElementUtils();
		AbstractAggModel diagram = null;

		for (MetaModelElement type : metaModel) {
			String elementType = type.getName();
			List<ModelElement> elements;

			switch (elementType) {

			// *** UML DIAGRAM ***
			case "diagram":
				elements = model.getAcceptedElements(type);
				ModelElement e = elements.get(0);
				if (eu.isAD(e)) {
					diagram = new ActivityDiagram(e.getXMIID(), e.getName());
				} else if (eu.isSD(e)) {
					diagram = new SequenceDiagram(e.getXMIID(), e.getName());
				} else {
					System.out.println("Diagram type not found.");
					return null;
				}
				break;

			// *** ACTIVITY DIAGRAM NODE ***
			case "node":
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					if (eu.isExecutableNode(me)) {
						((ActivityDiagram) diagram).addExecutableNode(new ExecutableNode(me.getXMIID(), (ActivityDiagram) diagram));
					} else if (eu.isJunctionNode(me)) {
						if (eu.isDecisionNode(me) || me.getSetAttribute("incomingEdges").size() == 1) {
							((ActivityDiagram) diagram).addDecisionNode(new DecisionNode(me.getXMIID(), (ActivityDiagram) diagram));
						} else {
							((ActivityDiagram) diagram).addMergeNode(new MergeNode(me.getXMIID(), (ActivityDiagram) diagram));
						}
					} else if (eu.isInitialNode(me)) {
						((ActivityDiagram) diagram).addInitialNode(new InitialNode(me.getXMIID(), (ActivityDiagram) diagram));
					} else if (eu.isFinalNode(me)) {
						((ActivityDiagram) diagram).addFinalNode(new FinalNode(me.getXMIID(), (ActivityDiagram) diagram));
					} else {
						System.out.println("Node type not found.");
						return null;
					}
				}
				break;

			// *** ACTIVITY DIAGRAM EDGE ***
			case "controlflow":
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					ControlFlow cf = new ControlFlow(me.getXMIID(), me.getPlainAttribute("source"), me.getPlainAttribute("target"), (ActivityDiagram) diagram);
					Double probability = eu.getProbability(me);
					if (probability != null) {
						cf.setPTS(probability);
						((ActivityDiagram) diagram).addControlFlow(cf);
					} else {
						return null;
					}
				}
				break;

			// *** SEQUENCE DIAGRAM LIFELINE ***
			case "lifeline":
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					Lifeline lifeline = new Lifeline(me.getXMIID(), me.getName(), (SequenceDiagram) diagram);
					Double probability = eu.getProbability(me);
					if (probability != null) {
						lifeline.setBCompRel(probability);
						((SequenceDiagram) diagram).addLifeline(lifeline);
					} else {
						return null;
					}
				}
				break;

			// *** SEQUENCE DIAGRAM MESSAGE (ONLY ASYNCHRONOUS FOR NOW) ***
			case "message":
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					((SequenceDiagram) diagram).addAsyncMessage(me.getXMIID(), me.getPlainAttribute("source"), me.getPlainAttribute("target"), "DefaultSignal");
				}
				break;
			default:
				break;
			}
		}
		return diagram;
	}

}
