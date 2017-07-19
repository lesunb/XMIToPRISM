package br.unb.xmiconverter;

import java.io.File;
import java.util.Scanner;

import br.unb.xmiconverter.util.FileController;
import br.unb.xmiconverter.util.MessageController;

public class MainClass {

	private static final Integer NUMBER_SUPPORTED_TOOLS = 2;

	public static void main(String[] args) {

		int a = 0;
		for (String s : args) {
			System.out.println("args[" + a + "]: " + s);
			a++;
		}

		MessageController.printToolOptionMenu();
		Scanner reader = new Scanner(System.in);
		Integer option = reader.nextInt();
		while (option < 1 || option > NUMBER_SUPPORTED_TOOLS) {
			MessageController.printOptionNotExistent(NUMBER_SUPPORTED_TOOLS);
			MessageController.printToolOptionMenu();
			option = reader.nextInt();
		}
		reader.close();
		String umlTool = MessageController.getTool(option);

		ModelConverter converter = ModelConverter.getInstance();

		if (args.length == 0) {
			// TODO filter xmi files right here. erase method isXmi
			File folder = new File(System.getProperty("user.dir"));
			File[] listOfFiles = folder.listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {
				String filename = listOfFiles[i].getName();
				if (listOfFiles[i].isFile() && FileController.isXmi(filename)) {
					converter.convert(umlTool, filename);
				}
			}
		} else {
			for (int i = 0; i < args.length; i++) {
				converter.convert(umlTool, args[i]);
			}
		}
	}
}