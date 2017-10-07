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

/**
 * Represents the high level steps for the conversion to happen. First the model
 * is built using the XMI file provided. Then, the AGG diagram is built with the
 * information extracted from the model and converted to PRISM language using
 * the appropriate method from UnB-DALi. It contains only one variable, to
 * evaluate the conversion time.
 * 
 * @author Pedro
 */
public class Converter {

	@SuppressWarnings("unused")
	private double conversionTimeMilli = 0;

	/**
	 * Aggregates the calls to the main steps in the conversion process. First
	 * it builds the model, then parses the XMI file, then builds a PRISMModel
	 * and finally calls the output generator.
	 * 
	 * @param umlModelingTool
	 * @param xmiFile
	 * @return True, if the conversion is successful. False, if not.
	 */
	protected boolean convert(String umlModelingTool, String xmiFile) {
		ModelBuilder mb = new ModelBuilder();
		DiagramBuilder db = new DiagramBuilder();

		mb.buildModel(umlModelingTool, xmiFile);
		AbstractAggModel diagram = db.buildDiagram(mb.getMetaModel(),
				mb.getModel());

		boolean conversionResult = false;
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

	/**
	 * Receives an AbstractAggModel diagram and converts it to a PRISMModel
	 * using UnB-DALi's methods.
	 * 
	 * @param diagram
	 * 
	 * @return A PRISMModel
	 */
	private PRISMModel convertToPRISM(AbstractAggModel diagram) {
		PRISMModel prismModel = null;
		long startTime = 0;
		long finishTime = 0;

		// TODO Avoid code repetition. auto cast solution? where to put toDTMC method?
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

}
