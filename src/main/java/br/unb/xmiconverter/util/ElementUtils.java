package br.unb.xmiconverter.util;

import java.util.Arrays;

import com.sdmetrics.model.ModelElement;

public class ElementUtils {

	public static String initialNode = "initialnode";

	// type names for the diagram elements may differ depending on the modeling tool
	// this list is updated with the transformations files
	static String[] adTypes = { "uml:Activity" };
	static String[] sdTypes = { "uml:Interaction" };
	static String[] initialNodeTypes = { "initial", "uml:InitialNode" };
	static String[] executableNodeTypes = { "uml:OpaqueAction" };
	static String[] junctionNodeTypes = { "junction", "uml:DecisionNode", "uml:MergeNode" };
	static String[] decisionNodeTypes = { "uml:DecisionNode" };
	static String[] mergeNodeTypes = { "uml:MergeNode" };
	static String[] finalNodeTypes = { "activityfinal", "uml:ActivityFinalNode" };

	public static String getElementTypeName(ModelElement me) {
		ElementNames element = null;
		if (Arrays.stream(adTypes).parallel().anyMatch(me.getPlainAttribute("type")::contains)) {
			element = ElementNames.ACTIVITY;
		} else if (Arrays.stream(sdTypes).parallel().anyMatch(me.getPlainAttribute("type")::contains)) {
			element = ElementNames.SEQUENCE;
		} else if (Arrays.stream(initialNodeTypes).parallel().anyMatch(me.getPlainAttribute("type")::contains)) {
			element = ElementNames.INITIAL;
		} else if (Arrays.stream(executableNodeTypes).parallel().anyMatch(me.getPlainAttribute("type")::contains)) {
			element = ElementNames.EXECUTABLE;
		} else if (Arrays.stream(junctionNodeTypes).parallel().anyMatch(me.getPlainAttribute("type")::contains)) {
			element = ElementNames.JUNCTION;
		} else if (Arrays.stream(decisionNodeTypes).parallel().anyMatch(me.getPlainAttribute("type")::contains)) {
			element = ElementNames.DECISION;
		} else if (Arrays.stream(mergeNodeTypes).parallel().anyMatch(me.getPlainAttribute("type")::contains)) {
			element = ElementNames.MERGE;
		} else if (Arrays.stream(finalNodeTypes).parallel().anyMatch(me.getPlainAttribute("type")::contains)) {
			element = ElementNames.FINAL;
		} else {
			element = ElementNames.UNKNOWN;
		}
		return element.getElement();
	}

	public static Double getProbability(ModelElement me) {
		Double probability = null;
		try {
			if (me.getPlainAttribute("type").equals("controlflow")) {
				probability = Double.parseDouble(me.getPlainAttribute("probability"));
			} else {
				probability = Double.parseDouble(me.getPlainAttribute("BCompRel"));
			}
		} catch (Exception e) {
			System.out.println("Could not get probability from " + me.getPlainAttribute("type") + " ID: [" + me.getXMIID() + "]");
		}
		return probability;
	}

}
