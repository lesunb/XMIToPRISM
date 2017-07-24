package br.unb.xmiconverter;

import java.io.File;

import br.unb.xmiconverter.util.FileUtil;
import br.unb.xmiconverter.util.MessageUtil;
import br.unb.xmiconverter.util.PathUtil;

public class MainClass {
	public static void main(String[] args) {
		String umlModelingTool = MessageUtil.startMenu();
		Converter converter = Converter.getInstance();

		// if the user runs the .JAR with no arguments, it converts all XMI files inside the folder
		if (args.length == 0) {
			File[] listOfXmiFiles = FileUtil.getListOfXmiFilesInFolder(PathUtil.getCurrentFolder());
			for (int i = 0; i < listOfXmiFiles.length; i++) {
				converter.convert(umlModelingTool, listOfXmiFiles[i].getName());
			}
			// else just converts what came in as arguments
		} else {
			for (int i = 0; i < args.length; i++) {
				converter.convert(umlModelingTool, args[i]);
			}
		}
	}
}
