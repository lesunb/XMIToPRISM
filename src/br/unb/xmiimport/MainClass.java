package br.unb.xmiimport;

import java.util.Scanner;

import javax.swing.JFrame;

public class MainClass extends JFrame {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {

		// metamodel, XMI transformation and XMI input files
		String metaModelDefinitionURL = "sdmetrics-metamodel-and-transformation-files/unb-dali-metamodel-elements.xml";
		String xmiTransformationURL = "sdmetrics-metamodel-and-transformation-files/modeling-tools/papyrus/PapyrusTransformations.xml";

		// TODO button to input file
		// Create a file chooser
		// final JFileChooser fc = new JFileChooser();

		// In response to a button click:
		// int returnVal = fc.showOpenDialog(aComponent);

		// File file = fc.getSelectedFile();

		// System.out.println("arquivo nome: " + file.getName());
		String xmiFile = new String();
		Scanner input = new Scanner(System.in);
		System.out.println("nome arquivo: ");
		xmiFile = input.nextLine();
		input.close();
		String xmiModelFilePath = "xmi-files/".concat(xmiFile + ".xml");

		ModelConverter converter = ModelConverter.getInstance();
		converter.convert(metaModelDefinitionURL, xmiTransformationURL, xmiModelFilePath);
	}
}
