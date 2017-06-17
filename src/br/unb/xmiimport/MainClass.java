package br.unb.xmiimport;

import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class MainClass extends JFrame {
	public static void main(String[] args) {

		// final JFileChooser fc = new JFileChooser();

		// metamodel, XMI transformation, and XMI input files
		String metaModelDefinitionURL = "sdmetrics-metamodel-and-transformation-files/unb-dali-metamodel-elements.xml";
		String xmiTransformationURL = "sdmetrics-metamodel-and-transformation-files/modeling-tools/astah/transformation-file.xml";
		String xmiFile = new String();
		Scanner input = new Scanner(System.in);
		System.out.println("arquivo: ");
		xmiFile = input.nextLine();
		input.close();
		String xmiModelFilePath = "xmi-files/".concat(xmiFile + ".xml");

		ModelConverter converter = ModelConverter.getInstance();
		converter.convert(metaModelDefinitionURL, xmiTransformationURL, xmiModelFilePath);
	}
}
