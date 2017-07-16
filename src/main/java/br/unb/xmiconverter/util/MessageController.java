package br.unb.xmiconverter.util;

import java.text.DecimalFormat;

public class MessageController {

	public static void printString(String string) {
		System.out.println(string);
	}

	public static void printConversionTime(double conversionTime) {
		DecimalFormat decimalFormat = new DecimalFormat("#.0");
		System.out.println("Conversion time (ms): " + decimalFormat.format(conversionTime).replace(",", "."));
	}

	public static void printCompletionMessage() {
		System.out.println("Conversion completed.");
	}

}
