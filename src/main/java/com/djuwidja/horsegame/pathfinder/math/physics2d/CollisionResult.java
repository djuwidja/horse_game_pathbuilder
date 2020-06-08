package com.djuwidja.horsegame.pathfinder.math.physics2d;

import java.awt.geom.Point2D;

import lombok.Getter;

public class CollisionResult {
	public static CollisionResult createSuccssResult(double timeOfImpact, Point2D collisionPt, Point2D destWithoutCollision) {
		return new CollisionResult(true, timeOfImpact, collisionPt, destWithoutCollision);
	}
	
	public static CollisionResult createFailResult() {
		return new CollisionResult(false, 0d, null, null);
	}
	
	
	@Getter private boolean hasCollide;
	@Getter private double timeOfImpact;
	@Getter private Point2D collisionPt;
	@Getter private Point2D destWithoutCollision;
		
	private CollisionResult(boolean hasCollide, double timeofImpact, Point2D collisionPt, Point2D destWithoutCollision) {
		this.hasCollide = hasCollide;
		this.timeOfImpact = timeofImpact;
		this.collisionPt = collisionPt;
		this.destWithoutCollision = destWithoutCollision;
	}
}
