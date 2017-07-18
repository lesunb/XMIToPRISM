package br.unb.xmiconverter.util;

import java.text.DecimalFormat;

public class MessageController {

	public static void print(String string) {
		System.out.println(string);
	}

	public static void printHeader(String filename) {
		System.out.println("Conversion for file " + filename);
	}

	public static void printCompletionMessage(double conversionTime) {
		DecimalFormat decimalFormat = new DecimalFormat("#.0");
		System.out.println("Conversion time (ms): " + decimalFormat.format(conversionTime).replace(",", "."));
		System.out.println("Conversion completed.");
		System.out.println("****************************************\n");
	}

}
