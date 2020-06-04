package com.djuwidja.horsegame.pathfinder.meta.curve.parabolic;

import org.junit.jupiter.api.Test;
import org.junit.Assert;

import java.awt.geom.Point2D;
import java.util.Random;

import com.djuwidja.horsegame.pathfinder.math.Vector2D;
import com.djuwidja.horsegame.pathfinder.meta.Direction;
import com.djuwidja.horsegame.pathfinder.meta.curve.TangentVector;

public class ParabolicCurveTest {
	@Test
	public void testinitializeSouth() {
		Point2D startPt = new Point2D.Double(0d, 1d);
		Point2D endPt = new Point2D.Double(0d, -1d);
		Point2D vertex = new Point2D.Double(-1d, 0d);
		Point2D controlPt = new Point2D.Double(0d, 0d);
		
		ParabolicCurve section = new ParabolicCurve(startPt, endPt, vertex, controlPt);
		Assert.assertEquals(Direction.SOUTH, section.getDirection());
	}
	
	@Test
	public void testinitializeNorth() {
		Point2D startPt = new Point2D.Double(0d, -1d);
		Point2D endPt = new Point2D.Double(0d, 1d);
		Point2D vertex = new Point2D.Double(-1d, 0d);
		Point2D controlPt = new Point2D.Double(0d, 0d);
		
		ParabolicCurve section = new ParabolicCurve(startPt, endPt, vertex, controlPt);
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
			Vector2D controlVec = new Vector2D(control.getX() - pt.getX(), control.getY() - pt.getY());
			controlVec.normalize();	
			
			TangentVector tangent = section.getTangentVector(pt, controlVec);
			
			Assert.assertTrue(tangent.getVector().getY() > 0);
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
			Vector2D controlVec = new Vector2D(control.getX() - pt.getX(), control.getY() - pt.getY());
			controlVec.normalize();	
			
			TangentVector tangent = section.getTangentVector(pt, controlVec);
			
			Assert.assertTrue(tangent.getVector().getY() < 0);
		}
	}
}
