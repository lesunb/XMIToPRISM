package br.unb.xmiconverter.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class FileUtil {

	final static String[] acceptedFileExtensions = { ".xml", ".xmi", ".uml" };

	public static void writePrismFile(String transformationResult, String xmiFile) {
		try (BufferedWriter bw = Files.newBufferedWriter(PathUtil.getOutputPath(xmiFile))) {
			bw.write(transformationResult);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static File[] getListOfXmiFilesInFolder(String folder) {
		File currentDirectory = new File(folder);
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

	public static String[] getAcceptedFileExtensions() {
		return acceptedFileExtensions;
	}

	public static String getFileNameFromPathInString(String filePath) {
		String filename = "";
		if (filePath.lastIndexOf('\\') != -1) {
			filename = filePath.substring(filePath.lastIndexOf('\\') + 1, filePath.length());
		} else {
			filename = filePath.substring(filePath.lastIndexOf('/') + 1, filePath.length());
		}
		return filename;
	}

}
