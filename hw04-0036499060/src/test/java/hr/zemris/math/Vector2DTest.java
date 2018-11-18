package hr.zemris.math;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.math.Vector2D;

/**
 * Test class for the Vector2D.
 * 
 * @author MarinoK
 */
public class Vector2DTest {
	Vector2D vector;

	@Before
	public void initialization() {
		vector = new Vector2D(3, 5);
	}

	@Test
	public void translateTest() {
		Vector2D offset = new Vector2D(1, 1);
		vector.translate(offset);
		Assert.assertEquals(vector.getX(), 4, 0.001);
		Assert.assertEquals(vector.getY(), 6, 0.001);
	}

	@Test
	public void translatedTest() {
		Vector2D offset = new Vector2D(2, 3);
		Vector2D vector2 = vector.translated(offset);
		Assert.assertEquals(vector2.getX(), 5, 0.001);
		Assert.assertEquals(vector2.getY(), 8, 0.001);
	}

	@Test
	public void rotateTest() {
		vector.rotate(Math.PI);
		Assert.assertEquals(vector.getX(), -3, 0.001);
		Assert.assertEquals(vector.getY(), -5, 0.001);
	}

	@Test
	public void rotatedTest() {
		Vector2D vector2 = vector.rotated(Math.PI);
		Assert.assertEquals(vector2.getX(), -3, 0.001);
		Assert.assertEquals(vector2.getY(), -5, 0.001);
	}

	@Test
	public void copyTest() {
		Vector2D vector2 = vector.copy();
		Assert.assertEquals(vector2.getX(), vector.getX(), 0.001);
		Assert.assertEquals(vector2.getY(), vector.getY(), 0.001);
	}

	@Test
	public void scaleTest() {
		vector.scale(0.5);
		Assert.assertEquals(vector.getX(), 1.5, 0.001);
		Assert.assertEquals(vector.getY(), 2.5, 0.001);
	}

	@Test
	public void scaledTest() {
		Vector2D vector2 = vector.scaled(-2);
		Assert.assertEquals(vector2.getX(), -6, 0.001);
		Assert.assertEquals(vector2.getY(), -10, 0.001);
	}

}
