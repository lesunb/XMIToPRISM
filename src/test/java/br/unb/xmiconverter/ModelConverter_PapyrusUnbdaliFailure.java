package br.unb.xmiconverter;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ModelConverter_PapyrusUnbdaliFailure {
	static final String umlTool = "papyrus";
	static final boolean expectedResult = false;
	static String testsFolder = System.getProperty("user.dir") + "\\tests\\papyrus\\unbdali\\expected-failure\\";
	static ModelConverter converter;

	@BeforeClass
	public static void setUp() {
		converter = ModelConverter.getInstance();
	}

	@Parameters(name = "{index}: File: {0}")
	public static Collection<Object[]> data() {
		// @formatter:off
		// TODO PREENCHER OS NOMES
		return Arrays.asList(new Object[][] { { testsFolder + "ad01.xml" },
											  { testsFolder + "ad04.xml" },
											  { testsFolder + "ad05.xml" },
											  { testsFolder + "ad06.xml" },
											  { testsFolder + "ad07.xml" },
											  { testsFolder + "ad10.xml" },
											  { testsFolder + "sd01.xml" },
											  { testsFolder + "sd02.xml" },
											  { testsFolder + "sd03.xml" },
											  { testsFolder + "sd04.xml" },
											  { testsFolder + "sd06.xml" },
											  { testsFolder + "sd07.xml" },
											  { testsFolder + "sd08.xml" }});
	}
	//@formatter:on

	@Parameter(0)
	public String filename;

	@Test
	public void correctConversion() {
		assertEquals(expectedResult, converter.convert(umlTool, filename));
	}

}
