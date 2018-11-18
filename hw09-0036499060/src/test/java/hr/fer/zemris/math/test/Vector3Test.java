package hr.fer.zemris.math.test;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.math.Vector3;

/**
 * Test class for the Vector3.
 * 
 * @author MarinoK
 */
@SuppressWarnings("javadoc")
public class Vector3Test {

	@Test
	public void normTest() {
		Vector3 v1 = new Vector3(1, 1, 1);
		Assert.assertEquals(Math.sqrt(3), v1.norm(), 1E-6);
		Vector3 v2 = new Vector3(2, 2, 2);
		Assert.assertEquals(Math.sqrt(12), v2.norm(), 1E-6);
		Vector3 v3 = new Vector3(1, 5, 0);
		Assert.assertEquals(Math.sqrt(26), v3.norm(), 1E-6);
		Vector3 v4 = new Vector3(0, 0, 1);
		Assert.assertEquals(1, v4.norm(), 1E-6);
	}

	@Test
	public void normalizationTest() {
		Vector3 v1 = new Vector3(1, 1, 1);
		Vector3 v2 = new Vector3(23, 23, 23);
		Assert.assertEquals(v1.norm(), v2.normalized().norm(), 1E-6);
	}

	@Test
	public void addTest() {
		Vector3 v1 = new Vector3(1, 1, 1);
		Vector3 v2 = v1.add(v1);
		Assert.assertEquals(v1.norm() * 2, v2.norm(), 1E-6);
	}

	@Test
	public void subTest() {
		Vector3 v1 = new Vector3(1, 1, 1);
		Vector3 v2 = v1.sub(v1);
		Assert.assertEquals(0, v2.norm(), 1E-6);
	}

	@Test
	public void dotTest() {
		Vector3 v1 = new Vector3(1, 1, 1);
		Assert.assertEquals(3, v1.dot(v1), 1E-6);
	}

	@Test
	public void crossTest() {
		Vector3 v1 = new Vector3(1, 1, 1);
		Vector3 v2 = v1.cross(v1);
		Assert.assertEquals(0, v2.norm(), 1E-6);
	}

	@Test
	public void scaleTest() {
		Vector3 v1 = new Vector3(61, 34, 521);
		Vector3 v2 = v1.scale(12);
		Assert.assertEquals(12 * v1.norm(), v2.norm(), 1E-6);
	}

	@Test(expected = IllegalArgumentException.class)
	public void cosAngleNullVectorTest() {
		Vector3 v1 = new Vector3(0, 0, 0);
		Vector3 v2 = new Vector3(1, 0, 0);
		v1.cosAngle(v2);
	}

	@Test
	public void cosAngleTest() {
		Vector3 v1 = new Vector3(1, 1, 1);
		Vector3 v2 = new Vector3(-1, -1, -1);
		Assert.assertEquals(-1, v1.cosAngle(v2), 1E-6);
	}
}
