package com.djuwidja.horsegame.pathfinder.model.meta;

import java.awt.geom.Point2D;

import com.djuwidja.horsegame.pathfinder.math.Vector2D;

public interface TrackSectionCurve {
	public Vector2D getTangentVector(Point2D pt);
}
