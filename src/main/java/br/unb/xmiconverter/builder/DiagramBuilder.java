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

/**
 * Provides a building method that iterates over the constructed model in the
 * previous step and builds, element by element, an abstract diagram in the form
 * of an AGG Model. This class is the bridge that enables the communication
 * between the UML Diagram information and the UnB-DALi library.
 * 
 * @author Pedro
 */
public class DiagramBuilder {

	/**
	 * @param metaModel
	 *            The MetaModel constructed in the previous step, according to
	 *            its definition in a XMI file.
	 * @param model
	 *            The Model constructed in the previous step, that only includes
	 *            attributes that were triggered according to the XMI
	 *            Transformation.
	 * @return An AbstractAggModel, which can be and Activity Diagram or a
	 *         Sequence Diagram, since these are its subclasses.
	 */
	public AbstractAggModel buildDiagram(MetaModel metaModel, Model model) {
		AbstractAggModel diagram = null;
		for (MetaModelElement type : metaModel) {
			String elementType = type.getName();
			List<ModelElement> elements;

			switch (elementType) {

			// *** UML DIAGRAM ***
			case "diagram":
				elements = model.getAcceptedElements(type);
				ModelElement de = elements.get(0);
				String diagId = de.getXMIID();
				String diagName = de.getName();
				String diagramType = de.getPlainAttribute("type");
				switch (diagramType) {
				case "uml:Activity":
					diagram = new ActivityDiagram(diagId, diagName);
					break;

				case "uml:Interaction":
					diagram = new SequenceDiagram(diagId, diagName);
					break;

				default:
					System.out.println("Diagram type not found.");
					break;
				}
				break;

			// *** ACTIVITY DIAGRAM NODE ***
			case "node":
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					String nodeId = me.getXMIID();

					String nodeType = me.getPlainAttribute("type");
					switch (nodeType) {
					case "uml:OpaqueAction":
						ExecutableNode en = new ExecutableNode(nodeId,
								(ActivityDiagram) diagram);
						((ActivityDiagram) diagram).addExecutableNode(en);
						break;

					case "junction":
					case "uml:DecisionNode":
					case "uml:MergeNode":
						if (me.getSetAttribute("incomingEdges").size() == 1) {
							DecisionNode dn = new DecisionNode(nodeId,
									(ActivityDiagram) diagram);
							((ActivityDiagram) diagram).addDecisionNode(dn);
						} else {
							MergeNode mn = new MergeNode(nodeId,
									(ActivityDiagram) diagram);
							((ActivityDiagram) diagram).addMergeNode(mn);
						}
						break;

					case "initial":
					case "uml:InitialNode":
						InitialNode in = new InitialNode(nodeId,
								(ActivityDiagram) diagram);
						((ActivityDiagram) diagram).addInitialNode(in);
						break;

					case "uml:ActivityFinalNode":
						FinalNode fn = new FinalNode(nodeId,
								(ActivityDiagram) diagram);
						((ActivityDiagram) diagram).addFinalNode(fn);
						break;

					default:
						System.out.println("Node type not found: "
								+ me.getPlainAttribute("type"));
						break;
					}
				}
				break;

			// *** ACTIVITY DIAGRAM EDGE ***
			case "controlflow":
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					Double probability = getProbability(me);
					if (probability != null) {
						String cfId = me.getXMIID();
						String cfSource = me.getPlainAttribute("source");
						String cfTarget = me.getPlainAttribute("target");
						ControlFlow cf = new ControlFlow(cfId, cfSource,
								cfTarget, (ActivityDiagram) diagram);
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
					Double probability = getProbability(me);
					if (probability != null) {
						String llId = me.getXMIID();
						String llName = me.getName();
						Lifeline lifeline = new Lifeline(llId, llName,
								(SequenceDiagram) diagram);
						lifeline.setBCompRel(probability);
						((SequenceDiagram) diagram).addLifeline(lifeline);
					} else {
						return null;
					}
				}
				break;

			// *** SEQUENCE DIAGRAM MESSAGE (ONLY ASYNCHRONOUS) ***
			case "message":
				elements = model.getAcceptedElements(type);
				for (ModelElement me : elements) {
					String msgId = me.getXMIID();
					String msgSource = me.getPlainAttribute("source");
					String msgTarget = me.getPlainAttribute("target");
					String msgSignal = "DefaultSignal";
					((SequenceDiagram) diagram).addAsyncMessage(msgId,
							msgSource, msgTarget, msgSignal);
				}
				break;

			// MetaModel element not currently in the model.
			default:
				break;
			}
		}
		return diagram;
	}

	/**
	 * Gets the probability from different attributes depending on the type of
	 * the element.
	 * 
	 * @param me
	 *            A model element of the model.
	 * 
	 * @return A Double, in case of a successful conversion. Null if
	 *         unsuccessful.
	 */
	private Double getProbability(ModelElement me) {
		Double probability = null;
		try {
			if (me.getPlainAttribute("type").equals("controlflow")) {
				probability = Double
						.parseDouble(me.getPlainAttribute("probability"));
			} else {
				probability = Double
						.parseDouble(me.getPlainAttribute("BCompRel"));
			}
		} catch (Exception e) {
			System.out.println("Could not get probability from "
					+ me.getPlainAttribute("type") + " ID: [" + me.getXMIID()
					+ "]");
		}
		return probability;
	}

}
