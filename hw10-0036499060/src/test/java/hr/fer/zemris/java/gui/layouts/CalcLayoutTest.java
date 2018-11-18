package hr.fer.zemris.java.gui.layouts;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.CalcLayoutException;
import hr.fer.zemris.java.gui.layouts.RCPosition;

@SuppressWarnings("javadoc")
public class CalcLayoutTest {
	JPanel p;

	@Before
	public void init() {
		p = new JPanel(new CalcLayout(3));
	}

	@Test(expected = CalcLayoutException.class)
	public void constraintTest1() {
		p.add(new JLabel("1"), new RCPosition(0, 2));
	}

	@Test(expected = CalcLayoutException.class)
	public void constraintTest2() {
		p.add(new JLabel("2"), new RCPosition(7, 2));
	}

	@Test(expected = CalcLayoutException.class)
	public void constraintTest3() {
		p.add(new JLabel("3"), new RCPosition(2, 0));
	}

	@Test(expected = CalcLayoutException.class)
	public void constraintTest4() {
		p.add(new JLabel("4"), new RCPosition(2, 9));
	}

	@Test(expected = CalcLayoutException.class)
	public void constraintTest5() {
		p.add(new JLabel("5"), new RCPosition(1, 3));
	}

	@Test
	public void sizeTest1() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(10, 30));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(20, 15));
		p.add(l1, new RCPosition(2, 2));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();
		Assert.assertEquals(152, dim.width);
		Assert.assertEquals(158, dim.height);
	}

	@Test
	public void sizeTest2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(108, 15));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(16, 30));
		p.add(l1, new RCPosition(1, 1));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();
		Assert.assertEquals(152, dim.width);
		Assert.assertEquals(158, dim.height);
	}
}
