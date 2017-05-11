package br.unb.xmiimport;

// how to parse an XMI file and access the model elements

// step 1: Required imports
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.sdmetrics.model.MetaModel;
import com.sdmetrics.model.MetaModelElement;
import com.sdmetrics.model.Model;
import com.sdmetrics.model.ModelElement;
import com.sdmetrics.model.XMIReader;
import com.sdmetrics.model.XMITransformations;
import com.sdmetrics.util.XMLParser;

public class TestClass {
	public static void main(String[] args) {
		
			// step 2: Have your metamodel, XMI transformation, and XMI input files ready:
			String metaModelURL = "test/default-metamodel2.xml";	// metamodel definition to use
			String xmiTransURL = "test/default-xmiTrans2_0.xml";	// XMI tranformations to use
			String xmiFile = "test/AD_1diag-1action.xml";	 // XMI file with the UML model
			
			// step 3: Read the metamodel
			// You do not have to use the SAX parser provided by class XMLParser, you may just as well use a org.xml.sax.XMLReader that you created yourself.
			XMLParser parser = null;
			try {
				parser = new XMLParser();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
			
			MetaModel metaModel = new MetaModel();
			try {
				parser.parse(metaModelURL, metaModel.getSAXParserHandler());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// step 4: Read the XMI transformation file
			XMITransformations transformation = new XMITransformations(metaModel);
			try {
				parser.parse(xmiTransURL, transformation.getSAXParserHandler());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// step 5: Read the XMI file with the UML model
			Model model = new Model(metaModel);
			XMIReader xmiReader = new XMIReader(transformation, model);
			try {
				parser.parse(xmiFile, xmiReader);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// step 6: Optionally, specify element filters to get rid of standard libraries or 3rd party APIs
			// At this point, you can already start calculating metrics for the elements in the model. The tutorial for package com.sdmetrics.metrics describes how. The remainder of this tutorial shows how to access the elements in the model.
			String[] filters = { "#.java", "#.javax", "#.org.xml" };
			model.setFilter(filters, false, true);
			
			// step 7: Access the UML model
			// The following example writes all model elements accepted by the element filter to the console, along with the values of their attributes.
			
			// iterate over all model element types in the metamodel
			for (MetaModelElement type : metaModel) {
			   System.out.println("Elements of type: " + type.getName());
			   
			   // iterate over all model elements of the current type
			   List<ModelElement> elements = model.getAcceptedElements(type);
			   for (ModelElement me : elements) {
			      System.out.println("  Element: " + me.getFullName() + " ");
			      
			      // write out the value of each attribute of the element
			      Collection<String> attributeNames = type.getAttributeNames();
			      for (String attr : attributeNames) {
			         System.out.print("     Attribute '" + attr);
			         if (type.isSetAttribute(attr))
			            System.out.println("' has set value "
			                  + me.getSetAttribute(attr));
			         else if (type.isRefAttribute(attr)) {
			            System.out.print("' references ");
			            ModelElement referenced = me.getRefAttribute(attr);
			            System.out.println((referenced == null) ? "nothing"
			                  : referenced.getFullName());
			         } else
			            System.out.println("' has value: "
			                  + me.getPlainAttribute(attr));
			      }
			   }
			}
		
	}
}
