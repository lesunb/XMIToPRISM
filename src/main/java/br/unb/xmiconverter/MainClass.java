package br.unb.xmiconverter;

import br.unb.xmiconverter.util.MessageController;

public class MainClass {

	private static final Integer MINIMUM_ARGUMENTS = 2;

	public static void main(String[] args) {
		if (args.length >= MINIMUM_ARGUMENTS) {
			String umlTool = args[0];
			ModelConverter converter = ModelConverter.getInstance();
			for (int i = 1; i < args.length; i++) {
				String xmiFile = args[i];
				converter.convert(umlTool, xmiFile);
			}
		} else {
			MessageController.print("Not enough arguments. Need the UML Tool and the XMI filename.");
		}
	}
}
