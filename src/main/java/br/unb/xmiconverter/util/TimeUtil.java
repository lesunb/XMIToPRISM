package br.unb.xmiconverter.util;

public class TimeUtil {

	public Long getTimeNano() {
		return System.nanoTime();
	}

	public double getTimeInMilliseconds(long start, long finish) {
		return (finish - start) / 1000000.0;
	}

}
