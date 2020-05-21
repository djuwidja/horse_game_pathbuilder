package com.djuwidja.horsegame.pathfinder.meta.path.vectorpath;

import java.awt.geom.Point2D;

import com.djuwidja.horsegame.pathfinder.math.Line2D;
import com.djuwidja.horsegame.pathfinder.math.Line2DType;
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
	
	public boolean isPointWithinBound(Point2D pt) {
		boolean xBound = line.getType() == Line2DType.X_PLANE? Math.abs(pt.getX() - line.getC()) <= 0.0000001d : isWithinBound(startPt.getX(), endPt.getX(), pt.getX());
		boolean yBound = line.getType() == Line2DType.Y_PLANE? Math.abs(pt.getY() - line.getC()) <= 0.0000001d : isWithinBound(startPt.getY(), endPt.getY(), pt.getY());
		
		return xBound && yBound;
	}
	
	private boolean isWithinBound(double bound1, double bound2, double value) {
		return (bound1 <= value && value <= bound2) || (bound2 <= value && value <= bound1);
	}
}
