package com.djuwidja.horsegame.pathfinder.model.meta;

import lombok.Getter;

import java.awt.geom.Point2D;

import com.djuwidja.horsegame.pathfinder.math.Parabola2D;
import com.djuwidja.horsegame.pathfinder.math.Vector2D;

public class ParabolicCurve implements TrackSectionCurve {
	@Getter private int sectionType;
	@Getter private Direction direction;
	private Parabola2D model;	
	
	public ParabolicCurve(Point2D startPt, Point2D endPt, Point2D vertex, Point2D control) {
		model = new Parabola2D(startPt, endPt, vertex, control);
		if (startPt.getY() - endPt.getY() > 0d) {
			direction = Direction.SOUTH;
		} else {
			direction = Direction.NORTH;
		}
	}
	
	public Vector2D getTangentVector(Point2D pt) {
		Point2D intersectPt = model.getInteractionCtrlPt(pt);
		double slope = model.getTangentSlope(intersectPt.getY());
		// point is at vertex when slope == 0
		if (slope == 0d) {
			return direction==Direction.NORTH?new Vector2D(0d,1d):new Vector2D(0d,-1d);
		}
		
		double c = -slope * pt.getY() + pt.getX();
		
		double y2 = 0;
		double x2 = getXFromLine(y2, slope, c);		
		Vector2D vec = new Vector2D(x2 - pt.getX(), y2 - pt.getY());
		// determine the direction of vector base on the direction variable.
		if (direction == Direction.NORTH && vec.getY() > 0d) {
			vec.dot(-1d);
		}
		else if (direction == Direction.SOUTH && vec.getY() < 0d) {
			vec.dot(-1d);
		}
		vec.normalize();		
		return vec;
	}
	
	private double getXFromLine(double y, double m, double c) {
		return m*y + c;
	}
	
}
