package br.unb.xmiconverter.util;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathUtil {

	private final String metamodelFolder = "/configuration-scripts/";
	private final String transformationsFolder = "/configuration-scripts/transformations/";
	private final URL metamodelPath = PathUtil.class.getResource(metamodelFolder + "metamodel-unb_dali.xml");

	public String getCurrentFolder() {
		return System.getProperty("user.dir");
	}

	public String getMetamodelPath() {
		return metamodelPath.toString();
	}

	public String getTransformationPath(String tool) {
		return PathUtil.class.getResource(transformationsFolder + tool + "-transformations.xml").toString();
	}

	public Path getOutputPath(String xmiFile) {
		return Paths.get(xmiFile.substring(0, xmiFile.lastIndexOf('.')) + ".pm");
	}

}
