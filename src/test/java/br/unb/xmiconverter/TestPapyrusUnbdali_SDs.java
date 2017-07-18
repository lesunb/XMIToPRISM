package br.unb.xmiconverter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestPapyrusUnbdali_SDs {

	static final String umlTool = "papyrus";
	static String testsFolder = System.getProperty("user.dir") + "/tests/papyrus/unbdali/";
	ModelConverter converter;

	@Before
	public void setUp() {
		converter = ModelConverter.getInstance();
	}

	@Test
	public void testUnbdaliad01() {
		String xmiFile = testsFolder + "sequence001.uml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad02() {
		String xmiFile = testsFolder + "sequence002.uml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad03() {
		String xmiFile = testsFolder + "sequence003.uml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad04() {
		String xmiFile = testsFolder + "sequence004.uml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad05() {
		String xmiFile = testsFolder + "sequence005.uml";
		Assert.assertEquals(false, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad06() {
		String xmiFile = testsFolder + "sequence006.uml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad07() {
		String xmiFile = testsFolder + "sequence007.uml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

	@Test
	public void testUnbdaliad08() {
		String xmiFile = testsFolder + "sequence008.uml";
		Assert.assertEquals(true, converter.convert(umlTool, xmiFile));
	}

}
