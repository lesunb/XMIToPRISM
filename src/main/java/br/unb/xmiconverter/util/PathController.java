package br.unb.xmiconverter.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class PathController {

	static String currentPath = System.getProperty("user.dir");
	static String sdmetricsMetaModelPath = currentPath + "/configuration-scripts/metamodel-unb_dali.xml";

	public static String getMetamodelPath() {
		return sdmetricsMetaModelPath;
	}

	public static String getTransformationPath(String tool) {
		final Map<String, String> modelingToolsTransformations;
		modelingToolsTransformations = new HashMap<String, String>();
		modelingToolsTransformations.put("astah", currentPath + "/configuration-scripts/transformations/astah-transformations.xml");
		modelingToolsTransformations.put("papyrus", currentPath + "/configuration-scripts/transformations/papyrus-transformations.xml");
		modelingToolsTransformations.put("modelio", currentPath + "/configuration-scripts/transformations/modelio-transformations.xml");
		return modelingToolsTransformations.get(tool);
	}

	public static Path getOutputPath(String xmiFile) {
		Path path = Paths.get(xmiFile.substring(0, xmiFile.lastIndexOf('.')) + ".pm");
		return path;
	}
}
