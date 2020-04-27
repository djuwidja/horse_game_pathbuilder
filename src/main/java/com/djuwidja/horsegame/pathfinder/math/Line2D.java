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
		Line2DType thisType = this.type;
		Line2DType lineType = line.getType();		
		
		if (thisType == lineType) {
			switch (thisType) {
			case X_PLANE:
				throw Line2DException.createLinesAreParallelException();
			case Y_PLANE:
				throw Line2DException.createLinesAreParallelException();
			default:
				return getLineIntersection(this, line);
			}
		} 
		else {
			// Plane to plane intersections
			if (thisType == Line2DType.X_PLANE && lineType == Line2DType.Y_PLANE) {
				return getInsectionWithXYPlane(this, line);
			} 
			else if (thisType == Line2DType.Y_PLANE && lineType == Line2DType.X_PLANE) {
				return getInsectionWithXYPlane(line, this);
			}
			// line to X plane intersections
			else if (thisType == Line2DType.LINE && lineType == Line2DType.X_PLANE) {
				return getIntersectionWithXPlane(this, line);
			}
			else if (thisType == Line2DType.X_PLANE && lineType == Line2DType.LINE) {
				return getIntersectionWithXPlane(line, this);
			}
			// line to Y plane intersections
			else if (thisType == Line2DType.LINE && lineType == Line2DType.Y_PLANE) {
				return getIntersectionWithYPlane(this, line);
			}
			else {
				return getIntersectionWithYPlane(line, this);
			}
			
		}
	}
	
	private static final Point2D getLineIntersection(Line2D lineA, Line2D lineB) throws Line2DException {
		double mDiff = lineB.m - lineA.m;
		if (mDiff == 0d) {
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
	
}
