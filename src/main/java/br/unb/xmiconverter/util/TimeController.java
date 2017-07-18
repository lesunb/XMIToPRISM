package br.unb.xmiconverter.util;

public class TimeController {

	private static long startTime;
	private static long endTime;

	public static void setStartTimeNano() {
		startTime = System.nanoTime();
	}

	public static void setEndTimeNano() {
		endTime = System.nanoTime();
	}

	public static double getTimeInMilliseconds() {
		return (endTime - startTime) / 1000000.0;
	}
}
