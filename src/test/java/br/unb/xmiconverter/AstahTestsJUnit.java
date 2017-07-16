package br.unb.xmiconverter;

import org.junit.Before;
import org.junit.Test;

import org.junit.Assert;

public class AstahTestsJUnit {

	static final String umlTool = "astah";
	static String testsFolder = System.getProperty("user.dir") + "/src/test/java/unbdali-tests-astah/";
	TestModelConverter converter;

	@Before
	public void setUp() {
		converter = TestModelConverter.getInstance();
	}

	@Test
	public void testUnbdaliad01() {
		String xmiFile = testsFolder + "ad-t01.xml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad02() {
		String xmiFile = testsFolder + "ad-t02.xml";
		Assert.assertEquals(false, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad03() {
		String xmiFile = testsFolder + "ad-t03.xml";
		Assert.assertEquals(false, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad04() {
		String xmiFile = testsFolder + "ad-t04.xml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad05() {
		String xmiFile = testsFolder + "ad-t05.xml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad06() {
		String xmiFile = testsFolder + "ad-t06.xml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad07() {
		String xmiFile = testsFolder + "ad-t07.xml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad08() {
		String xmiFile = testsFolder + "ad-t08.xml";
		Assert.assertEquals(false, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad09() {
		String xmiFile = testsFolder + "ad-t09.xml";
		Assert.assertEquals(false, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad10() {
		String xmiFile = testsFolder + "ad-t10.xml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}
}
