package com.djuwidja.horsegame.pathfinder.model.meta;

import java.awt.geom.Point2D;

import com.djuwidja.horsegame.pathfinder.math.Vector2D;

import lombok.Getter;

public class VectorPathSection {
	@Getter private Point2D startPt;
	@Getter private Point2D endPt;
	@Getter private Vector2D vector;
	
	public VectorPathSection(Point2D startPt, Point2D endPt) {
		this.startPt = startPt;
		this.endPt = endPt;
		this.vector = new Vector2D(startPt, endPt);
	}
}
