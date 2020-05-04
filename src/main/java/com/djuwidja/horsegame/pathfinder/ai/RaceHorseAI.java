package com.djuwidja.horsegame.pathfinder.ai;

import com.djuwidja.horsegame.pathfinder.meta.RaceHorse;

import lombok.Getter;

public class RaceHorseAI implements AI {
	@Getter private RaceHorse raceHorse;
	
	public RaceHorseAI(RaceHorse raceHorse) {
		this.raceHorse = raceHorse;
	}

	@Override
	public void update(double timeDelta) {
		
	}
}
