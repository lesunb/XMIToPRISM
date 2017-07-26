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
			File[] listXMIFiles = FileUtil.getListOfXmiFilesInFolder(PathUtil.getCurrentFolder());
			for (File file : listXMIFiles) {
				converter.convert(umlModelingTool, file.getName());
			}
			// else just converts what came in as arguments
		} else {
			for (String file : args) {
				converter.convert(umlModelingTool, file);
			}
		}
	}
}
