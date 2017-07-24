package br.unb.xmiconverter.builder;

import java.util.Collection;
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

	public static AbstractAggModel buildDiagram(MetaModel metaModel, Model model) {
		AbstractAggModel diagram = null;
		boolean elementAdditionSuccess = true;

		for (MetaModelElement type : metaModel) {
			List<ModelElement> elements;
			String elementType = type.getName();

			switch (elementType) {

			// TODO Only processing one diagram for now
			// Activity or Sequence diagrams
			case "diagram":
				elements = model.getAcceptedElements(type);
				ModelElement diagramElement = elements.get(0);
				String diagramType = diagramElement.getPlainAttribute("type");
				switch (diagramType) {
				case "Activity Diagram":
				case "uml:Activity":
					diagram = new ActivityDiagram(diagramElement.getXMIID(), diagramElement.getName());
					break;
				case "Sequence Diagram":
				case "uml:Interaction":
					diagram = new SequenceDiagram(diagramElement.getXMIID(), diagramElement.getName());
					break;
				default:
					System.out.println("No diagram type found.");
					elementAdditionSuccess = false;
					break;
				}

				if (!elementAdditionSuccess) {
					return null;
				}
				break;

			// Activity Diagram's - Executable Action
			case "action":
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					((ActivityDiagram) diagram).addExecutableNode(new ExecutableNode(me.getXMIID(), (ActivityDiagram) diagram));
				}
				break;

			// Activity Diagram's - Initial, Final, Decision and Merge nodes.
			case "node":
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					String nodeType = me.getPlainAttribute("type");
					switch (nodeType) {
					case "executable":
					case "uml:OpaqueAction":
						((ActivityDiagram) diagram).addExecutableNode(new ExecutableNode(me.getXMIID(), (ActivityDiagram) diagram));
						break;
					case "initial":
					case "uml:InitialNode":
						((ActivityDiagram) diagram).addInitialNode(new InitialNode(me.getXMIID(), (ActivityDiagram) diagram));
						break;
					case "activityfinal":
					case "uml:ActivityFinalNode":
						((ActivityDiagram) diagram).addFinalNode(new FinalNode(me.getXMIID(), (ActivityDiagram) diagram));
						break;
					case "uml:DecisionNode":
					case "uml:MergeNode":
					case "junction":
						Collection<?> incomingEdges = me.getSetAttribute("incomingEdges");
						// difference between merge and decision nodes is checked with the number of incoming edges
						if (incomingEdges.size() > 1) {
							((ActivityDiagram) diagram).addMergeNode(new MergeNode(me.getXMIID(), (ActivityDiagram) diagram));
						} else {
							((ActivityDiagram) diagram).addDecisionNode(new DecisionNode(me.getXMIID(), (ActivityDiagram) diagram));
						}
						break;
					default:
						System.out.println("Found node of unknown type.");
						elementAdditionSuccess = false;
						break;
					}

					if (!elementAdditionSuccess) {
						return null;
					}
				}
				break;

			// Activity Diagram's - Edges
			case "controlflow":
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
				break;

			// Sequence Diagram's - Lifelines
			case "lifeline":
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
				break;

			// Sequence Diagram's - Asynchronous Messages
			case "asynchronousmessage":
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
