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
	public void testUnbdaliad01() {
		String xmiFile = testsFolder + "sd-t01.xml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad02() {
		String xmiFile = testsFolder + "sd-t02.xml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad03() {
		String xmiFile = testsFolder + "sd-t03.xml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad04() {
		String xmiFile = testsFolder + "sd-t04.xml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad05() {
		String xmiFile = testsFolder + "sd-t05.xml";
		Assert.assertEquals(false, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad06() {
		String xmiFile = testsFolder + "sd-t06.xml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad07() {
		String xmiFile = testsFolder + "sd-t07.xml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad08() {
		String xmiFile = testsFolder + "sd-t08.xml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

}
