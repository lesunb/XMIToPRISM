package br.unb.xmiconverter.builder;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.sdmetrics.model.MetaModel;
import com.sdmetrics.model.Model;
import com.sdmetrics.model.XMIReader;
import com.sdmetrics.model.XMITransformations;
import com.sdmetrics.util.XMLParser;

import br.unb.xmiconverter.util.FileUtil;

/**
 * Builds the SDMetrics model.
 * 
 * @author Pedro
 */
public class ModelBuilder {

	private static MetaModel metaModel = new MetaModel();
	private static XMITransformations transformation = null;
	private static Model model = null;
	private static XMLParser parser = null;
	private static XMIReader xmiReader = null;

	/**
	 * Builds the SDMetrics MetaModel and Model. The MetaModel and Model created
	 * here are accessed via get methods.
	 * 
	 * @param umlModelingTool
	 *            The UML tool name. Necessary to choose the proper XMI
	 *            Transformation.
	 * @param xmiFile
	 *            The name of the XMI file that will have its model constructed.
	 */
	public void buildSdmetricsModel(String umlModelingTool, String xmiFile) {
		FileUtil fu = new FileUtil();

		try {
			parser = new XMLParser();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		try {
			parser.parse(fu.getMetamodelPath(),
					metaModel.getSAXParserHandler());
		} catch (Exception e) {
			e.printStackTrace();
		}

		transformation = new XMITransformations(metaModel);
		try {
			parser.parse(fu.getTransformationPath(umlModelingTool),
					transformation.getSAXParserHandler());
		} catch (Exception e) {
			e.printStackTrace();
		}

		model = new Model(metaModel);
		xmiReader = new XMIReader(transformation, model);
		try {
			parser.parse(xmiFile, xmiReader);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String[] filters = { "#.java", "#.javax", "#.org.xml" };
		model.setFilter(filters, false, true);

	}

	public MetaModel getMetaModel() {
		return metaModel;
	}

	public Model getModel() {
		return model;
	}

}
