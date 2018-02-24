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

import br.unb.xmitoprism.Converter;

@RunWith(Parameterized.class)
public class ModelConverter_AstahUnbdaliFailure {
	static final String umlTool = "astah";
	static final boolean expectedResult = false;
	static String testsFolder = System.getProperty("user.dir")
			+ "\\tests\\astah\\unbdali\\expected-failure\\";
	static Converter converter;

	@BeforeClass
	public static void setUp() {
		converter = new Converter();
	}

	@Parameters(name = "{index}: File: {0}")
	public static Collection<Object[]> data() {
		// @formatter:off
		return Arrays.asList(new Object[][] { { testsFolder + "ad02.xml" },
											  { testsFolder + "ad03.xml" },
											  { testsFolder + "ad08.xml" },
											  { testsFolder + "ad09.xml" },
											  { testsFolder + "sd05.xml" }});
	}
	//@formatter:on

	@Parameter(0)
	public String filename;

	@Test
	public void correctConversion() {
		assertEquals(expectedResult, converter.convert(umlTool, filename));
	}

}
