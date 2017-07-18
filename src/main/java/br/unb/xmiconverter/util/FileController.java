package br.unb.xmiconverter.util;

import java.io.BufferedWriter;
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

	public static boolean fileIsXmi(String inputStr) {
		return Arrays.stream(acceptedFileExtensions).parallel().anyMatch(inputStr::contains);
	}

}
