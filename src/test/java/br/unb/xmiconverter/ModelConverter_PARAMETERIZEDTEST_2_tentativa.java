package br.unb.xmiconverter;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import br.unb.xmiconverter.util.FileUtil;

// TODO Problem of iteration of a list going as arguments to vector
@RunWith(Parameterized.class)
public class ModelConverter_PARAMETERIZEDTEST_2_tentativa {

	static final String umlTool = "astah";
	static final boolean expectedResult = true;
	static String testsFolder = System.getProperty("user.dir") + "\\tests\\astah\\unbdali\\expected-success\\";
	static Converter converter;
	static File[] listOfFiles;

	@BeforeClass
	public static void setUp() {

		// get list of names of files that are XMI format
		File directory = new File(System.getProperty(testsFolder));
		FilenameFilter textFilter = new FilenameFilter() {
			public boolean accept(File directory, String name) {
				String lowercaseName = name.toLowerCase();
				if (Arrays.stream(FileUtil.getAcceptedFileExtensions()).parallel().anyMatch(lowercaseName::endsWith)) {
					return true;
				} else {
					return false;
				}
			}
		};
		listOfFiles = directory.listFiles(textFilter);

		// initialize the converter to begin tests
		converter = Converter.getInstance();
	}

	@Parameters(name = "{index}: File: {0}")
	public static Collection<Object[]> data() {
		Object[][] array = new Object[listOfFiles.length][listOfFiles.length];

		// fill the array with the file names
		for (int i = 0; i < listOfFiles.length; i++) {
			array[0][i] = listOfFiles[i].getName();
		}

		return Arrays.asList(array);
	}

	@Parameter(0)
	public String filename;

	@Test
	public void correctConversion() {
		assertEquals(expectedResult, converter.convert(umlTool, filename));
	}

}
