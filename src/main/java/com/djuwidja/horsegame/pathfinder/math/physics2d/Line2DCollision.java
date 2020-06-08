package com.djuwidja.horsegame.pathfinder.math.physics2d;

import java.awt.geom.Point2D;

import com.djuwidja.horsegame.pathfinder.math.Line2D;
import com.djuwidja.horsegame.pathfinder.math.Line2DException;
import com.djuwidja.horsegame.pathfinder.math.Line2DType;
import com.djuwidja.horsegame.pathfinder.math.Vector2D;

public class Line2DCollision {
	public static CollisionResult checkCollision(Line2D line, Point2D pt, Vector2D unitVec, double spd, double timeDelta) {
		return checkCollision(line, pt, unitVec, spd, timeDelta, false);
	}
	
	public static CollisionResult checkCollision(Line2D line, Point2D pt, Vector2D unitVec, double spd, double timeDelta, boolean boundCheck) {
		Vector2D vecWithSpd = unitVec.clone();
		vecWithSpd.scalar(spd);
		
		try {
			CollisionResult result = null;
			
			double timeOfImpactBefore = line.getTimeOfImpact(pt, vecWithSpd);
			Point2D destPt = new Point2D.Double(pt.getX() + vecWithSpd.getX() * timeDelta, pt.getY() + vecWithSpd.getY() * timeDelta);
			double timeOfImpactAfter = line.getTimeOfImpact(destPt,  unitVec);
			Point2D collisionPt = new Point2D.Double(pt.getX() + vecWithSpd.getX() * timeOfImpactBefore,
					 pt.getY() + vecWithSpd.getY() * timeOfImpactBefore);
			
			if (timeOfImpactBefore >= 0f && timeOfImpactBefore <= timeDelta) {
				result =  CollisionResult.createSuccssResult(timeOfImpactBefore, collisionPt, destPt);
			} else if (timeOfImpactBefore >= 0f && timeOfImpactAfter < 0f) {
				result =  CollisionResult.createSuccssResult(timeOfImpactBefore, collisionPt, destPt);
			}
			
			if (result != null) {
				if (boundCheck) {
					return checkBound(line, collisionPt)?result:CollisionResult.createFailResult();
				} else {
					return result;
				}	
			}
			else {
				return CollisionResult.createFailResult();	
			}
		} catch (Line2DException e) {
			return CollisionResult.createFailResult();
		}
	}
	
	public static boolean checkBound(Line2D line, Point2D collisionPt) {
		boolean xBound = line.getType() == Line2DType.X_PLANE? Math.abs(collisionPt.getX() - line.getC()) <= 0.0000001d : isWithinBound(line.getPt1().getX(), line.getPt2().getX(), collisionPt.getX());
		boolean yBound = line.getType() == Line2DType.Y_PLANE? Math.abs(collisionPt.getY() - line.getC()) <= 0.0000001d : isWithinBound(line.getPt1().getY(), line.getPt2().getY(), collisionPt.getY());
		
		return xBound && yBound;
	}
	
	private static boolean isWithinBound(double bound1, double bound2, double value) {
		return (bound1 <= value && value <= bound2) || (bound2 <= value && value <= bound1);
	}

}
