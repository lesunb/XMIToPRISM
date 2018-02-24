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
public class ModelConverter_PapyrusUnbdaliSuccess {
	static final String umlTool = "papyrus";
	static final boolean SUCCESS = true;
	static String testsFolder = System.getProperty("user.dir")
			+ "\\tests\\papyrus\\unbdali\\expected-success\\";
	static Converter converter;

	@BeforeClass
	public static void setUp() {
		converter = new Converter();
	}

	@Parameters(name = "{index}: File: {0}")
	public static Collection<Object[]> data() {
		// @formatter:off
		return Arrays.asList(new Object[][] { { testsFolder + "ad01.uml" },
											  { testsFolder + "ad04.uml" },
											  { testsFolder + "ad05.uml" },
											  { testsFolder + "ad06.uml" },
											  { testsFolder + "ad07.uml" },
											  { testsFolder + "ad10.uml" },
											  { testsFolder + "sd01.uml" },
											  { testsFolder + "sd02.uml" },
											  { testsFolder + "sd03.uml" },
											  { testsFolder + "sd04.uml" },
											  { testsFolder + "sd06.uml" },
											  { testsFolder + "sd07.uml" },
											  { testsFolder + "sd08.uml" }});
	}
	//@formatter:on

	@Parameter(0)
	public String filename;

	@Test
	public void correctConversion() {
		assertEquals(SUCCESS, converter.convert(umlTool, filename));
	}

}
