package br.unb.xmiconverter.util;

public class TimeUtil {

	public static Long getTimeNano() {
		return System.nanoTime();
	}

	public static double getTimeInMilliseconds(long start, long finish) {
		return (finish - start) / 1000000.0;
	}

}
