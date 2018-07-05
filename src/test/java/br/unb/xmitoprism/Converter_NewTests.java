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
												{ testsFolder + "magicdraw_ad_success_all-elements.xml" },
												{ testsFolder + "magicdraw_ad_success_all-elements-repetition.xml" },
												{ testsFolder + "magicdraw_ad_success_in-to-fn.xml" },
												{ testsFolder + "magicdraw_ad_success_twenty-en.xml" },
												{ testsFolder + "magicdraw_sd_success_fifty-messages.xml" },
												{ testsFolder + "magicdraw_sd_success_one-lifeline.xml" },
												{ testsFolder + "magicdraw_sd_success_three-lifelines.xml" },
												{ testsFolder + "magicdraw_sd_success_two-lifelines.xml" }
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
