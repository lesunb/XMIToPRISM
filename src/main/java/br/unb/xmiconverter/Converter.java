package br.unb.xmiconverter;

import br.unb.dali.models.agg.AbstractAggModel;
import br.unb.dali.models.agg.uml.ActivityDiagram;
import br.unb.dali.models.agg.uml.SequenceDiagram;
import br.unb.dali.util.prism.PRISMModel;
import br.unb.xmiconverter.builder.DiagramBuilder;
import br.unb.xmiconverter.builder.ModelBuilder;
import br.unb.xmiconverter.util.FileUtil;
import br.unb.xmiconverter.util.MessageUtil;
import br.unb.xmiconverter.util.TimeUtil;

public class Converter {

	private static Converter instance = null;
	@SuppressWarnings("unused")
	private double conversionTime = 0;

	private Converter() {
	}

	public static Converter getInstance() {
		if (instance == null) {
			instance = new Converter();
		}
		return instance;
	}

	// @formatter:off
	/* 1. Build MetalModel and model (with the configuration files and the XMI file)
	 * 2. Iterate over MetaModel elements and build a diagram with the elements found in the model
	 * 3. Convert to PRISM using UnB-DALi
	 * 4. Write output file
	 * 5. If the conversion was successful returns true, else returns false
	 * */
	// @formatter:on
	protected boolean convert(String umlModelingTool, String xmiFile) {
		ModelBuilder.buildModel(umlModelingTool, xmiFile);
		AbstractAggModel diagram = DiagramBuilder.buildDiagram(ModelBuilder.getMetaModel(), ModelBuilder.getModel());
		boolean conversionResult = false;
		// diagram is converted to PRISM if there were no errors during it's construction
		if (diagram != null) {
			PRISMModel prismModel = convertToPRISM(diagram);
			if (prismModel != null) {
				conversionResult = true;
				FileUtil.writePrismFile(prismModel.toString(), xmiFile);

			}
		}
		MessageUtil.printResultOfConversion(xmiFile, conversionResult);
		return conversionResult;
	}

	private PRISMModel convertToPRISM(AbstractAggModel diagram) {
		PRISMModel prismModel = null;
		long startTime = 0;
		long finishTime = 0;

		// TODO check an auto cast solution
		try {
			if (diagram.getClass().getSimpleName().equals("ActivityDiagram")) {
				startTime = TimeUtil.getTimeNano();
				prismModel = ((ActivityDiagram) diagram).toDTMC().toPRISM();
				finishTime = TimeUtil.getTimeNano();
			} else {
				startTime = TimeUtil.getTimeNano();
				prismModel = ((SequenceDiagram) diagram).toDTMC().toPRISM();
				finishTime = TimeUtil.getTimeNano();
			}
			conversionTime = TimeUtil.getTimeInMilliseconds(startTime, finishTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prismModel;
	}

}
