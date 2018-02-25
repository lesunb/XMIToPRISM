package br.unb.xmitoprism;

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
public class Converter_AllSuccess {
	static final boolean expectedResult = true;
	static String testsFolder = System.getProperty("user.dir")
			+ "\\tests\\all-success\\";
	static Converter converter;

	@BeforeClass
	public static void setUp() {
		converter = new Converter();
	}

	// 21 tests
	@Parameters(name = "{index}: File: {0}")
	public static Collection<Object[]> data() {
		// @formatter:off
		return Arrays.asList(new Object[][] { { testsFolder + "papyrus_ad01.uml" },
											  { testsFolder + "papyrus_ad04.uml" },
											  { testsFolder + "papyrus_ad05.uml" },
											  { testsFolder + "papyrus_ad06.uml" },
											  { testsFolder + "papyrus_ad07.uml" },
											  { testsFolder + "papyrus_ad10.uml" },
											  { testsFolder + "papyrus_sd01.uml" },
											  { testsFolder + "papyrus_sd02.uml" },
											  { testsFolder + "papyrus_sd03.uml" },
											  { testsFolder + "papyrus_sd04.uml" },
											  { testsFolder + "papyrus_sd06.uml" },
											  { testsFolder + "papyrus_sd07.uml" },
											  { testsFolder + "papyrus_sd08.uml" },
											  { testsFolder + "astah_ad_all-elements.xml" },
											  { testsFolder + "astah_ad_all-elements-repetition.xml" },
											  { testsFolder + "astah_ad_in-to-fn.xml" },
											  { testsFolder + "astah_ad_one-hundred-en.xml" },
											  { testsFolder + "astah_sd_fifty-messages.xml" },
											  { testsFolder + "astah_sd_one-lifeline.xml" },
											  { testsFolder + "astah_sd_three-lifelines.xml" },
											  { testsFolder + "astah_sd_two-lifelines.xml" }});
	}
	//@formatter:on

	@Parameter(0)
	public String filename;

	@Test
	public void correctConversion() {
		assertEquals(expectedResult, converter.convert(filename));
	}

}
