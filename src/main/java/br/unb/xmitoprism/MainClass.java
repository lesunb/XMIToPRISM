package br.unb.xmitoprism;

import java.io.File;

import br.unb.xmitoprism.util.FileUtil;
import br.unb.xmitoprism.util.MessageUtil;

/**
 * The entry point of the application. It has utility objects to find the
 * current folder with the XMI files and print the termination message.
 */
public class MainClass {

	private static FileUtil fu = new FileUtil();
	private static MessageUtil mu = new MessageUtil();

	/**
	 * Entry point of the program.
	 * 
	 * @param args
	 *            The XMI files to be converted. If the user enters no
	 *            arguments, the converter will process every file in the folder
	 *            with XMI extensions (.xmi, .xml and .uml).
	 * 
	 */
	public static void main(String[] args) {
		Converter converter = new Converter();

		if (args.length == 0) {
			System.out.println();
			File[] listXMIFiles = fu.getXmiList(fu.getCurrentFolder());
			for (File file : listXMIFiles) {
				converter.convert(file.getName());
			}
		} else {
			for (int i = 0; i < args.length; i++) {
				String filename = args[i];
				converter.convert(filename);
			}
		}
		mu.printTerminated();
	}
}
