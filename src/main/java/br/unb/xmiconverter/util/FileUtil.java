package br.unb.xmiconverter.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

/**
 * Provides operations related to files, such as writing the output file,
 * returning a path name of a file and getting a list of files through an
 * XMI-file-extension filter.
 * 
 * @author Pedro
 */
public class FileUtil {
	private PathUtil pu = new PathUtil();
	static final String[] acceptedFileExtensions = { ".xml", ".xmi", ".uml" };

	/**
	 * Writes an output file with the same file name but with the PRISM language
	 * extension (".pm") in the same folder of the XMI file being converted.
	 * 
	 * @param transformationResult
	 *            The result in PRISM language.
	 * @param xmiFile
	 *            The name of the XMI file so the method can use it to get its
	 *            path and change the output file extension to ".pm"
	 */
	public void writePrismFile(String transformationResult, String xmiFile) {
		try (BufferedWriter bw = Files
				.newBufferedWriter(pu.getOutputPath(xmiFile))) {
			bw.write(transformationResult);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param folder
	 *            The folder provided for the search
	 * @return An array of Files with the paths of XMI files inside the folder
	 */
	public File[] getXmiList(String folder) {
		File currentDirectory = new File(folder);
		FilenameFilter textFilter = new FilenameFilter() {
			public boolean accept(File currentDirectory, String name) {
				String lowercaseName = name.toLowerCase();
				if (Arrays.stream(acceptedFileExtensions).parallel()
						.anyMatch(lowercaseName::endsWith)) {
					return true;
				} else {
					return false;
				}
			}
		};
		return currentDirectory.listFiles(textFilter);
	}

	// TODO change from String to Path?
	/**
	 * @param filePath
	 *            The path of the file in String format.
	 * 
	 * @return A String that contains the name of the file in the path.
	 */
	public String getFileNameFromPath(String filePath) {
		String filename = "";
		if (filePath.lastIndexOf('\\') != -1) {
			filename = filePath.substring(filePath.lastIndexOf('\\') + 1,
					filePath.length());
		} else {
			filename = filePath.substring(filePath.lastIndexOf('/') + 1,
					filePath.length());
		}
		return filename;
	}

}
