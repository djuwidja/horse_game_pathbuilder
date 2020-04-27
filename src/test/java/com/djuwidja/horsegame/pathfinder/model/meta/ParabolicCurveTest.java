package com.djuwidja.horsegame.pathfinder.model.meta;

import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;
import java.util.Random;

import org.junit.Assert;
import com.djuwidja.horsegame.pathfinder.math.Vector2D;

public class ParabolicCurveTest {
	@Test
	public void testinitializeSouth() {
		Point2D startPt = new Point2D.Double(0d, 1d);
		Point2D endPt = new Point2D.Double(0d, -1d);
		Point2D vertex = new Point2D.Double(-1d, 0d);
		Point2D control = new Point2D.Double(0d, 0d);
		
		ParabolicCurve section = new ParabolicCurve(startPt, endPt, vertex, control);
		Assert.assertEquals(Direction.SOUTH, section.getDirection());
	}
	
	@Test
	public void testinitializeNorth() {
		Point2D startPt = new Point2D.Double(0d, -1d);
		Point2D endPt = new Point2D.Double(0d, 1d);
		Point2D vertex = new Point2D.Double(-1d, 0d);
		Point2D control = new Point2D.Double(0d, 0d);
		
		ParabolicCurve section = new ParabolicCurve(startPt, endPt, vertex, control);
		Assert.assertEquals(Direction.NORTH, section.getDirection());
	}
	
	@Test
	public void testGetTangentVectorSouth() {
		int sampleSize = 100;
		
		Point2D startPt = new Point2D.Double(0d, 1d);
		Point2D endPt = new Point2D.Double(0d, -1d);
		Point2D vertex = new Point2D.Double(-1d, 0d);
		Point2D control = new Point2D.Double(0d, 0d);
		
		ParabolicCurve section = new ParabolicCurve(startPt, endPt, vertex, control);
		Random rand = new Random();
		for (int i = 0; i < sampleSize; i++) {
			Point2D pt = new Point2D.Double(rand.nextDouble(), rand.nextDouble());
			Vector2D vec = section.getTangentVector(pt);
			
			Assert.assertTrue(vec.getY() > 0);
		}
	}
	
	@Test
	public void testGetTangentVectorNorth() {
		int sampleSize = 100;
		
		Point2D startPt = new Point2D.Double(0d, -1d);
		Point2D endPt = new Point2D.Double(0d, 1d);
		Point2D vertex = new Point2D.Double(-1d, 0d);
		Point2D control = new Point2D.Double(0d, 0d);
		
		ParabolicCurve section = new ParabolicCurve(startPt, endPt, vertex, control);
		Random rand = new Random();
		for (int i = 0; i < sampleSize; i++) {
			Point2D pt = new Point2D.Double(rand.nextDouble(), rand.nextDouble());
			Vector2D vec = section.getTangentVector(pt);
			
			Assert.assertTrue(vec.getY() < 0);
		}
	}
}
