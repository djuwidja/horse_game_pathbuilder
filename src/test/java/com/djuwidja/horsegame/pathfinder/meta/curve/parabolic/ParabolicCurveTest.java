package com.djuwidja.horsegame.pathfinder.meta.curve.parabolic;

import org.junit.jupiter.api.Test;
import org.junit.Assert;

import java.awt.geom.Point2D;
import java.util.Random;

import com.djuwidja.horsegame.pathfinder.math.Vector2D;
import com.djuwidja.horsegame.pathfinder.meta.Direction;

public class ParabolicCurveTest {
	@Test
	public void testinitializeSouth() {
		Point2D startPt = new Point2D.Double(0d, 1d);
		Point2D endPt = new Point2D.Double(0d, -1d);
		Point2D vertex = new Point2D.Double(-1d, 0d);
		
		ParabolicCurve section = new ParabolicCurve(startPt, endPt, vertex);
		Assert.assertEquals(Direction.SOUTH, section.getDirection());
	}
	
	@Test
	public void testinitializeNorth() {
		Point2D startPt = new Point2D.Double(0d, -1d);
		Point2D endPt = new Point2D.Double(0d, 1d);
		Point2D vertex = new Point2D.Double(-1d, 0d);
		
		ParabolicCurve section = new ParabolicCurve(startPt, endPt, vertex);
		Assert.assertEquals(Direction.NORTH, section.getDirection());
	}
	
	@Test
	public void testGetTangentVectorSouth() {
		int sampleSize = 100;
		
		Point2D startPt = new Point2D.Double(0d, 1d);
		Point2D endPt = new Point2D.Double(0d, -1d);
		Point2D vertex = new Point2D.Double(-1d, 0d);
		Point2D control = new Point2D.Double(0d, 0d);
		
		ParabolicCurve section = new ParabolicCurve(startPt, endPt, vertex);
		Random rand = new Random();
		for (int i = 0; i < sampleSize; i++) {
			Point2D pt = new Point2D.Double(rand.nextDouble(), rand.nextDouble());
			Vector2D normal = new Vector2D(pt.getX() - control.getX(), pt.getY() - control.getY());
			normal.normalize();
			Vector2D vec = section.getTangentVector(pt, normal);
			
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
		
		ParabolicCurve section = new ParabolicCurve(startPt, endPt, vertex);
		Random rand = new Random();
		for (int i = 0; i < sampleSize; i++) {
			Point2D pt = new Point2D.Double(rand.nextDouble(), rand.nextDouble());
			Vector2D normal = new Vector2D(pt.getX() - control.getX(), pt.getY() - control.getY());
			normal.normalize();
			Vector2D vec = section.getTangentVector(pt, normal);
			
			Assert.assertTrue(vec.getY() < 0);
		}
	}
}
