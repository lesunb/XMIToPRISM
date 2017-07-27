package br.unb.xmiconverter.util;

// @formatter:off
public enum ElementNames {
	ACTIVITY("activitydiagram"),
	SEQUENCE("sequencediagram"),
	INITIAL("initialnode"),
    FINAL("finalnode"),
    EXECUTABLE("executablenode"),
    JUNCTION("junctionnode"),
    DECISION("decisionnode"),
    MERGE("mergenode"),
    LIFELINE("lifeline"),
    ASYNMESSAGE("asyncmessage"),
    UNKNOWN("");
//@formatter:on
	private String element;

	ElementNames(String element) {
		this.element = element;
	}

	public String getElement() {
		return element;
	}
}
