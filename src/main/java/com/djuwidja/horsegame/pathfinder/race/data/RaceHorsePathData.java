package com.djuwidja.horsegame.pathfinder.race.data;

import java.awt.geom.Point2D;

import com.djuwidja.horsegame.pathfinder.math.Vector2D;

import lombok.Getter;

public class RaceHorsePathData {
	@Getter private Point2D position;
	@Getter private Vector2D direction;
	@Getter private double speed;
	
	public RaceHorsePathData(Point2D position, Vector2D direction, double speed) {
		this.position = position;
		this.direction = direction;
		this.speed = speed;
	}
}
