package br.unb.xmiconverter;

import java.util.HashMap;
import java.util.Map;

public class MainClass {

	// TODO multiple files after the selected tool? for now just one.
	// args order: 1) tool, 2)xmi file location (path)
	public static void main(String[] args) {

		// Initialization. The Map will increase but not to much. See if there is a better solution in the future.
		String sdmetricsMetaModelPath = "SDMetrics - Metamodel and Transformations/metamodel-unb_dali.xml";
		final Map<String, String> modelingToolsFiles;
		{
			modelingToolsFiles = new HashMap<String, String>();
			modelingToolsFiles.put("astah", "SDMetrics - Metamodel and Transformations/transformations/astah-transformations.xml");
			modelingToolsFiles.put("papyrus", "SDMetrics - Metamodel and Transformations/transformations/papyrus-transformations.xml");
		}

		// Gets values from arguments of Main. Must be in the exact order.
		String xmiTransformationPath = modelingToolsFiles.get(args[0]);
		String xmiModelPath = args[1];

		ModelConverter converter = ModelConverter.getInstance();
		converter.convert(sdmetricsMetaModelPath, xmiTransformationPath, xmiModelPath);
	}
}
