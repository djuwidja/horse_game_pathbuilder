package com.djuwidja.horsegame.pathfinder.meta;

import lombok.Getter;

public class RaceHorse {
	@Getter private double fwdSpdMax; // max forward speed
	@Getter private double angSpdMax; // max angular speed
	@Getter private double fwdAcc; // forward acceleration
	@Getter private double angAcc; // angular acceleration
	
	public RaceHorse(
			double fwdSpdMax,
			double angSpdMax,
			double fwdAcc,
			double angAcc) {
		this.fwdSpdMax = fwdSpdMax;
		this.angSpdMax = angSpdMax;
		this.fwdAcc = fwdAcc;
		this.angAcc = angAcc;
	}
}
