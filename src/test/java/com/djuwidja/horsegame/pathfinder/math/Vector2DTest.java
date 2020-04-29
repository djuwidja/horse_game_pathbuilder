package com.djuwidja.horsegame.pathfinder.math;

import org.junit.jupiter.api.Test;
import org.junit.Assert;

import java.awt.geom.Point2D;
import java.util.Random;

public class Vector2DTest {
	@Test
	public void testInitialization() {
		Vector2D vec0 = new Vector2D();
		Assert.assertEquals(0d, vec0.getX(), 0.0000001d);
		Assert.assertEquals(0d, vec0.getY(), 0.0000001d);
		
		Random rand = new Random();
		
		double vecX = rand.nextDouble();
		double vecY = rand.nextDouble();
		
		
		vec0.set(vecX, vecY);
		Vector2D vec1 = new Vector2D(vecX, vecY);
		
		Assert.assertEquals(vecX, vec0.getX(), 0.0000001d);
		Assert.assertEquals(vecY, vec0.getY(), 0.0000001d);
		
		Assert.assertEquals(vecX, vec1.getX(), 0.0000001d);
		Assert.assertEquals(vecY, vec1.getY(), 0.0000001d);
	}
	
	@Test
	public void testInitializationWithPoints() {
		Random rand = new Random();
		Point2D startPt = new Point2D.Double(rand.nextDouble(), rand.nextDouble());
		Point2D endPt = new Point2D.Double(rand.nextDouble(), rand.nextDouble());
		
		double vecX = endPt.getX() - startPt.getX();
		double vecY = endPt.getY() - startPt.getY();
		
		Vector2D vec = new Vector2D(startPt, endPt);
		System.out.println(vec);
		
		Assert.assertEquals(vecX, vec.getX(), 0.0000001d);
		Assert.assertEquals(vecY, vec.getY(), 0.0000001d);	
	}
	
	@Test
	public void testMagnitudeAndNormalize() {
		Random rand = new Random();
		double vecX = rand.nextDouble();
		double vecY = rand.nextDouble();
		
		double magnitude = Math.sqrt(vecX * vecX + vecY * vecY);
		
		Vector2D vec = new Vector2D(vecX, vecY);
		Assert.assertEquals(magnitude, vec.getMagnitude(), 0.00001d);
		
		double unitVecX = vecX / magnitude;
		double unitVecY = vecY / magnitude;
		
		vec.normalize();
		Assert.assertEquals(unitVecX, vec.getX(), 0.0000001d);
		Assert.assertEquals(unitVecY, vec.getY(), 0.0000001d);
		
		Assert.assertEquals(1d, vec.getMagnitude(), 0.0000001d);
	}
	
	@Test
	public void testScalar() {
		Random rand = new Random();
		double vecX = rand.nextDouble();
		double vecY = rand.nextDouble();
		
		double multiply = rand.nextDouble();
		double resultX = vecX * multiply;
		double resultY = vecY * multiply;
		
		Vector2D vec = new Vector2D(vecX, vecY);
		vec.scalar(multiply);
		
		Assert.assertEquals(resultX, vec.getX(), 0.0000001d);
		Assert.assertEquals(resultY, vec.getY(), 0.0000001d);
	}
	
	@Test
	public void testDoct() {
		Random rand = new Random();
		double vec1X = rand.nextDouble();
		double vec1Y = rand.nextDouble();
		Vector2D vec1 = new Vector2D(vec1X, vec1Y);
		
		double vec2X = rand.nextDouble();
		double vec2Y = rand.nextDouble();
		Vector2D vec2 = new Vector2D(vec2X, vec2Y);
		
		double expected = vec1X * vec2X + vec1Y * vec2Y;
		Assert.assertEquals(expected, vec1.dot(vec2), 0.0000001d);
	}
	
	@Test
	public void testForceEvaluate() {
		int iteration = 100;
		
		Random rand = new Random();		
		
		Vector2D vec = new Vector2D();
		for (int i = 0; i < iteration; i++) {
			double vecX = rand.nextDouble();
			double vecY = rand.nextDouble();
			
			double magnitude = Math.sqrt(vecX * vecX + vecY * vecY);
			double unitVecX = vecX / magnitude;
			double unitVecY = vecY / magnitude;			
			
			vec.set(vecX, vecY);
			Assert.assertEquals(magnitude, vec.getMagnitude(), 0.0000001d);
			vec.normalize();
			Assert.assertEquals(unitVecX, vec.getX(), 0.0000001d);
			Assert.assertEquals(unitVecY, vec.getY(), 0.0000001d);
			Assert.assertEquals(1d, vec.getMagnitude(), 0.0000001d);
			
			double multiply = rand.nextDouble();
			vec.scalar(multiply);
			Assert.assertEquals(multiply, vec.getMagnitude(), 0.0000001d);
		}
	}
	
	@Test
	public void testNormal() {
		Vector2D vec = new Vector2D(0d, 1d);
		
		Vector2D normal = vec.normal();
		Assert.assertEquals(1d, normal.getX(), 0.0000001d);
		Assert.assertEquals(0d, normal.getY(), 0.0000001d);
		
		Random rand = new Random();	
		int sampleSize = 100;
		for (int i = 0; i < sampleSize; i++) {
			Vector2D testVec = new Vector2D(rand.nextDouble(), rand.nextDouble());		
			Vector2D testNormal = testVec.normal();
			Assert.assertEquals(0d, testVec.dot(testNormal), 0.0000001d);
		}
	}
}
