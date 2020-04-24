package com.djuwidja.horsegame.pathfinder.math;

import lombok.Getter;
import java.awt.geom.Point2D;
/**
 * Parabola class to model a path through the curved tracks within a race track. 
 * Note that the equation of this parabola is inverted (x = ay^2 + by + c) to be more fitting with its application.
 * @author Kenneth Djuwidja
 * @since 1.0.0
 */
public class Parabola2D {
	// f(x) = ax^2 + bx + c
	@Getter private double a;
	@Getter private double b;
	@Getter private double c;
	
	@Getter private Point2D pt1;
	@Getter private Point2D pt2;
	@Getter private Point2D vertex;
	@Getter private Point2D control;
	
	public Parabola2D(Point2D pt1, Point2D pt2, Point2D vertex, Point2D control) {
		this.pt1 = pt1;
		this.pt2 = pt2;
		this.vertex = vertex;
		this.control = control;
		
		this.evaluate();
	}
	/**
	 * 
	 * @param x
	 * @return
	 */
	private static final double square(double x) {
		return x*x;
	}
	/**
	 * Evaluate the value of a, b and c.
	 */
	private void evaluate() {
		double vxmtx = vertex.getX() - pt2.getX();
		double tymoy = pt2.getY() - pt1.getY();
		double vymty = vertex.getY() - pt2.getY();
		double vy2mty2 = square(vertex.getY()) - square(pt2.getY());
		double ty2moy2 = square(pt2.getY()) - square(pt1.getY());
		
		double au = vxmtx * tymoy - pt2.getX() * vymty + pt1.getX() * vymty;
		double ad = vy2mty2 * tymoy - ty2moy2 * vymty;
		
		a = au / ad;
		
		double bu = a * ty2moy2 - pt2.getX() + pt1.getX();
		double bd = pt1.getY() - pt2.getY();
		
		b = bu / bd;
		
		c = pt1.getX() - a * square(pt1.getY()) - b * pt1.getY();	
	}
	/**
	 * Get the value of x from y with ay^2 + by + c.
	 * @param y
	 * @return
	 */
	public double getX(double y) {
		return a * square(y) + b * y + c;
	}
	/**
	 * Get the slope of tangent in y.
	 * @param y
	 * @return
	 */
	public double getTangentSlope(double y) {
		// x' = 2ay + b
		return 2 * a * y + b;
	}
	/**
	 * Get nearest intersection point between this parabola and a line formed between the given point and the control point.
	 * @param pt point to form a line with the control point.
	 * @return The nearest intersection point on this parabola.
	 */
	public Point2D getInteractionCtrlPt(Point2D pt) {
		double md = pt.getY() - control.getY();
		if (md == 0d) {
			return vertex;
		}
		double m = (pt.getX() - control.getX()) / md;
		double cl = control.getX() - m * control.getY();
		
		double bmMOn2a = (b - m) / (2 * a);
		
		double withinSqrt = Math.sqrt((cl - c) / a + square(bmMOn2a));
		
		double yPos = -bmMOn2a + withinSqrt;
		double xPos = getX(yPos);
		double distFromPtPos = square(pt.getX() - xPos) + square(pt.getY() - yPos);
				
		double yNeg = -bmMOn2a - withinSqrt;
		double xNeg = getX(yNeg);
		double distFromPtNeg = square(pt.getX() - xNeg) + square(pt.getY() - yNeg);
		
		if (distFromPtNeg < distFromPtPos) {
			return new Point2D.Double(xNeg, yNeg);
		} else {
			return new Point2D.Double(xPos, yPos);
		}
	}
	
}
