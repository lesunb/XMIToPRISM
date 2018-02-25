package br.unb.xmitoprism;

import br.unb.dali.models.agg.AbstractAggModel;
import br.unb.dali.models.agg.uml.ActivityDiagram;
import br.unb.dali.models.agg.uml.SequenceDiagram;
import br.unb.dali.util.prism.PRISMModel;
import br.unb.xmitoprism.builder.DiagramBuilder;
import br.unb.xmitoprism.builder.ModelBuilder;
import br.unb.xmitoprism.util.FileUtil;
import br.unb.xmitoprism.util.MessageUtil;
import br.unb.xmitoprism.util.TimeUtil;

/**
 * Represents the high level steps of the conversion. First the model is built
 * using the XMI file provided. Then, the AGG diagram is built with the
 * information extracted from the model and converted to PRISM language using
 * the UnB-DALi's method. The class' only variable is to evaluate the conversion
 * time.
 * 
 * @author Pedro
 */
public class Converter {

	private double conversionTimeMilli = 0;

	/**
	 * Calls the main steps in the conversion process. Builds the model, parses
	 * the XMI file, builds a PRISMModel and calls the output generator.
	 * 
	 * @param xmiFile
	 *            The next file in the list of XMI files entered by the user in
	 *            the execution command
	 * @return True, if the conversion is successful. False, if not.
	 */
	public boolean convert(String xmiFile) {
		ModelBuilder mb = new ModelBuilder();
		DiagramBuilder db = new DiagramBuilder();

		mb.buildSdmetricsModel(xmiFile);
		AbstractAggModel aggModel = db.buildAggModel(mb.getMetaModel(),
				mb.getModel());

		boolean conversionResult = false;
		if (aggModel != null) {
			PRISMModel prismModel = convertToPRISM(aggModel);
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

	/**
	 * Converts an AbstractAggModel to a PRISMModel using UnB-DALi's methods.
	 * 
	 * @param diagram
	 *            An AbstractAggModel previously built
	 * 
	 * @return A PRISMModel (UnB-DALi class)
	 */
	private PRISMModel convertToPRISM(AbstractAggModel diagram) {
		PRISMModel prismModel = null;
		long startTime = 0;
		long finishTime = 0;

		// TODO Code repetition. auto cast solution? where to put toDTMC?
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
			conversionTimeMilli = TimeUtil.getTimeMilli(startTime, finishTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prismModel;
	}

	public double getConversionTimeMilli() {
		return conversionTimeMilli;
	}
}
