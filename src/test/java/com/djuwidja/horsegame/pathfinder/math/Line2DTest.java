package com.djuwidja.horsegame.pathfinder.math;

import java.awt.geom.Point2D;
import java.util.Random;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.djuwidja.testbase.ExceptionFacilitator;
import com.djuwidja.testbase.TestBase;

public class Line2DTest extends TestBase {
	@Test
	public void testGeneralInitialization() {
		Random rand = new Random();
		Point2D pt1 = new Point2D.Double(rand.nextDouble(), rand.nextDouble());
		Point2D pt2 = new Point2D.Double(rand.nextDouble(), rand.nextDouble());
		
		Line2D line = new Line2D(pt1, pt2);
		Assert.assertEquals(Line2DType.LINE, line.getType());
		
		double m = (pt2.getY() - pt1.getY()) / (pt2.getX() - pt1.getX());
		Assert.assertEquals(m, line.getM(), 0.00000001d);
		
		double c = pt1.getY() - m * pt1.getX();
		Assert.assertEquals(c, line.getC(), 0.00000001d);
	}
	
	@Test
	public void testXPlaneInitialization() throws Line2DException {
		Random rand = new Random();
		Point2D pt1 = new Point2D.Double(8d, rand.nextDouble());
		Point2D pt2 = new Point2D.Double(8d, rand.nextDouble());
		
		Line2D line = new Line2D(pt1, pt2);
		Assert.assertEquals(Line2DType.X_PLANE, line.getType());
		Assert.assertEquals(0d, line.getM(), 0.00000001d);
		Assert.assertEquals(8d, line.getC(), 0.00000001d);
		
		// Fail: Unable to get Y
		this.testExceptionThrown(new ExceptionFacilitator<Line2DException>() {
			@Override
			public void testForException() throws Line2DException {
				line.getY(8d);
				
			}
		}, Line2DException.class);
		
		// Get X always return the same value
		int sampleSize = 100;
		for (int i = 0; i < sampleSize; i++) {
			double x = line.getX(rand.nextDouble());
			Assert.assertEquals(8d, x, 0.00000001d);
		}
	}
	
	@Test
	public void testYPlaneInitialization() throws Line2DException {
		Random rand = new Random();
		Point2D pt1 = new Point2D.Double(rand.nextDouble(), 4d);
		Point2D pt2 = new Point2D.Double(rand.nextDouble(), 4d);
		
		Line2D line = new Line2D(pt1, pt2);
		Assert.assertEquals(Line2DType.Y_PLANE, line.getType());
		Assert.assertEquals(0d, line.getM(), 0.00000001d);
		Assert.assertEquals(4d, line.getC(), 0.00000001d);
		
		// Fail: Unable to get X
		this.testExceptionThrown(new ExceptionFacilitator<Line2DException>() {
			@Override
			public void testForException() throws Line2DException {
				line.getX(4d);
				
			}
		}, Line2DException.class);
		
		// Get Y always return the same value
		int sampleSize = 100;
		for (int i = 0; i < sampleSize; i++) {
			double x = line.getY(rand.nextDouble());
			Assert.assertEquals(4d, x, 0.00000001d);
		}
	}
	
	@Test
	public void testLinetoLineIntersection() throws Line2DException {
		Random rand = new Random();
		int sampleSize = 100;
		for (int i = 0; i < sampleSize; i++) {
			Point2D pt1 = new Point2D.Double(rand.nextDouble(), rand.nextDouble());
			Point2D pt2 = new Point2D.Double(rand.nextDouble(), rand.nextDouble());
			Line2D line1 = new Line2D(pt1, pt2);
			
			
			Point2D pt3 = new Point2D.Double(rand.nextDouble(), rand.nextDouble());
			Point2D pt4 = new Point2D.Double(rand.nextDouble(), rand.nextDouble());
			Line2D line2 = new Line2D(pt3, pt4);
			
			Point2D intersection1 = line1.getIntersection(line2);
			Assert.assertEquals(intersection1.getX(), line1.getX(intersection1.getY()), 0.00000001d);
			Assert.assertEquals(intersection1.getY(), line1.getY(intersection1.getX()), 0.00000001d);
			Assert.assertEquals(intersection1.getX(), line2.getX(intersection1.getY()), 0.00000001d);
			Assert.assertEquals(intersection1.getY(), line2.getY(intersection1.getX()), 0.00000001d);
			
			Point2D intersection2 = line2.getIntersection(line1);
			Assert.assertEquals(intersection2.getX(), line1.getX(intersection2.getY()), 0.00000001d);
			Assert.assertEquals(intersection2.getY(), line1.getY(intersection2.getX()), 0.00000001d);
			Assert.assertEquals(intersection2.getX(), line2.getX(intersection2.getY()), 0.00000001d);
			Assert.assertEquals(intersection2.getY(), line2.getY(intersection2.getX()), 0.00000001d);
			
			
			Assert.assertEquals(intersection1.getX(), intersection2.getX(), 0.00000001d);
			Assert.assertEquals(intersection1.getY(), intersection2.getY(), 0.00000001d);
		}
	}
	
