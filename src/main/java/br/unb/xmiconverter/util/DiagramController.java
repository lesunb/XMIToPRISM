package br.unb.xmiconverter.util;

import br.unb.dali.models.agg.uml.ActivityDiagram;
import br.unb.dali.models.agg.uml.SequenceDiagram;

public class DiagramController {

	private static ActivityDiagram activityDiagram = null;
	private static SequenceDiagram sequenceDiagram = null;

	public static ActivityDiagram getActivityDiagram() {
		return activityDiagram;
	}

	public static void setActivityDiagram(ActivityDiagram activityDiagram) {
		DiagramController.activityDiagram = activityDiagram;
	}

	public static SequenceDiagram getSequenceDiagram() {
		return sequenceDiagram;
	}

	public static void setSequenceDiagram(SequenceDiagram sequenceDiagram) {
		DiagramController.sequenceDiagram = sequenceDiagram;
	}

}
