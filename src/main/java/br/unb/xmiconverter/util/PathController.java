package br.unb.xmiconverter.util;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathController {

	private static final String metamodelFolder = "/configuration-scripts/";
	private static final String transformationsFolder = "/configuration-scripts/transformations/";
	private static final URL metamodelPath = PathController.class.getResource(metamodelFolder + "metamodel-unb_dali.xml");

	public static URL getMetamodelPath() {
		return metamodelPath;
	}

	public static URL getTransformationPath(String tool) {
		return PathController.class.getResource(transformationsFolder + tool + "-transformations.xml");
	}

	public static Path getOutputPath(String xmiFile) {
		Path path = Paths.get(xmiFile.substring(0, xmiFile.lastIndexOf('.')) + ".pm");
		return path;
	}
}
