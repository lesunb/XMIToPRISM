package br.unb.xmiconverter.util;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MessageUtil {

	private static final Map<Integer, String> toolsMap = createMap();
	private static final Integer NUMBER_SUPPORTED_TOOLS = 2;

	public static String startMenu() {
		MessageUtil.printToolOptionMenu();
		Scanner reader = new Scanner(System.in);
		Integer option = reader.nextInt();
		while (option < 1 || option > NUMBER_SUPPORTED_TOOLS) {
			MessageUtil.printOptionNotExistent(NUMBER_SUPPORTED_TOOLS);
			MessageUtil.printToolOptionMenu();
			option = reader.nextInt();
		}
		reader.close();
		return MessageUtil.getTool(option);
	}

	private static Map<Integer, String> createMap() {
		Map<Integer, String> toolsMap = new HashMap<Integer, String>();
		toolsMap.put(1, "astah");
		toolsMap.put(2, "papyrus");
		return toolsMap;
	}

	public static void printHeader(String filename) {
		System.out.print("Conversion for file " + filename + ": ");
	}

	public static void printResultOfConversion(boolean result) {
		if (result) {
			System.out.print("[SUCCESS]\n");
		} else {
			System.out.print("[FAIL]\n");
		}
	}

	public static void printCompletionMessage(double conversionTime) {
		DecimalFormat decimalFormat = new DecimalFormat("#.0");
		System.out.println("Conversion time (ms): " + decimalFormat.format(conversionTime).replace(",", "."));
		System.out.println("Conversion completed.");
		System.out.println("****************************************\n");
	}

	public static void printToolOptionMenu() {
		System.out.println("Choose the tool where the XMI file was exported from.\n");
		System.out.println("1. Astah Professional");
		System.out.println("2. Papyrus");
		System.out.print("Enter a number: ");
	}

	public static void printOptionNotExistent(Integer numberOfTools) {
		System.out.println("\nYou must select a number between 1 and " + numberOfTools + "!. Try again.");
	}

	public static String getTool(Integer option) {
		String tool = toolsMap.get(option);
		return tool;
	}


}
