package com.djuwidja.horsegame.pathfinder.meta.path.vectorpath;

import java.awt.geom.Point2D;

import com.djuwidja.horsegame.pathfinder.math.Line2D;
import com.djuwidja.horsegame.pathfinder.math.Vector2D;

import lombok.Getter;

public class VectorPathSect {
	@Getter private Point2D startPt;
	@Getter private Point2D endPt;
	@Getter private Vector2D vector;
	@Getter private Line2D line;
	
	public VectorPathSect(Point2D startPt, Point2D endPt) {
		this.startPt = startPt;
		this.endPt = endPt;
		this.vector = new Vector2D(startPt, endPt);
		this.vector.normalize();
		this.line = new Line2D(startPt, endPt);
	}
}
