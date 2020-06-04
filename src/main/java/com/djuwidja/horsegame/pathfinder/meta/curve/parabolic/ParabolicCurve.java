package com.djuwidja.horsegame.pathfinder.meta.curve.parabolic;

import lombok.Getter;

import java.awt.geom.Point2D;

import com.djuwidja.horsegame.pathfinder.math.Parabola2D;
import com.djuwidja.horsegame.pathfinder.math.Vector2D;
import com.djuwidja.horsegame.pathfinder.meta.Direction;
import com.djuwidja.horsegame.pathfinder.meta.curve.TrackSectionCurve;

public class ParabolicCurve implements TrackSectionCurve {
	@Getter private int sectionType;
	@Getter private Direction direction;
	@Getter private Point2D controlPt;
	private Parabola2D model;	
	
	public ParabolicCurve(Point2D startPt, Point2D endPt, Point2D vertex, Point2D controlPt) {
		model = new Parabola2D(startPt, endPt, vertex);
		if (startPt.getY() - endPt.getY() > 0d) {
			direction = Direction.SOUTH;
		} else {
			direction = Direction.NORTH;
		}
		
		this.controlPt = controlPt;
	}
	
	@Override
	public Vector2D getTangentVector(Point2D pt, Vector2D normal) {		
		double t = model.getTimeOfImpact(pt, normal);	
		Point2D intersectPt = new Point2D.Double(pt.getX() + t * normal.getX(), pt.getY() + t * normal.getY());
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
			vec.scalar(-1d);
		}
		else if (direction == Direction.SOUTH && vec.getY() < 0d) {
			vec.scalar(-1d);
		}
		vec.normalize();		
		return vec;
	}
	
	@Override
	public double getTimeOfImpact(Point2D pt, Vector2D vec) {
		return model.getTimeOfImpact(pt, vec);
	}
	
	private double getXFromLine(double y, double m, double c) {
		return m*y + c;
	}
	
}
