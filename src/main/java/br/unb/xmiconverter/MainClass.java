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
