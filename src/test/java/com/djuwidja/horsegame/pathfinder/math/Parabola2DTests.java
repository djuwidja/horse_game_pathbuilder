package com.djuwidja.horsegame.pathfinder.math;

import java.awt.geom.Point2D;

import org.junit.jupiter.api.Test;
import org.junit.Assert;

public class Parabola2DTests {
	@Test
	public void testY2M1() {
		// test x = y^2 - 1;
		
		Point2D pt1 = new Point2D.Double(0d, 1d);
		Point2D pt2 = new Point2D.Double(0d, -1d);
		Point2D vertex = new Point2D.Double(-1d, 0d);
		Point2D control = new Point2D.Double(0d, 0d);
		
		Parabola2D p = new Parabola2D(pt1, pt2, vertex, control);
		Assert.assertEquals(1d, p.getA(), 0.001d);
		Assert.assertEquals(0d, p.getB(), 0.001d);
		Assert.assertEquals(-1d, p.getC(), 0.001d);	
		
		double pt1x = p.getX(1d);
		Assert.assertEquals(0d, pt1x, 0.001d);
		
		double pt2x = p.getX(-1d);
		Assert.assertEquals(0d, pt2x, 0.001d);
		
		double vx = p.getX(0d);
		Assert.assertEquals(-1d, vx, 0.001d);
		
		Point2D intP1 = p.getInteractionCtrlPt(new Point2D.Double(0d, -5d));
		Assert.assertEquals(0d, intP1.getX(), 0.001d);
		Assert.assertEquals(-1d, intP1.getY(), 0.001d);
		
		Point2D intP2 = p.getInteractionCtrlPt(new Point2D.Double(0d, 12d));
		Assert.assertEquals(0d, intP2.getX(), 0.001d);
		Assert.assertEquals(1d, intP2.getY(), 0.001d);
		
		Point2D intP3 = p.getInteractionCtrlPt(new Point2D.Double(-66d, 0d));
		Assert.assertEquals(-1d, intP3.getX(), 0.001d);
		Assert.assertEquals(0d, intP3.getY(), 0.001d);

		Assert.assertEquals(-2d, p.getTangentSlope(intP1.getY()), 0.001d);
		Assert.assertEquals(2d, p.getTangentSlope(intP2.getY()), 0.001d);
		Assert.assertEquals(0d, p.getTangentSlope(intP3.getY()), 0.001d);
	}
	
	@Test
	public void testNY2P1() {
		// test x = -y^2 + 1;
		Point2D pt1 = new Point2D.Double(0d, 1d);
		Point2D pt2 = new Point2D.Double(0d, -1d);
		Point2D vertex = new Point2D.Double(1d, 0d);
		Point2D control = new Point2D.Double(0d, 0d);
		
		Parabola2D p = new Parabola2D(pt1, pt2, vertex, control);		
		Assert.assertEquals(-1d, p.getA(), 0.001d);
		Assert.assertEquals(0d, p.getB(), 0.001d);
		Assert.assertEquals(1d, p.getC(), 0.001d);		
		
		double pt1x = p.getX(1d);
		Assert.assertEquals(0d, pt1x, 0.001d);
		
		double pt2x = p.getX(-1d);
		Assert.assertEquals(0d, pt2x, 0.001d);
		
		double vx = p.getX(0d);
		Assert.assertEquals(1d, vx, 0.001d);
		
		Point2D intP1 = p.getInteractionCtrlPt(new Point2D.Double(0d, -5d));
		Assert.assertEquals(0d, intP1.getX(), 0.001d);
		Assert.assertEquals(-1d, intP1.getY(), 0.001d);
		
		Point2D intP2 = p.getInteractionCtrlPt(new Point2D.Double(0d, 12d));
		Assert.assertEquals(0d, intP2.getX(), 0.001d);
		Assert.assertEquals(1d, intP2.getY(), 0.001d);
		
		Point2D intP3 = p.getInteractionCtrlPt(new Point2D.Double(66d, 0d));
		Assert.assertEquals(1d, intP3.getX(), 0.001d);
		Assert.assertEquals(0d, intP3.getY(), 0.001d);

		Assert.assertEquals(2d, p.getTangentSlope(intP1.getY()), 0.001d);
		Assert.assertEquals(-2d, p.getTangentSlope(intP2.getY()), 0.001d);
		Assert.assertEquals(0d, p.getTangentSlope(intP3.getY()), 0.001d);
	}
	
	@Test
	public void test6Y2P4YP8() {
		// test x = 6y^2 + 4y + 8
		Point2D pt1 = new Point2D.Double(178d, 5d);
		Point2D pt2 = new Point2D.Double(138d, -5d);
		Point2D vertex = new Point2D.Double(8d, 0d);
		Point2D control = new Point2D.Double(0d, 0d);
		Parabola2D p = new Parabola2D(pt1, pt2, vertex, control);		
		Assert.assertEquals(6d, p.getA(), 0.001d);
		Assert.assertEquals(4d, p.getB(), 0.001d);
		Assert.assertEquals(8d, p.getC(), 0.001d);	
	}
}
