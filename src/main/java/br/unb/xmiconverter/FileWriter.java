package br.unb.xmiconverter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;

public class FileWriter {

	public static void writePrismFile(String transformationResult, String xmiFile) {
		try (BufferedWriter bw = Files.newBufferedWriter(PathController.getOutputPath(xmiFile))) {
			bw.write(transformationResult);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
