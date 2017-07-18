package br.unb.xmiconverter;

import java.io.File;

import br.unb.xmiconverter.util.FileController;
import br.unb.xmiconverter.util.MessageController;

public class MainClass {

	private static final Integer MINIMUM_ARGUMENTS = 2;
	private static final String RUN_ALL_FLAG = "all";

	public static void main(String[] args) {
		if (args.length >= MINIMUM_ARGUMENTS) {
			String umlTool = args[0];
			ModelConverter converter = ModelConverter.getInstance();
			if (args[1].equals(RUN_ALL_FLAG)) {
				File folder = new File(System.getProperty("user.dir"));
				File[] listOfFiles = folder.listFiles();

				for (int i = 0; i < listOfFiles.length; i++) {
					String filename = listOfFiles[i].getName();
					if (listOfFiles[i].isFile() && FileController.fileIsXmi(filename)) {
						converter.convert(umlTool, filename);
					}
				}
			} else {
				for (int i = 1; i < args.length; i++) {
					converter.convert(umlTool, args[i]);
				}
			}
		} else {
			MessageController.print("Not enough arguments. Need the UML Tool and the XMI filename.");
		}
	}
}
