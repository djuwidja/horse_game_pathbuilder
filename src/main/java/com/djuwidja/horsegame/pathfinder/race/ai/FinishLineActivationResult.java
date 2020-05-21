package com.djuwidja.horsegame.pathfinder.race.ai;

import lombok.Getter;

public class FinishLineActivationResult {
	@Getter private boolean isSuccess;
	@Getter private double timeOfImpact;
	
	public FinishLineActivationResult(boolean isSuccess, double timeOfImpact) {
		this.isSuccess = isSuccess;
		this.timeOfImpact = timeOfImpact;
	}
	
}
