package br.unb.xmiconverter.util;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Provides operations related to Paths of files and folders.
 * 
 * @author Pedro
 */
public class PathUtil {

	private final String metamodelFolder = "/configuration-scripts/";
	private final String transformationsFolder = "/configuration-scripts/transformations/";
	private final URL metamodelPath = PathUtil.class
			.getResource(metamodelFolder + "metamodel-unb_dali.xml");

	public String getCurrentFolder() {
		return System.getProperty("user.dir");
	}

	public String getMetamodelPath() {
		return metamodelPath.toString();
	}

	// TODO use a Map also or restrict the name of the future XMI
	// Transformations?
	public String getTransformationPath(String tool) {
		URL url = PathUtil.class.getResource(
				transformationsFolder + tool + "-transformations.xml");
		return url.toString();
	}

	public Path getOutputPath(String xmiFile) {
		return Paths
				.get(xmiFile.substring(0, xmiFile.lastIndexOf('.')) + ".pm");
	}

}
