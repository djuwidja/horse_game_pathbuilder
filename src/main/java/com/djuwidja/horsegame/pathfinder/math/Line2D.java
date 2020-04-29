package com.djuwidja.horsegame.pathfinder.math;

import java.awt.geom.Point2D;

import lombok.Getter;

public class Line2D {
	@Getter private double m;
	@Getter private double c;
	@Getter private Line2DType type;
		
	public Line2D(Point2D pt1, Point2D pt2) {
		double xDiff = (pt2.getX() - pt1.getX());
		double yDiff = (pt2.getY() - pt1.getY());
		
		if (xDiff == 0d) {
			this.type = Line2DType.X_PLANE;
			this.m = 0;
			this.c = pt1.getX();
		} else if (yDiff == 0d) {
			this.type = Line2DType.Y_PLANE;
			this.m = 0;
			this.c = pt1.getY();
		} else {
			this.type = Line2DType.LINE;
			this.m = (pt2.getY() - pt1.getY()) / (pt2.getX() - pt1.getX());
			this.c = pt1.getY() - m * pt1.getX();
		}
	}
	
	public double getY(double x) throws Line2DException{
		switch (this.type) {
		case X_PLANE:
			throw Line2DException.createYCanontBeDeterminedException();
		case Y_PLANE:
			return this.c;
		default:
			return this.m * x + this.c;
		}
	}
	
	public double getX(double y) throws Line2DException {
		switch (this.type) {
		case X_PLANE:
			return this.c;
		case Y_PLANE:
			throw Line2DException.createXCanontBeDeterminedException();
		default:
			return (y - this.c) / this.m;	
		}
	}
	
	public Point2D getIntersection(Line2D line) throws Line2DException {
		return Line2D.getIntersection(this, line);
	}
	
	public Double getTimeOfImpact(Point2D pt, Vector2D vec) throws Line2DException {
		return Line2D.getTimeOfImpact(pt, vec, this);
	}
	
	@Override
	public String toString() {
		switch (this.type) {
		case X_PLANE:
			return String.format("Line[x=%f]", this.c);
		case Y_PLANE:
			return String.format("Line[y=%f]", this.c);
		default:
			return String.format("Line[y=%fm + %f]", this.m, this.c);
		}
	}
	
	public static final Point2D getIntersection(Line2D lineA, Line2D lineB) throws Line2DException {
		Line2DType lineAType = lineA.getType();
		Line2DType lineBType = lineB.getType();
		
		if (lineAType == lineBType) {
			switch (lineAType) {
			case X_PLANE:
				throw Line2DException.createLinesAreParallelException();
			case Y_PLANE:
				throw Line2DException.createLinesAreParallelException();
			default:
				return getLineIntersection(lineA, lineB);
			}
		}
		else {
			// Plane to plane intersections
			if (lineAType == Line2DType.X_PLANE && lineBType == Line2DType.Y_PLANE) {
				return getInsectionWithXYPlane(lineA, lineB);
			} 
			else if (lineAType == Line2DType.Y_PLANE && lineBType == Line2DType.X_PLANE) {
				return getInsectionWithXYPlane(lineB, lineA);
			}
			// line to X plane intersections
			else if (lineAType == Line2DType.LINE && lineBType == Line2DType.X_PLANE) {
				return getIntersectionWithXPlane(lineA, lineB);
			}
			else if (lineAType == Line2DType.X_PLANE && lineBType == Line2DType.LINE) {
				return getIntersectionWithXPlane(lineB, lineA);
			}
			// line to Y plane intersections
			else if (lineAType == Line2DType.LINE && lineBType == Line2DType.Y_PLANE) {
				return getIntersectionWithYPlane(lineA, lineB);
			}
			else {
				return getIntersectionWithYPlane(lineB, lineA);
			}
		}
	}
	
	private static final Point2D getLineIntersection(Line2D lineA, Line2D lineB) throws Line2DException {
		double mDiff = lineB.m - lineA.m;
		if (Math.abs(mDiff) < 0.000000000000001d) {
			throw Line2DException.createLinesAreParallelException();
		}
		
		double x = (lineA.c - lineB.c) / mDiff;
		double y = lineA.m * x + lineA.c;
		
		return new Point2D.Double(x, y);
	}
		
	private static final Point2D getIntersectionWithXPlane(Line2D line, Line2D xPlane) throws Line2DException {
		if (xPlane.getType() != Line2DType.X_PLANE) {
			throw Line2DException.createLineIsNotXPlaneException();
		}
		
		double x = xPlane.getC();
		double y = line.getY(x);
		return new Point2D.Double(x, y);
	}
	
	private static final Point2D getIntersectionWithYPlane(Line2D line, Line2D yPlane) throws Line2DException {
		if (yPlane.getType() != Line2DType.Y_PLANE) {
			throw Line2DException.createLineIsNotYPlaneException();
		}
		
		double y = yPlane.getC();
		double x = line.getX(y);
		return new Point2D.Double(x, y);
	}
	
	private static final Point2D getInsectionWithXYPlane(Line2D xPlane, Line2D yPlane) throws Line2DException {
		if (xPlane.getType() != Line2DType.X_PLANE) {
			throw Line2DException.createLineIsNotXPlaneException();
		}
		
		if (yPlane.getType() != Line2DType.Y_PLANE) {
			throw Line2DException.createLineIsNotYPlaneException();
		}
		
		return new Point2D.Double(xPlane.getC(), yPlane.getC());
	}
	
	public static final double getTimeOfImpact(Point2D pt, Vector2D vec, Line2D line) throws Line2DException {
		switch (line.getType()) {
		case X_PLANE:
			return getTimeOfImpactWithXPlane(pt, vec, line);
		case Y_PLANE:
			return getTimeOfImpactWithYPlane(pt, vec, line);
		default:
			return getTimeOfImpactWithLine(pt, vec, line);
		}
	}
	
	private static final double getTimeOfImpactWithLine(Point2D pt, Vector2D vec, Line2D line) throws Line2DException {
		double t;
		try {
			t = (line.getM() * pt.getX() - pt.getY() + line.getC()) / (vec.getY() - line.m * vec.getX());
		}
		catch (final Exception e) {
			throw Line2DException.createCannotResolveTimeOfImpactException();
		}
		
		if (t < 0) {
			throw Line2DException.createCannotResolveTimeOfImpactException();
		}
		
		return t;
	}
	
	private static final double getTimeOfImpactWithXPlane(Point2D pt, Vector2D vec, Line2D line) throws Line2DException {
		if (vec.getX() == 0d) {
			throw Line2DException.createCannotResolveTimeOfImpactException();
		}
		
		return line.getC() / vec.getX();
	}
	
	private static final double getTimeOfImpactWithYPlane(Point2D pt, Vector2D vec, Line2D line) throws Line2DException {
		if (vec.getY() == 0d) {
			throw Line2DException.createCannotResolveTimeOfImpactException();
		}
		
		return line.getC() / vec.getY();
	}
	
}
