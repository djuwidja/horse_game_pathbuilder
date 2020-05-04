package com.djuwidja.horsegame.pathfinder.meta.curve.vectorpath;

import java.awt.geom.Point2D;

import com.djuwidja.horsegame.pathfinder.math.Line2DException;
import com.djuwidja.horsegame.pathfinder.math.Vector2D;
import com.djuwidja.horsegame.pathfinder.meta.curve.TrackSectionCurve;
import com.djuwidja.horsegame.pathfinder.meta.curve.TrackSectionCurveException;
import com.djuwidja.horsegame.pathfinder.meta.path.vectorpath.ConstructorParamException;
import com.djuwidja.horsegame.pathfinder.meta.path.vectorpath.VectorPath;
import com.djuwidja.horsegame.pathfinder.meta.path.vectorpath.VectorPathSect;

public class VectorPathCurve implements TrackSectionCurve {
	private VectorPath path;

	public VectorPathCurve(Point2D[] pointList) throws ConstructorParamException {
		this.path = new VectorPath(pointList);
	}

	@Override
	public Vector2D getTangentVector(Point2D pt, Vector2D normal) throws TrackSectionCurveException {
		double closestTime = Double.MAX_VALUE;
		VectorPathSect closestSect = null;

		VectorPathSect[] sectList = path.getVectorList();
		System.out.println(String.format("pt=%s normal=%s", pt, normal));
		for (int i = 0; i < sectList.length; i++) {
			VectorPathSect sect = sectList[i];
			try {
				System.out.println(String.format("sect line %s", sect.getLine()));
				double timeOfImpact = sect.getLine().getTimeOfImpact(pt, normal);
				if (timeOfImpact >= 0d) {
					Point2D intersectPoint = new Point2D.Double(pt.getX() + normal.getX() * timeOfImpact, pt.getY() + normal.getY() * timeOfImpact);
					if (isIntersectionWithinBound(sect, intersectPoint)) {
						if (timeOfImpact < closestTime) {
							closestTime = timeOfImpact;
							closestSect = sect;
						}
					}
				}
			} catch (final Line2DException e) {
				
			}
		}
		
		if (closestSect == null) {
			throw new TrackSectionCurveException("Point does not intersect with this path.");
		} else {
			return closestSect.getVector();
		}
	}
		
	private boolean isIntersectionWithinBound(VectorPathSect sect, Point2D intersectionPt) {
		Point2D startPt = sect.getStartPt();
		Point2D endPt = sect.getEndPt();
		
		boolean xBound = isWithinBound(startPt.getX(), endPt.getX(), intersectionPt.getX());
		boolean yBound = isWithinBound(startPt.getY(), endPt.getY(), intersectionPt.getY());
		
		return xBound && yBound;
	}
	
	private boolean isWithinBound(double bound1, double bound2, double value) {
		return (bound1 <= value && value <= bound2) || (bound2 <= value && value <= bound1);
	}
}
