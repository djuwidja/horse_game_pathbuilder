package com.djuwidja.horsegame.pathfinder.math;

import java.awt.geom.Point2D;

import lombok.Getter;

public class Vector2D {
	@Getter private double x;
	@Getter private double y;
	private double magnitude;
	
	boolean isDirty = true;
	
	private double oneOnMag = 0d;
	
	public Vector2D() {
		this.x = 0f;
		this.y = 0f;
		evaluate();
	}
	
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
		evaluate();
	}
	
	public Vector2D(Point2D startPt, Point2D endPt) {
		this.x = endPt.getX() - startPt.getX();
		this.y = endPt.getY() - startPt.getY();
		evaluate();
	}
	
	private void evaluate() {
		if (isDirty) {
			this.magnitude = Math.sqrt(x*x + y*y);
			try {
				this.oneOnMag = 1d / this.magnitude;
			}
			catch (Exception e) {
				this.oneOnMag = 0d;
			}
			
			isDirty = false;
		}
	}
	
	public double getMagnitude() {
		evaluate();
		return magnitude;
	}
		
	public void normalize() {
		evaluate();
		set(x * oneOnMag, y * oneOnMag);
	}
	
	public void setX(double x) {
		this.x = x;
		isDirty = true;
	}
	
	public void setY(double y) {
		this.y = y;
		isDirty = true;
	}
	
	public void set(double x, double y) {
		this.x = x;
		this.y = y;
		isDirty = true;
	}
	
	public void scalar(double a) {
		this.x *= a;
		this.y *= a;
		isDirty = true;
	}
	
	public double dot(Vector2D vec) {
		return this.x * vec.x + this.y * vec.y;
	}
	
	public Vector2D normal() {
		double halfPi = Math.PI * 0.5d;
		double vecX = this.x * Math.cos(halfPi) - this.y * Math.sin(halfPi);
		double vecY = this.x * Math.sin(halfPi) + this.y * Math.cos(halfPi);
		return new Vector2D(vecX, vecY);
	}
	
	@Override
	public String toString() {
		return String.format("Vector2D[%f, %f]", this.x, this.y);
	}
}
