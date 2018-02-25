package br.unb.xmitoprism.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Provides operations related to files, such as retrieving a file or folder
 * path, writing the output file and getting a list of files through an
 * XMI-file-extension filter.
 * 
 * @author Pedro
 */
public class FileUtil {
	private final URL metamodelPath = FileUtil.class
			.getResource("/metamodel_unb-dali.xml");
	private final URL transformationPath = FileUtil.class
			.getResource("/xmiTransformation_unb-dali.xml");
	static final String[] acceptedFileExtensions = { ".xml", ".xmi", ".uml" };

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

	/**
	 * @return A String with the folder where the program is running which is
	 *         where the XMI files are
	 */
	public String getCurrentFolder() {
		return System.getProperty("user.dir");
	}

	/**
	 * @return A String with the MetaModel path, which is inside the resources
	 *         folder.
	 */
	public String getMetamodelPath() {
		return metamodelPath.toString();
	}

	/**
	 * @return A String with the XMI Transformation path, which is inside the
	 *         resources folder.
	 */
	public String getTransformationPath() {
		return transformationPath.toString();
	}

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

	/**
	 * @param xmiFile
	 *            The URI of the file.
	 * @return The URI of the file with the extension ".pm" added. This will be
	 *         the name of the output PRISM file.
	 */
	public Path getOutputPath(String xmiFile) {
		return Paths
				.get(xmiFile.substring(0, xmiFile.lastIndexOf('.')) + ".pm");
	}

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
				.newBufferedWriter(this.getOutputPath(xmiFile))) {
			bw.write(transformationResult);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
