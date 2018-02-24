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
public class Converter_AstahADSuccess {
	static final String umlTool = "astah";
	static final boolean expectedResult = true;
	static String testsFolder = System.getProperty("user.dir")
			+ "\\tests\\astah\\classes\\ad\\success\\";
	static Converter converter;

	@BeforeClass
	public static void setUp() {
		converter = new Converter();
	}

	@Parameters(name = "{index}: File: {0}")
	public static Collection<Object[]> data() {
		// @formatter:off
		return Arrays.asList(new Object[][] { { testsFolder + "in-to-fn.xml" },
											  { testsFolder + "all-elements.xml" },
											  { testsFolder + "all-elements-repetition.xml" },
											  { testsFolder + "one-hundred-en.xml" }});
	}
	//@formatter:on

	@Parameter(0)
	public String filename;

	@Test
	public void correctConversion() {
		assertEquals(expectedResult, converter.convert(umlTool, filename));
	}

}
