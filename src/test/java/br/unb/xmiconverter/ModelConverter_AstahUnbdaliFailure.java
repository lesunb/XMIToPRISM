package br.unb.xmiconverter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ModelConverter_AstahUnbdaliFailure {

	static final String umlTool = "astah";
	static String testsFolder = System.getProperty("user.dir") + "/tests/astah/unbdali/expected-failure/";
	boolean expectedResult = false;
	ModelConverter converter;

	@Before
	public void setUp() {
		converter = ModelConverter.getInstance();
	}

	@Test
	public void testUnbdaliFailure01() {
		String xmiFile = testsFolder + "ad02.xml";
		Assert.assertEquals(expectedResult, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliFailure02() {
		String xmiFile = testsFolder + "ad03.xml";
		Assert.assertEquals(expectedResult, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliFailure03() {
		String xmiFile = testsFolder + "ad08.xml";
		Assert.assertEquals(expectedResult, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliFailure04() {
		String xmiFile = testsFolder + "sd05.xml";
		Assert.assertEquals(expectedResult, converter.convert(umlTool, xmiFile));
	}

}