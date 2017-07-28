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

	@SuppressWarnings("unused")
	private double conversionTime = 0;

	public Converter() {

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
				FileUtil fu = new FileUtil();
				fu.writePrismFile(prismModel.toString(), xmiFile);
			}
		}
		MessageUtil mu = new MessageUtil();
		mu.printResultOfConversion(xmiFile, conversionResult);
		return conversionResult;
	}

	private PRISMModel convertToPRISM(AbstractAggModel diagram) {
		PRISMModel prismModel = null;
		TimeUtil tu = new TimeUtil();
		long startTime = 0;
		long finishTime = 0;

		// TODO check an auto cast solution
		try {
			if (diagram.getClass().getSimpleName().equals("ActivityDiagram")) {
				startTime = tu.getTimeNano();
				prismModel = ((ActivityDiagram) diagram).toDTMC().toPRISM();
				finishTime = tu.getTimeNano();
			} else {
				startTime = tu.getTimeNano();
				prismModel = ((SequenceDiagram) diagram).toDTMC().toPRISM();
				finishTime = tu.getTimeNano();
			}
			conversionTime = tu.getTimeInMilliseconds(startTime, finishTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prismModel;
	}

}
