package com.djuwidja.horsegame.pathfinder.meta;

import lombok.Getter;

public class RaceHorse {
	@Getter private int horseId;
	@Getter private int laneId;
	@Getter private int expectedRank;
	@Getter private double fwdSpdMax; // max forward speed
	@Getter private double angSpdMax; // max angular speed
	@Getter private double fwdAcc; // forward acceleration
	@Getter private double angAcc; // angular acceleration
	
	public RaceHorse(
			int horseId,
			int laneId,
			int expectedRank,
			double fwdSpdMax,
			double angSpdMax,
			double fwdAcc,
			double angAcc) {
		this.horseId = horseId;
		this.laneId = laneId;
		this.expectedRank = expectedRank;
		this.fwdSpdMax = fwdSpdMax;
		this.angSpdMax = angSpdMax;
		this.fwdAcc = fwdAcc;
		this.angAcc = angAcc;
	}
}
