package com.djuwidja.horsegame.pathfinder.meta;

import lombok.Getter;

public class RaceHorse {
	@Getter private float fwdSpdMax; // max forward speed
	@Getter private float angSpdMax; // max angular speed
	@Getter private float fwdAcc; // forward acceleration
	@Getter private float angAcc; // angular acceleration
	
	public RaceHorse(float fwdSpdMax,
					 float angSpdMax,
					 float fwdAcc,
					 float angAcc) {
		this.fwdSpdMax = fwdSpdMax;
		this.angSpdMax = angSpdMax;
		this.fwdAcc = fwdAcc;
		this.angAcc = angAcc;
	}
}
