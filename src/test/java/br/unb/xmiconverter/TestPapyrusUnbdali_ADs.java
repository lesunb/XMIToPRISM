package br.unb.xmiconverter;

import org.junit.Before;
import org.junit.Test;

import org.junit.Assert;

public class TestPapyrusUnbdali_ADs {

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

	// TODO Jefferson

}
