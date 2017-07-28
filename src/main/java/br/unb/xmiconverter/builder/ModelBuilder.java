package br.unb.xmiconverter.builder;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.sdmetrics.model.MetaModel;
import com.sdmetrics.model.Model;
import com.sdmetrics.model.XMIReader;
import com.sdmetrics.model.XMITransformations;
import com.sdmetrics.util.XMLParser;

import br.unb.xmiconverter.util.PathUtil;

public class ModelBuilder {

	private static XMLParser parser = null;
	private static MetaModel metaModel = new MetaModel();
	private static XMITransformations transformation = null;
	private static Model model = null;
	private static XMIReader xmiReader = null;

	public static void buildModel(String umlModelingTool, String xmiFile) {
		PathUtil pu = new PathUtil();

		try {
			parser = new XMLParser();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		try {
			parser.parse(pu.getMetamodelPath(), metaModel.getSAXParserHandler());
		} catch (Exception e) {
			e.printStackTrace();
		}

		transformation = new XMITransformations(metaModel);
		try {
			parser.parse(pu.getTransformationPath(umlModelingTool), transformation.getSAXParserHandler());
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

	public static MetaModel getMetaModel() {
		return metaModel;
	}

	public static Model getModel() {
		return model;
	}

}
