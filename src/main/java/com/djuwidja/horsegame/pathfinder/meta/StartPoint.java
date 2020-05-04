package com.djuwidja.horsegame.pathfinder.meta;

import java.awt.geom.Point2D;

import com.djuwidja.horsegame.pathfinder.math.Vector2D;

import lombok.Getter;

public class StartPoint {
	@Getter private Point2D startPos;
	@Getter private Vector2D startVec;
	
	public StartPoint(Point2D startPos, Vector2D startVec) {
		this.startPos = startPos;
		this.startVec = startVec;
	}
	
}
