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
			
			VectorPathSect section = new VectorPathSect(startPt, endPt);
			Assert.assertEquals(startPt, section.getStartPt());
			Assert.assertEquals(endPt, section.getEndPt());
			Assert.assertEquals(vec.getX(), section.getVector().getX(), 0.00000001d);
			Assert.assertEquals(vec.getY(), section.getVector().getY(), 0.00000001d);
		}
	}
}
