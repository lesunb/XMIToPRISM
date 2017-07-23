package br.unb.xmiconverter.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class FileController {

	final static String[] acceptedFileExtensions = { ".xml", ".xmi", ".uml" };

	public static void writePrismFile(String transformationResult, String xmiFile) {
		try (BufferedWriter bw = Files.newBufferedWriter(PathController.getOutputPath(xmiFile))) {
			bw.write(transformationResult);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static File[] getXmiFiles() {
		File currentDirectory = new File(System.getProperty("user.dir"));
		FilenameFilter textFilter = new FilenameFilter() {
			public boolean accept(File currentDirectory, String name) {
				String lowercaseName = name.toLowerCase();
				if (Arrays.stream(acceptedFileExtensions).parallel().anyMatch(lowercaseName::endsWith)) {
					return true;
				} else {
					return false;
				}
			}
		};
		return currentDirectory.listFiles(textFilter);
	}

	public static String[] getAcceptedfileextensions() {
		return acceptedFileExtensions;
	}

}
