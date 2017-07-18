package br.unb.xmiconverter;

import org.junit.Before;
import org.junit.Test;

import org.junit.Assert;

public class TestAstahUnbdali_SDs {

	static final String umlTool = "astah";
	static String testsFolder = System.getProperty("user.dir") + "/tests/astah/unbdali/";
	ModelConverter converter;

	@Before
	public void setUp() {
		converter = ModelConverter.getInstance();
	}

	@Test
	public void testUnbdalisd01() {
		String xmiFile = testsFolder + "sd01.xml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdalisd02() {
		String xmiFile = testsFolder + "sd02.xml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdalisd03() {
		String xmiFile = testsFolder + "sd03.xml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdalisd04() {
		String xmiFile = testsFolder + "sd04.xml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdalisd05() {
		String xmiFile = testsFolder + "sd05.xml";
		Assert.assertEquals(false, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdalisd06() {
		String xmiFile = testsFolder + "sd06.xml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdalisd07() {
		String xmiFile = testsFolder + "sd07.xml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdalisd08() {
		String xmiFile = testsFolder + "sd08.xml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

}