	@Test
	public void testLinetoXPlaneIntersection() throws Line2DException {
		Random rand = new Random();
		
		int sampleSize = 100;
		for (int i = 0; i < sampleSize; i++) {
			Point2D pt1 = new Point2D.Double(rand.nextDouble(), rand.nextDouble());
			Point2D pt2 = new Point2D.Double(rand.nextDouble(), rand.nextDouble());
			Line2D line = new Line2D(pt1, pt2);
			
			double x = rand.nextDouble();
			Point2D pt3 = new Point2D.Double(x, rand.nextDouble());
			Point2D pt4 = new Point2D.Double(x, rand.nextDouble());
			Line2D xPlane = new Line2D(pt3, pt4);
			
			Point2D intersection1 = line.getIntersection(xPlane);
			Assert.assertEquals(intersection1.getX(), line.getX(intersection1.getY()), 0.00000001d);
			Assert.assertEquals(intersection1.getY(), line.getY(intersection1.getX()), 0.00000001d);
			Assert.assertEquals(x, intersection1.getX(), 0.00000001d);
			
			Point2D intersection2 = xPlane.getIntersection(line);
			Assert.assertEquals(intersection2.getX(), line.getX(intersection2.getY()), 0.00000001d);
			Assert.assertEquals(intersection2.getY(), line.getY(intersection2.getX()), 0.00000001d);
			Assert.assertEquals(x, intersection2.getX(), 0.00000001d);
		}
	}
	
	@Test
	public void testLinetoYPlaneIntersection() throws Line2DException {
		Random rand = new Random();
		
		int sampleSize = 100;
		for (int i = 0; i < sampleSize; i++) {
			Point2D pt1 = new Point2D.Double(rand.nextDouble(), rand.nextDouble());
			Point2D pt2 = new Point2D.Double(rand.nextDouble(), rand.nextDouble());
			Line2D line = new Line2D(pt1, pt2);
			
			double y = rand.nextDouble();
			Point2D pt3 = new Point2D.Double(rand.nextDouble(), y);
			Point2D pt4 = new Point2D.Double(rand.nextDouble(), y);
			Line2D yPlane = new Line2D(pt3, pt4);
			
			Point2D intersection1 = line.getIntersection(yPlane);
			Assert.assertEquals(intersection1.getX(), line.getX(intersection1.getY()), 0.00000001d);
			Assert.assertEquals(intersection1.getY(), line.getY(intersection1.getX()), 0.00000001d);
			Assert.assertEquals(y, intersection1.getY(), 0.00000001d);
			
			Point2D intersection2 = yPlane.getIntersection(line);
			Assert.assertEquals(intersection2.getX(), line.getX(intersection2.getY()), 0.00000001d);
			Assert.assertEquals(intersection2.getY(), line.getY(intersection2.getX()), 0.00000001d);
			Assert.assertEquals(y, intersection2.getY(), 0.00000001d);
		}	
	}
	
	@Test
	public void testInvalidPlaneIntersections() {
		Random rand = new Random();
		
		//x plane to x plane intersection
		double x1 = rand.nextDouble();
		Point2D pt1 = new Point2D.Double(x1, rand.nextDouble());
		Point2D pt2 = new Point2D.Double(x1, rand.nextDouble());
		Line2D xPlane1 = new Line2D(pt1, pt2);
		
		double x2 = rand.nextDouble();
		Point2D pt3 = new Point2D.Double(x2, rand.nextDouble());
		Point2D pt4 = new Point2D.Double(x2, rand.nextDouble());
		Line2D xPlane2 = new Line2D(pt3, pt4);
		
		this.testExceptionThrown(new ExceptionFacilitator<Line2DException>() {
			@Override
			public void testForException() throws Line2DException {
				xPlane1.getIntersection(xPlane2);
			}
		}, Line2DException.class);
		
		//y plane to y plane intersection
		double y1 = rand.nextDouble();
		Point2D pt5 = new Point2D.Double(rand.nextDouble(), y1);
		Point2D pt6 = new Point2D.Double(rand.nextDouble(), y1);
		Line2D yPlane1 = new Line2D(pt5, pt6);
		
		double y2 = rand.nextDouble();
		Point2D pt7 = new Point2D.Double(rand.nextDouble(), y2);
		Point2D pt8 = new Point2D.Double(rand.nextDouble(), y2);
		Line2D yPlane2 = new Line2D(pt7, pt8);
		
		this.testExceptionThrown(new ExceptionFacilitator<Line2DException>() {
			@Override
			public void testForException() throws Line2DException {
				yPlane1.getIntersection(yPlane2);
			}
		}, Line2DException.class);		
	}
	
	@Test
	public void testInvalidLineIntersection() {
		Random rand = new Random();
		
		Point2D pt1 = new Point2D.Double(rand.nextDouble(), rand.nextDouble());
		Point2D pt2 = new Point2D.Double(rand.nextDouble(), rand.nextDouble());
		Line2D line1 = new Line2D(pt1, pt2);
		
		double deltaX = rand.nextDouble();
				
		Point2D pt3 = new Point2D.Double(pt1.getX() + deltaX, pt1.getY());
		Point2D pt4 = new Point2D.Double(pt2.getX() + deltaX, pt2.getY());
		Line2D line2 = new Line2D(pt3, pt4);
		
		this.testExceptionThrown(new ExceptionFacilitator<Line2DException>() {
			@Override
			public void testForException() throws Line2DException {
				line1.getIntersection(line2);
			}
		}, Line2DException.class);	
		
		
	}
}
