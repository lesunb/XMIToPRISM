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
public class Converter_AllFail {
	static final boolean expectedResult = false;
	static String testsFolder = System.getProperty("user.dir")
			+ "\\tests\\all-fail\\";
	static Converter converter;

	@BeforeClass
	public static void setUp() {
		converter = new Converter();
	}
	
	//17 tests
	@Parameters(name = "{index}: File: {0}")
	public static Collection<Object[]> data() {
		// @formatter:off
		return Arrays.asList(new Object[][] { { testsFolder + "papyrus_ad02.uml" },
											  { testsFolder + "papyrus_ad03.uml" },
											  { testsFolder + "papyrus_ad08.uml" },
											  { testsFolder + "papyrus_ad09.uml" },
											  { testsFolder + "papyrus_sd05.uml" },
											  { testsFolder + "astah_ad_cf-loop.xml" },
											  { testsFolder + "astah_ad_cf-no-prob.xml" },
											  { testsFolder + "astah_ad_mandatory-cf-fn.xml" },
											  { testsFolder + "astah_ad_mandatory-cf-in.xml" },
											  { testsFolder + "astah_ad_mandatory-incoming-cf-en.xml" },
											  { testsFolder + "astah_ad_mandatory-outgoing-cf-dn.xml" },
											  { testsFolder + "astah_ad_mandatory-outgoing-cf-en.xml" },
											  { testsFolder + "astah_ad_mult-cf-from-en.xml" },
											  { testsFolder + "astah_ad_mult-cf-to-dn-or-mn.xml" },
											  { testsFolder + "astah_ad_mult-cf-to-en.xml" },
											  { testsFolder + "astah_ad_mult-cf-to-fn.xml" },
											  { testsFolder + "astah_sd_lifeline-no-prob.xml" }});
	}
	//@formatter:on

	@Parameter(0)
	public String filename;

	@Test
	public void correctConversion() {
		assertEquals(expectedResult, converter.convert(filename));
	}

}
