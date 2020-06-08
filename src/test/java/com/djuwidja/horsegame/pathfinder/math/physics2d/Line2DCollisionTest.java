package com.djuwidja.horsegame.pathfinder.math.physics2d;

import org.junit.jupiter.api.Test;

import com.djuwidja.horsegame.pathfinder.math.Line2D;
import com.djuwidja.horsegame.pathfinder.math.Vector2D;

import java.awt.geom.Point2D;

import org.junit.Assert;

public class Line2DCollisionTest {
	@Test
	public void testCollisionFacingLine() {
		Line2D line = new Line2D(new Point2D.Double(0d, 10d), new Point2D.Double(0d, -10d));
		Point2D pt = new Point2D.Double(5d, 0d);
		Vector2D vec = new Vector2D(-1d, 0d);
		
		// no collision
		CollisionResult result1 = Line2DCollision.checkCollision(line, pt, vec, 1d, 1d);
		Assert.assertFalse(result1.isHasCollide());
		
		// collide within time delta
		CollisionResult result2 = Line2DCollision.checkCollision(line, pt, vec, 5d, 1d);
		Assert.assertTrue(result2.isHasCollide());
		Assert.assertEquals(result2.getTimeOfImpact(), 1d, 0.00000000001d);
		Assert.assertEquals(result2.getCollisionPt().getX(), 0d, 0.00000000001d);
		Assert.assertEquals(result2.getCollisionPt().getY(), 0d, 0.00000000001d);
		Assert.assertEquals(result2.getDestWithoutCollision().getX(), 0d, 0.00000000001d);
		Assert.assertEquals(result2.getDestWithoutCollision().getY(), 0d, 0.00000000001d);
		
		CollisionResult result3 = Line2DCollision.checkCollision(line, pt, vec, 1d, 5d);
		Assert.assertTrue(result3.isHasCollide());
		Assert.assertEquals(result3.getTimeOfImpact(), 5d, 0.00000000001d);
		Assert.assertEquals(result3.getCollisionPt().getX(), 0d, 0.00000000001d);
		Assert.assertEquals(result3.getCollisionPt().getY(), 0d, 0.00000000001d);
		Assert.assertEquals(result3.getDestWithoutCollision().getX(), 0d, 0.00000000001d);
		Assert.assertEquals(result3.getDestWithoutCollision().getY(), 0d, 0.00000000001d);
		
		// pass through line
		CollisionResult result4 = Line2DCollision.checkCollision(line, pt, vec, 10d, 1d);
		Assert.assertTrue(result4.isHasCollide());
		Assert.assertEquals(result4.getTimeOfImpact(), 0.5d, 0.00000000001d);
		Assert.assertEquals(result4.getCollisionPt().getX(), 0d, 0.00000000001d);
		Assert.assertEquals(result4.getCollisionPt().getY(), 0d, 0.00000000001d);
		Assert.assertEquals(result4.getDestWithoutCollision().getX(), -5d, 0.00000000001d);
		Assert.assertEquals(result4.getDestWithoutCollision().getY(), 0d, 0.00000000001d);
	}
	
	@Test
	public void testCollisionNotFacingLine() {
		Line2D line = new Line2D(new Point2D.Double(0d, 10d), new Point2D.Double(0d, -10d));
		Point2D pt = new Point2D.Double(5d, 0d);
		Vector2D vec = new Vector2D(1d, 0d);
		
		CollisionResult result1 = Line2DCollision.checkCollision(line, pt, vec, 1d, 1d);
		Assert.assertFalse(result1.isHasCollide());
		
		CollisionResult result2 = Line2DCollision.checkCollision(line, pt, vec, 5d, 1d);
		Assert.assertFalse(result2.isHasCollide());
		
		CollisionResult result3 = Line2DCollision.checkCollision(line, pt, vec, 1d, 5d);
		Assert.assertFalse(result3.isHasCollide());
		
		CollisionResult result4 = Line2DCollision.checkCollision(line, pt, vec, 10d, 1d);
		Assert.assertFalse(result4.isHasCollide());
	}
	
	@Test
	public void testCollisionFacingLineWithBound() {
		Line2D line = new Line2D(new Point2D.Double(0d, 10d), new Point2D.Double(0d, -10d));
		Point2D pt = new Point2D.Double(5d, 11d);
		Vector2D vec = new Vector2D(-1d, 0d);
		
		// out of bound
		CollisionResult resultWithoutBound = Line2DCollision.checkCollision(line, pt, vec, 5d, 1d);
		Assert.assertTrue(resultWithoutBound.isHasCollide());
		
		CollisionResult resultWithBound = Line2DCollision.checkCollision(line, pt, vec, 5d, 1d, true);
		Assert.assertFalse(resultWithBound.isHasCollide());
	}
}
