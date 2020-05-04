package com.djuwidja.horsegame.pathfinder.ai;

import com.djuwidja.horsegame.pathfinder.meta.RaceHorse;
import com.djuwidja.horsegame.pathfinder.meta.RaceTrack;

import lombok.Getter;

public class RaceAI implements AI{
	@Getter private RaceTrack raceTrack;
	private RaceHorseAI[] raceHorseAIList;
	
	@Getter private boolean isRaceFinished;
	
	public RaceAI(RaceTrack raceTrack, RaceHorse[] raceHorseList) {
		this.raceTrack = raceTrack;
		this.raceHorseAIList = new RaceHorseAI[raceHorseList.length];
		for (int i = 0; i < raceHorseList.length; i++) {
			RaceHorseAI ai = new RaceHorseAI(raceHorseList[i]);
			this.raceHorseAIList[i] = ai;
		}
		this.isRaceFinished = false;
	}
	
	@Override
	public void update(double timeDelta) {
		for (int i = 0; i < raceHorseAIList.length; i++) {
			raceHorseAIList[i].update(timeDelta);
		}
	}
}
