package com.djuwidja.horsegame.pathfinder.race.data;

import lombok.Getter;

public class RaceHorsePathData {
	@Getter private double x;
	@Getter private double y;
	@Getter private double vecX;
	@Getter private double vecY;
	@Getter private double speed;
	
	public RaceHorsePathData(double x, double y, double vecX, double vecY, double speed) {
		this.x = x;
		this.y = y;
		this.vecX = vecX;
		this.vecY = vecY;
		this.speed = speed;
	}
	
	@Override
	public String toString() {
		return String.format("RaceHorsePathData[pos=(%f,%f), dir=(%f,%f), spd=%f]", x, y, vecX, vecY, speed);
	}
}
