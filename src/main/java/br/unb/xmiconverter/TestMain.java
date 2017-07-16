package br.unb.xmiconverter;

import br.unb.xmiconverter.util.MessageController;

public class TestMain {

	private static final Integer MINIMUM_ARGUMENTS = 2;

	public static void main(String[] args) {
		if (args.length >= MINIMUM_ARGUMENTS) {
			String umlTool = args[0];
			String xmiFile = args[1];
			TestModelConverter converter = TestModelConverter.getInstance();
			converter.convert(umlTool, xmiFile);
		} else {
			MessageController.print("Not enough arguments. Need the UML Tool and the XMI filename.");
		}
	}
}
