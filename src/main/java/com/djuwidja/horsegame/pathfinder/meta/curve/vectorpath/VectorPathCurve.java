package com.djuwidja.horsegame.pathfinder.meta.curve.vectorpath;

import java.awt.geom.Point2D;

import com.djuwidja.horsegame.pathfinder.math.Line2DException;
import com.djuwidja.horsegame.pathfinder.math.Vector2D;
import com.djuwidja.horsegame.pathfinder.meta.curve.TangentVector;
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
	public TangentVector getTangentVector(Point2D pt, Vector2D vec) throws TrackSectionCurveException {
		double closestTime = Double.MAX_VALUE;
		VectorPathSect closestSect = null;

		VectorPathSect[] sectList = path.getVectorList();
		for (int i = 0; i < sectList.length; i++) {
			VectorPathSect sect = sectList[i];
			try {
				double timeOfImpact = sect.getLine().getTimeOfImpact(pt, vec);
				Point2D intersectPoint = new Point2D.Double(pt.getX() + vec.getX() * timeOfImpact, pt.getY() + vec.getY() * timeOfImpact);
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
			return new TangentVector(closestTime, closestSect.getVector());
		}
	}
}
