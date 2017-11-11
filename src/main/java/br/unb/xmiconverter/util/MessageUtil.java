package br.unb.xmiconverter.util;

import java.text.DecimalFormat;

/**
 * Provides operations that display some message on the screen, such as the
 * conversion result.
 * 
 * @author Pedro
 */
public class MessageUtil {

	public void printResultOfConversion(String filePath, boolean result) {
		FileUtil fu = new FileUtil();
		System.out.print("File " + fu.getFileNameFromPath(filePath) + ": ");
		if (result) {
			System.out.print("[SUCCESS]\n");
		} else {
			System.out.print("[FAIL]\n\n");
		}
	}

	public void printCompletionMessage(double conversionTime) {
		DecimalFormat decimalFormat = new DecimalFormat("#.0");
		System.out.println("Conversion time (ms): "
				+ decimalFormat.format(conversionTime).replace(",", "."));
		System.out.println("Conversion completed.");
		System.out.println("****************************************\n");
	}

	public void printTerminated() {
		System.out.println(
				"Program Terminated. PRISM files generated for successful conversions.");
	}

}
