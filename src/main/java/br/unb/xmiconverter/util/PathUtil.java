package br.unb.xmiconverter.util;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathUtil {

	private static final String metamodelFolder = "/configuration-scripts/";
	private static final String transformationsFolder = "/configuration-scripts/transformations/";
	private static final URL metamodelPath = PathUtil.class.getResource(metamodelFolder + "metamodel-unb_dali.xml");

	public static String getCurrentFolder() {
		return System.getProperty("user.dir");
	}

	public static String getMetamodelPath() {
		return metamodelPath.toString();
	}

	public static String getTransformationPath(String tool) {
		return PathUtil.class.getResource(transformationsFolder + tool + "-transformations.xml").toString();
	}

	public static Path getOutputPath(String xmiFile) {
		Path outputPath = Paths.get(xmiFile.substring(0, xmiFile.lastIndexOf('.')) + ".pm");
		return outputPath;
	}

}
