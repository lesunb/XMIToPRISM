package br.unb.xmiconverter;

import org.junit.Before;
import org.junit.Test;

import org.junit.Assert;

public class ModelConverter_PapyrusUnbdaliFailure {

	static final String umlTool = "papyrus";
	static String testsFolder = System.getProperty("user.dir") + "/tests/papyrus/unbdali/";
	ModelConverter converter;

	@Before
	public void setUp() {
		converter = ModelConverter.getInstance();
	}

	@Test
	public void testUnbdaliad01() {
		String xmiFile = testsFolder + "activity001.uml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad02() {
		String xmiFile = testsFolder + "activity002.uml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad03() {
		String xmiFile = testsFolder + "activity003.uml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad04() {
		String xmiFile = testsFolder + "activity004.uml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad05() {
		String xmiFile = testsFolder + "activity005.uml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad06() {
		String xmiFile = testsFolder + "activity006.uml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

}
