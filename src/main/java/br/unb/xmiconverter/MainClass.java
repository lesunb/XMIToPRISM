package br.unb.xmiconverter;

import java.io.File;

import br.unb.xmiconverter.util.FileUtil;
import br.unb.xmiconverter.util.MessageUtil;

/**
 * The entry point of the application. It has utility objects to find the
 * current folder with the XMI files and print the termination message.
 */
public class MainClass {

	private static FileUtil fu = new FileUtil();
	private static MessageUtil mu = new MessageUtil();

	/**
	 * Calls the interface Menu and process the files according to the user's
	 * option.
	 * 
	 * @param args
	 *            The XMI files that the user wants to convert. If the user
	 *            enters no arguments, the converter will process every file in
	 *            the folder with the extensions .xmi, .xml and .uml.
	 * 
	 */
	public static void main(String[] args) {
		String modelingTool = args[0];
		Converter converter = new Converter();

		if (args.length == 1) {
			System.out.println();
			File[] listXMIFiles = fu.getXmiList(fu.getCurrentFolder());
			for (File file : listXMIFiles) {
				converter.convert(modelingTool, file.getName());
			}
		} else {
			for (int i = 1; i < args.length; i++) {
				String filename = args[i];
				converter.convert(modelingTool, filename);
			}
		}
		mu.printTerminated();
	}
}
