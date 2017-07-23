package br.unb.xmiconverter;

import java.io.File;

import br.unb.xmiconverter.util.FileController;
import br.unb.xmiconverter.util.MessageController;

public class MainClass {
	public static void main(String[] args) {
		String umlTool = MessageController.startMenu();
		ZModelConverter_backup converter = ZModelConverter_backup.getInstance();

		// if the user runs the .JAR with no arguments, it converts all XMI files inside the folder
		if (args.length == 0) {
			File[] listOfXmiFiles = FileController.getXmiFiles();
			for (int i = 0; i < listOfXmiFiles.length; i++) {
				converter.convert(umlTool, listOfXmiFiles[i].getName());
			}
			// else just converts what came in as arguments
		} else {
			for (int i = 0; i < args.length; i++) {
				converter.convert(umlTool, args[i]);
			}
		}
	}
}
