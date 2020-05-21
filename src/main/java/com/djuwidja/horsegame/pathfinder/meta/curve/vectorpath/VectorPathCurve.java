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

	public VectorPathCurve(Point2D[] pointList, Point2D controlPt) throws ConstructorParamException {
		this.path = new VectorPath(pointList, controlPt);
	}

	@Override
	public Vector2D getTangentVector(Point2D pt) throws TrackSectionCurveException {
		Vector2D controlVec = new Vector2D(path.getControlPt().getX() - pt.getX(), path.getControlPt().getY() - pt.getY());
		controlVec.normalize();
		
		double closestTime = Double.MAX_VALUE;
		VectorPathSect closestSect = null;

		VectorPathSect[] sectList = path.getVectorList();
		for (int i = 0; i < sectList.length; i++) {
			VectorPathSect sect = sectList[i];
			try {
				double timeOfImpact = sect.getLine().getTimeOfImpact(pt, controlVec);
				Point2D intersectPoint = new Point2D.Double(pt.getX() + controlVec.getX() * timeOfImpact, pt.getY() + controlVec.getY() * timeOfImpact);
				if (sect.isPointWithinBound(intersectPoint)) {
					double absTimeOfImpact = Math.abs(timeOfImpact);
					if (absTimeOfImpact < closestTime) {
						closestTime = absTimeOfImpact;
						closestSect = sect;
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
}
