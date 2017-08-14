package br.unb.xmiconverter;

import java.io.File;

import br.unb.xmiconverter.util.FileUtil;
import br.unb.xmiconverter.util.MessageUtil;
import br.unb.xmiconverter.util.PathUtil;

/**
 * The entry point of the application. It has some utility objects to help
 * identify the current folder where the XMI files are and show messages, like
 * the menu.
 */
public class MainClass {

	private static FileUtil fu = new FileUtil();
	private static MessageUtil mu = new MessageUtil();
	private static PathUtil pu = new PathUtil();

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
		String umlModelingTool = mu.startMenu();
		Converter converter = new Converter();

		if (args.length == 0) {
			File[] listXMIFiles = fu.getXmiList(pu.getCurrentFolder());
			// TODO Send Path for system independence
			for (File file : listXMIFiles) {
				converter.convert(umlModelingTool, file.getName());
			}
		} else {
			for (String filename : args) {
				converter.convert(umlModelingTool, filename);
			}
		}
	}
}
