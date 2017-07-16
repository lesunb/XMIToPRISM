package br.unb.xmiconverter;

import br.unb.xmiconverter.util.MessageController;

public class MainClass {

	final static Integer MINIMUM_ARGUMENTS = 2;

	// TODO multiple files? for now just one file.
	public static void main(String[] args) {

		if (args.length >= MINIMUM_ARGUMENTS) {
			String umlTool = args[0];
			String xmiFile = args[1];
			ModelConverter converter = ModelConverter.getInstance();
			converter.convert(umlTool, xmiFile);
		} else {
			MessageController.printString("Not enough arguments. Need the UML Tool and the XMI filename.");
		}
	}
}
