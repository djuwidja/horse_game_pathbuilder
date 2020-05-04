package com.djuwidja.horsegame.pathfinder.meta;

import java.awt.geom.Point2D;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.djuwidja.horsegame.pathfinder.math.Vector2D;

public class StartPointTest {
	@Test
	public void testInitialization() {
		Point2D startPos = new Point2D.Double(0d, 0d);
		Vector2D startVec = new Vector2D(1d, 0d);
		StartPoint pt = new StartPoint(startPos, startVec);
		
		Assert.assertEquals(startPos.getX(), pt.getStartPos().getX(), 0.00000001d);
		Assert.assertEquals(startPos.getY(), pt.getStartPos().getY(), 0.00000001d);
		Assert.assertEquals(startVec.getX(), pt.getStartVec().getX(), 0.00000001d);
		Assert.assertEquals(startVec.getY(), pt.getStartVec().getY(), 0.00000001d);
	}
}
