package br.unb.xmiconverter;

import java.io.File;

import br.unb.xmiconverter.util.FileUtil;

/**
 * Programa que roda cada um dos testes 10 vezes cada. Extrai informações de
 * tempo e joga em um arquivo separado.
 * 
 */
public class Converter_Astah_10vezescada {
	static final int vezes = 10;

	private static FileUtil fu = new FileUtil();
	static final String umlTool = "astah";
	static String testsFolder = System.getProperty("user.dir")
			+ "\\tests\\astah\\all-success-files-10-times\\";
	static Converter converter = new Converter();
	static double[] data = new double[vezes];
	static int size = vezes;

	public static void main(String[] args) {
		File[] listXMIFiles = fu.getXmiList(testsFolder);
		for (File file : listXMIFiles) {
			// roda 10 vezes por arquivo
			for (int i = 0; i < vezes; i++) {
				converter.convert(umlTool, testsFolder + file.getName());
				data[i] = converter.getConversionTimeMilli();
			}
			System.out.println("Model: " + file.getName());
			System.out.println(getMean() + "ms +- " + getStdDev() + "ms\n");
		}
	}

	static double getMean() {
		double sum = 0.0;
		for (double a : data)
			sum += a;
		return sum / size;
	}

	static double getVariance() {
		double mean = getMean();
		double temp = 0;
		for (double a : data)
			temp += (mean - a) * (mean - a);
		return temp / size;
	}

	static double getStdDev() {
		return Math.sqrt(getVariance());
	}

}
