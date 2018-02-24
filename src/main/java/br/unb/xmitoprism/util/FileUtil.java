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
import java.util.HashMap;
import java.util.Map;

/**
 * Provides operations related to files, such as retrieving a file or folder
 * path, writing the output file and getting a list of files through an
 * XMI-file-extension filter.
 * 
 * @author Pedro
 */
public class FileUtil {
	private static final Map<String, String> transformationsMap = createTransformationsMap();
	private final String metamodelFolder = "/configuration-scripts/";
	private final String transformationsFolder = "/configuration-scripts/transformations/";
	private final URL metamodelPath = FileUtil.class
			.getResource(metamodelFolder + "metamodel-unb_dali.xml");
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
	 * @param
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
	 * @param tool
	 *            the name of the UML Modeling Tool that is used as a key to the
	 *            Map that has the name of the corresponding XMI Transformation
	 *            File.
	 * 
	 * @return A String with the URI of the XMI Transformation File that
	 *         corresponds to the chosen tool.
	 */
	public String getTransformationPath(String tool) {
		URL url = FileUtil.class.getResource(
				transformationsFolder + getTransformationFile(tool));
		return url.toString();
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

	/**
	 * In the future more tools will be supported. This HashMap associates the
	 * simplified name of the tool with the name of the XMI Transformation File
	 * that is located inside the resources folder. Don't forget to rebuild the
	 * project if you add a new file!
	 */
	private static Map<String, String> createTransformationsMap() {
		Map<String, String> transformationsMap = new HashMap<String, String>();
		transformationsMap.put("argo", "argouml-transformations.xml");
		transformationsMap.put("astah", "astah-transformations.xml");
		transformationsMap.put("bouml", "bouml-transformations.xml");
		transformationsMap.put("magicdraw", "magicdraw-transformations.xml");
		transformationsMap.put("papyrus", "papyrus-transformations.xml");
		return transformationsMap;
	}

	/**
	 * @param The
	 *            tool which is the key of the HashMap.
	 * 
	 * @return The name of the XMI Transformation File associated with the tool.
	 */
	private String getTransformationFile(String tool) {
		return transformationsMap.get(tool);
	}

}
