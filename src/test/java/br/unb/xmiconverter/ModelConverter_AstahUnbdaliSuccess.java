package br.unb.xmiconverter;

import org.junit.Before;
import org.junit.Test;

import org.junit.Assert;

public class ModelConverter_AstahUnbdaliSuccess {

	static final String umlTool = "astah";
	static String testsFolder = System.getProperty("user.dir") + "/tests/astah/unbdali/expected-success/";
	boolean expectedResult = true;
	ModelConverter converter;

	@Before
	public void setUp() {
		converter = ModelConverter.getInstance();
	}

	@Test
	public void testUnbdaliSuccess01() {
		String xmiFile = testsFolder + "ad01.xml";
		Assert.assertEquals(expectedResult, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliSuccess02() {
		String xmiFile = testsFolder + "ad04.xml";
		Assert.assertEquals(expectedResult, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliSuccess03() {
		String xmiFile = testsFolder + "ad05.xml";
		Assert.assertEquals(expectedResult, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliSuccess04() {
		String xmiFile = testsFolder + "ad06.xml";
		Assert.assertEquals(expectedResult, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliSuccess05() {
		String xmiFile = testsFolder + "ad07.xml";
		Assert.assertEquals(expectedResult, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliSuccess06() {
		String xmiFile = testsFolder + "ad10.xml";
		Assert.assertEquals(expectedResult, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliSuccess07() {
		String xmiFile = testsFolder + "sd01.xml";
		Assert.assertEquals(expectedResult, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliSuccess08() {
		String xmiFile = testsFolder + "sd02.xml";
		Assert.assertEquals(expectedResult, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliSuccess09() {
		String xmiFile = testsFolder + "sd03.xml";
		Assert.assertEquals(expectedResult, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliSuccess10() {
		String xmiFile = testsFolder + "sd04.xml";
		Assert.assertEquals(expectedResult, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliSuccess11() {
		String xmiFile = testsFolder + "sd06.xml";
		Assert.assertEquals(expectedResult, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliSuccess12() {
		String xmiFile = testsFolder + "sd07.xml";
		Assert.assertEquals(expectedResult, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliSuccess13() {
		String xmiFile = testsFolder + "sd08.xml";
		Assert.assertEquals(expectedResult, converter.convert(umlTool, xmiFile));
	}

}
