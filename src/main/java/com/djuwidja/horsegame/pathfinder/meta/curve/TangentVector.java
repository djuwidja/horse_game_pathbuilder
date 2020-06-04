package com.djuwidja.horsegame.pathfinder.meta.curve;

import com.djuwidja.horsegame.pathfinder.math.Vector2D;

import lombok.Getter;

public class TangentVector {
	@Getter private double timeOfImpact;
	@Getter private Vector2D vector;
	
	public TangentVector(double timeOfImpact, Vector2D vector) {
		this.timeOfImpact = timeOfImpact;
		this.vector = vector;
	}
}
