package br.unb.xmiconverter;

public class TestMain {

	final static Integer MINIMUM_ARGUMENTS = 2;

	// TODO multiple files? for now just one file.
	public static void main(String[] args) {

		if (args.length >= MINIMUM_ARGUMENTS) {
			String umlTool = args[0];
			String xmiFile = args[1];
			TestModelConverter converter = TestModelConverter.getInstance();
			converter.convert(umlTool, xmiFile);
		} else {
			MessageController.printString("Not enough arguments. Need the UML Tool and the XMI filename.");
		}
	}
}
