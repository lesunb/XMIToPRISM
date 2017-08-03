package br.unb.xmiconverter.util;

/**
 * Provides simple time methods to measure the conversion.
 * 
 * @author Pedro
 */
public class TimeUtil {

	public static Long getTimeNano() {
		return System.nanoTime();
	}

	public static double getTimeMilli(long start, long finish) {
		return (finish - start) / 1000000.0;
	}

}
