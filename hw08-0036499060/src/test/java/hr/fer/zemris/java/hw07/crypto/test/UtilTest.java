package hr.fer.zemris.java.hw07.crypto.test;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.hw07.crypto.Util;

@SuppressWarnings("javadoc")
public class UtilTest {
	
	@Test
	public void hexToByteTest1() {
		byte[] array = Util.hextobyte("01aE22");
		Assert.assertEquals(1, array[0]);
		Assert.assertEquals(-82, array[1]);
		Assert.assertEquals(34, array[2]);
	}
	@Test
	public void hexToByteTest2() {
		byte[] array = Util.hextobyte("a0f008");
		Assert.assertEquals(-96, array[0]);
		Assert.assertEquals(-16, array[1]);
		Assert.assertEquals(8, array[2]);
	}
	@Test
	public void hexToByteTest3() {
		byte[] array = Util.hextobyte("AaB0d1");
		Assert.assertEquals(-86, array[0]);
		Assert.assertEquals(-80, array[1]);
		Assert.assertEquals(-47, array[2]);
	}

	@Test
	public void hexToByteToHexTest1() {
		String hexBefore = "2534A56976BEAF5E3B68A4A71AEA1811A6CB305CF4C72AF86E3D7AC631E097C2";
		byte[] array = Util.hextobyte(hexBefore);
		String hexAfter = Util.bytetohex(array);
		Assert.assertEquals(hexBefore.toLowerCase(),hexAfter);
	}
	
	@Test
	public void hexToByteToHexTest2() {
		String hexBefore = "E8E29988C818081EC9CF9319BD885C8979E6F1EB3DFF7AB5EA98C8B8ED551143";
		byte[] array = Util.hextobyte(hexBefore);
		String hexAfter = Util.bytetohex(array);
		Assert.assertEquals(hexBefore.toLowerCase(),hexAfter);
	}
	
	@Test
	public void hexToByteToHexTest3() {
		String hexBefore = "C6D121BDB2B78C198A706B199464B26F55D388FCE2CB683B37903A7D09D6BC5E";
		byte[] array = Util.hextobyte(hexBefore);
		String hexAfter = Util.bytetohex(array);
		Assert.assertEquals(hexBefore.toLowerCase(),hexAfter);
	}
	
	@Test
	public void hexToByteToHexTest4() {
		String hexBefore = "94E6CE8E12062AF22EA64688CBCFCAA5C897BC011E85C222B4B2C49316A4B6D9";
		byte[] array = Util.hextobyte(hexBefore);
		String hexAfter = Util.bytetohex(array);
		Assert.assertEquals(hexBefore.toLowerCase(),hexAfter);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void unevenStringTest() {
		Util.hextobyte("1aE22");
	}
	
}
