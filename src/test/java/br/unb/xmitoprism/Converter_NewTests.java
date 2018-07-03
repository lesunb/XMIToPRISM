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
public class Converter_NewTests {
	static final boolean expectedResult = true;
	static String testsFolder = System.getProperty("user.dir")
			+ "\\tests\\new-tests\\";
	static Converter converter;

	@BeforeClass
	public static void setUp() {
		converter = new Converter();
	}

	@Parameters(name = "{index}: File: {0}")
	public static Collection<Object[]> data() {
		// @formatter:off
		return Arrays.asList(new Object[][] {
									// add new	{ testsFolder + "path"} * don't forget comma! *
												{ testsFolder + "modelio_ad_all-elements.xmi" },
												{ testsFolder + "modelio_ad_all-elements-repetition.xmi" },
												{ testsFolder + "modelio_ad_in-to-fn.xmi" },
												{ testsFolder + "modelio_ad_one-hundred-en.xmi" },
												{ testsFolder + "modelio_sd_fifty-messages.xmi" },
												{ testsFolder + "modelio_sd_one-lifeline.xmi" },
												{ testsFolder + "modelio_sd_three-lifelines.xmi" },
												{ testsFolder + "modelio_sd_two-lifelines.xmi" }
											});
	}
	//@formatter:on

	@Parameter(0)
	public String filename;

	@Test
	public void correctConversion() {
		assertEquals(expectedResult, converter.convert(filename));
	}

}
