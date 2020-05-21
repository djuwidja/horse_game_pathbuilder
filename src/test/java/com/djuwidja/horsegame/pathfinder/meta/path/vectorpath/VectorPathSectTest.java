package com.djuwidja.horsegame.pathfinder.meta.path.vectorpath;

import org.junit.jupiter.api.Test;
import org.junit.Assert;

import com.djuwidja.horsegame.pathfinder.math.Vector2D;

import java.awt.geom.Point2D;
import java.util.Random;

public class VectorPathSectTest {
	@Test
	public void testInitialization() {
		int sampleSize = 100;
		
		Random rand = new Random();
		
		for (int i = 0; i < sampleSize; i++) {
			Point2D startPt = new Point2D.Double(rand.nextDouble(), rand.nextDouble());
			Point2D endPt = new Point2D.Double(rand.nextDouble(), rand.nextDouble());
			
			Vector2D vec = new Vector2D(startPt, endPt);
			vec.normalize();
			
			VectorPathSect section = new VectorPathSect(startPt, endPt);
			Assert.assertEquals(startPt, section.getStartPt());
			Assert.assertEquals(endPt, section.getEndPt());
			Assert.assertEquals(vec.getX(), section.getVector().getX(), 0.00000001d);
			Assert.assertEquals(vec.getY(), section.getVector().getY(), 0.00000001d);
		}
	}
	
	@Test
	public void testWithinBoundPlaneY() {
		Point2D startPt = new Point2D.Double(10d, 0d);
		Point2D endPt = new Point2D.Double(-10d, 0d);
		
		VectorPathSect sect = new VectorPathSect(startPt, endPt);
		Assert.assertTrue(sect.isPointWithinBound(new Point2D.Double(8d, 0d)));
		Assert.assertTrue(sect.isPointWithinBound(new Point2D.Double(10d, 0d)));
		Assert.assertTrue(sect.isPointWithinBound(new Point2D.Double(-10d, 0d)));
		Assert.assertFalse(sect.isPointWithinBound(new Point2D.Double(66d, 0d)));
		Assert.assertFalse(sect.isPointWithinBound(new Point2D.Double(8d, 4d)));
	}
	
	@Test
	public void testWithinBoundPlaneX() {
		Point2D startPt = new Point2D.Double(0d, 10d);
		Point2D endPt = new Point2D.Double(0d, -10d);
		
		VectorPathSect sect = new VectorPathSect(startPt, endPt);
		Assert.assertTrue(sect.isPointWithinBound(new Point2D.Double(0d, 8d)));
		Assert.assertTrue(sect.isPointWithinBound(new Point2D.Double(0d, 10d)));
		Assert.assertTrue(sect.isPointWithinBound(new Point2D.Double(0d, -10d)));
		Assert.assertFalse(sect.isPointWithinBound(new Point2D.Double(0d, 66d)));
		Assert.assertFalse(sect.isPointWithinBound(new Point2D.Double(4d, 8d)));
	}
	
	@Test
	public void testWithinBoundLine() {
		Point2D startPt = new Point2D.Double(10d, 10d);
		Point2D endPt = new Point2D.Double(-10d, -10d);
		
		VectorPathSect sect = new VectorPathSect(startPt, endPt);
		Assert.assertTrue(sect.isPointWithinBound(new Point2D.Double(8d, 8d)));
		Assert.assertTrue(sect.isPointWithinBound(new Point2D.Double(10d, 10d)));
		Assert.assertTrue(sect.isPointWithinBound(new Point2D.Double(-10d, -10d)));
		Assert.assertFalse(sect.isPointWithinBound(new Point2D.Double(11d, 11d)));
		Assert.assertFalse(sect.isPointWithinBound(new Point2D.Double(-11d, -11d)));
		Assert.assertTrue(sect.isPointWithinBound(new Point2D.Double(5d, -7d)));
	}
}
