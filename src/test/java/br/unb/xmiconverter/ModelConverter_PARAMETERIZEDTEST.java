package br.unb.xmiconverter;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import br.unb.xmiconverter.util.FileController;

// TODO Got to make it work...
@RunWith(Parameterized.class)
public class ModelConverter_PARAMETERIZEDTEST {
	static final String umlTool = "astah";
	static String testsFolder = System.getProperty("user.dir") + "/tests/astah/unbdali/expected-success/";
	static ArrayList<String> xmiFiles = new ArrayList<>();
	static ModelConverter converter;

	@BeforeClass
	public static void setUp() {
		File folder = new File(testsFolder);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile() && FileController.isXmi(listOfFiles[i].getName())) {
				xmiFiles.add(testsFolder + listOfFiles[i].getName());
			}
		}
		converter = ModelConverter.getInstance();
	}

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { true, "C:/Users/Pedro/workspace/XMI-PRISM-Converter/tests/astah/unbdali/expected-success/ad01.xml" } });
	}

	@Parameter(0)
	public boolean expectedResult;

	@Parameter(1)
	public String filename;

	@Test
	public void correctConversion() {
		assertEquals(expectedResult, converter.convert(umlTool, filename));
	}

}
