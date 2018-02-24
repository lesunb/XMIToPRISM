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
public class ModelConverter_PapyrusUnbdaliFailure {
	static final String umlTool = "papyrus";
	static final boolean FAIL = false;
	static String testsFolder = System.getProperty("user.dir")
			+ "\\tests\\papyrus\\unbdali\\expected-failure\\";
	static Converter converter;

	@BeforeClass
	public static void setUp() {
		converter = new Converter();
	}

	@Parameters(name = "{index}: File: {0}")
	public static Collection<Object[]> data() {
		// @formatter:off
		return Arrays.asList(new Object[][] { { testsFolder + "ad02.uml" },
											  { testsFolder + "ad03.uml" },
											  { testsFolder + "ad08.uml" },
											  { testsFolder + "ad09.uml" },
											  { testsFolder + "sd05.uml" }});
	}
	//@formatter:on

	@Parameter(0)
	public String filename;

	@Test
	public void correctConversion() {
		assertEquals(FAIL, converter.convert(umlTool, filename));
	}

}
