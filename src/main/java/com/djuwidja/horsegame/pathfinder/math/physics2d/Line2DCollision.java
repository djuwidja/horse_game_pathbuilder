package com.djuwidja.horsegame.pathfinder.math.physics2d;

import java.awt.geom.Point2D;

import com.djuwidja.horsegame.pathfinder.math.Line2D;
import com.djuwidja.horsegame.pathfinder.math.Line2DException;
import com.djuwidja.horsegame.pathfinder.math.Vector2D;

public class Line2DCollision {
	public static CollisionResult checkCollision(Line2D line, Point2D pt, Vector2D unitVec, double spd, double timeDelta) {
		Vector2D vecWithSpd = unitVec.clone();
		vecWithSpd.scalar(spd);
		
		try {
			double timeOfImpactBefore = line.getTimeOfImpact(pt, vecWithSpd);
			Point2D destPt = new Point2D.Double(pt.getX() + vecWithSpd.getX() * timeDelta, pt.getY() + vecWithSpd.getY() * timeDelta);
			double timeOfImpactAfter = line.getTimeOfImpact(destPt,  unitVec);
			Point2D collisionPt = new Point2D.Double(pt.getX() + vecWithSpd.getX() * timeOfImpactBefore,
					 pt.getY() + vecWithSpd.getY() * timeOfImpactBefore);
			
			if (timeOfImpactBefore >= 0f && timeOfImpactBefore <= timeDelta) {
				return CollisionResult.createSuccssResult(timeOfImpactBefore, collisionPt, destPt);
			} else if (timeOfImpactBefore >= 0f && timeOfImpactAfter < 0f) {
				return CollisionResult.createSuccssResult(timeOfImpactBefore, collisionPt, destPt);
			} 
			
			return CollisionResult.createFailResult();			
			
		} catch (Line2DException e) {
			return CollisionResult.createFailResult();
		}
		
		
	}

}
