package br.unb.xmiconverter;

import java.io.File;

import br.unb.xmiconverter.util.FileUtil;
import br.unb.xmiconverter.util.MessageUtil;
import br.unb.xmiconverter.util.PathUtil;

public class MainClass {

	private static FileUtil fu = new FileUtil();
	private static MessageUtil mu = new MessageUtil();
	private static PathUtil pu = new PathUtil();

	public static void main(String[] args) {
		String umlModelingTool = mu.startMenu();
		Converter converter = new Converter();

		// if the user runs the .JAR with no arguments, it converts all XMI files inside the folder
		if (args.length == 0) {
			File[] listXMIFiles = fu.getListOfXmiFilesInFolder(pu.getCurrentFolder());
			// TODO SEND THE PATH TO MAKE IT SYSTEM INDEPENDENT
			for (File file : listXMIFiles) {
				converter.convert(umlModelingTool, file.getName());
			}
			// else just converts what came in as arguments
		} else {
			for (String filename : args) {
				converter.convert(umlModelingTool, filename);
			}
		}
	}
}
