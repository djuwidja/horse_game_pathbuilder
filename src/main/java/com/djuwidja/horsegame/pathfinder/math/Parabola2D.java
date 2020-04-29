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
	
	public Parabola2D(Point2D pt1, Point2D pt2, Point2D vertex) {
		this.pt1 = pt1;
		this.pt2 = pt2;
		this.vertex = vertex;
		
		this.evaluate();
	}
	/**
	 * Evaluate the value of a, b and c.
	 */
	private void evaluate() {
		double vxmtx = vertex.getX() - pt2.getX();
		double tymoy = pt2.getY() - pt1.getY();
		double vymty = vertex.getY() - pt2.getY();
		double vy2mty2 = Arithmetic.square(vertex.getY()) - Arithmetic.square(pt2.getY());
		double ty2moy2 = Arithmetic.square(pt2.getY()) - Arithmetic.square(pt1.getY());
		
		double au = vxmtx * tymoy - pt2.getX() * vymty + pt1.getX() * vymty;
		double ad = vy2mty2 * tymoy - ty2moy2 * vymty;
		
		a = au / ad;
		
		double bu = a * ty2moy2 - pt2.getX() + pt1.getX();
		double bd = pt1.getY() - pt2.getY();
		
		b = bu / bd;
		
		c = pt1.getX() - a * Arithmetic.square(pt1.getY()) - b * pt1.getY();	
	}
	/**
	 * Get the value of x from y with ay^2 + by + c.
	 * @param y
	 * @return
	 */
	public double getX(double y) {
		return a * Arithmetic.square(y) + b * y + c;
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
	
	public double getTimeOfImpact(Point2D pt, Vector2D vec) {
		if (vec.getY() == 0d) {
			return (this.a * Arithmetic.square(pt.getY()) + b * pt.getY() + c - pt.getX()) / vec.getX();
		}
		
		double omega = (2 * this.a * vec.getY() * pt.getY() + this.b * vec.getY() - vec.getX()) / (2 * a * Arithmetic.square(vec.getY()));
		double theta = (pt.getX() - this.a * Arithmetic.square(pt.getY()) - this.b * pt.getY() - this.c) / (this.a * Arithmetic.square(vec.getY()));
		double root = Math.sqrt(theta + Arithmetic.square(omega));
		
		double tPos = -omega + root;
		double tNeg = -omega - root;		
		return Math.min(tPos, tNeg);
	}
	
	@Override
	public String toString() {
		return String.format("x = %fy^2 + %fy + %f", this.a, this.b, this.c);
	}
}
