package com.djuwidja.horsegame.pathfinder.meta.curve;

import java.awt.geom.Point2D;

import com.djuwidja.horsegame.pathfinder.math.Vector2D;

public interface TrackSectionCurve {
	public Vector2D getTangentVector(Point2D pt, Vector2D normal) throws TrackSectionCurveException;
	public double getTimeOfImpact(Point2D pt, Vector2D vec) throws TrackSectionCurveException;
}
