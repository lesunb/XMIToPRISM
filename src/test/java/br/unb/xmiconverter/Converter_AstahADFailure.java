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
public class Converter_AstahADFailure {
	static final String umlTool = "astah";
	static final boolean expectedResult = false;
	static String testsFolder = System.getProperty("user.dir")
			+ "\\tests\\astah\\classes\\ad\\failure\\";
	static Converter converter;

	@BeforeClass
	public static void setUp() {
		converter = new Converter();
	}

	@Parameters(name = "{index}: File: {0}")
	public static Collection<Object[]> data() {
		// @formatter:off
		return Arrays.asList(new Object[][] { { testsFolder + "cf-no-prob.xml" },
											  { testsFolder + "mandatory-cf-in.xml" },
											  { testsFolder + "mandatory-cf-fn.xml" },
											  { testsFolder + "mandatory-outgoing-cf-dn.xml" },
											  { testsFolder + "mandatory-incoming-cf-en.xml" },
											  { testsFolder + "mandatory-outgoing-cf-en.xml" },
											  { testsFolder + "mult-cf-to-fn.xml" },
											  { testsFolder + "mult-cf-from-en.xml" },
											  { testsFolder + "mult-cf-to-en.xml" },
											  { testsFolder + "mult-cf-to-dn-or-mn.xml" },
											  { testsFolder + "cf-loop.xml" }});
	}
	//@formatter:on

	@Parameter(0)
	public String filename;

	@Test
	public void correctConversion() {
		assertEquals(expectedResult, converter.convert(umlTool, filename));
	}

}
