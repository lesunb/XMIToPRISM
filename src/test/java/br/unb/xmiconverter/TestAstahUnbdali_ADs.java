package br.unb.xmiconverter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestAstahUnbdali_ADs {

	// final String testsFolder = System.getProperty("user.dir") + "/tests/astah/unbdali/";
	// File folder = new File(testsFolder);
	// File[] listOfFiles = folder.listFiles();
	//
	// for (int i = 0; i < listOfFiles.length; i++) {
	// if (listOfFiles[i].isFile()) {
	// System.out.println("File " + listOfFiles[i].getName());
	// } else if (listOfFiles[i].isDirectory()) {
	// System.out.println("Directory " + listOfFiles[i].getName());
	// }
	// }

	static final String umlTool = "astah";
	static String testsFolder = System.getProperty("user.dir") + "/tests/astah/unbdali/";
	ModelConverter converter;

	@Before
	public void setUp() {
		converter = ModelConverter.getInstance();
	}

	@Test
	public void testUnbdaliad01() {
		String xmiFile = testsFolder + "ad01.xml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad02() {
		String xmiFile = testsFolder + "ad02.xml";
		Assert.assertEquals(false, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad03() {
		String xmiFile = testsFolder + "ad03.xml";
		Assert.assertEquals(false, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad04() {
		String xmiFile = testsFolder + "ad04.xml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad05() {
		String xmiFile = testsFolder + "ad05.xml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad06() {
		String xmiFile = testsFolder + "ad06.xml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad07() {
		String xmiFile = testsFolder + "ad07.xml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad08() {
		String xmiFile = testsFolder + "ad08.xml";
		Assert.assertEquals(false, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad09() {
		String xmiFile = testsFolder + "ad09.xml";
		Assert.assertEquals(false, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad10() {
		String xmiFile = testsFolder + "ad10.xml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}
}
