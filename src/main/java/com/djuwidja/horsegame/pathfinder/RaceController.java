package com.djuwidja.horsegame.pathfinder;

import com.djuwidja.horsegame.pathfinder.ai.RaceAI;
import com.djuwidja.horsegame.pathfinder.meta.RaceHorse;
import com.djuwidja.horsegame.pathfinder.meta.RaceTrack;

import lombok.Getter;

public class RaceController {
	@Getter private RaceHorse[] raceHorseList;
	@Getter private RaceTrack raceTrack;
	@Getter private double fps; // the fps of the race.
	
	@Getter private double raceTime; // time elapse of the race;
	private double timeInterval; // the time process of the race after each iteraction.
	private RaceAI raceAI;
	
		
	public RaceController(RaceTrack raceTrack, RaceHorse[] raceHorseList, double fps) {
		this.raceTrack = raceTrack;
		this.raceHorseList = raceHorseList;
		this.fps = fps;
		
		this.timeInterval = 1d / fps;
		this.raceTime = 0d;
		this.raceAI = new RaceAI(raceTrack, raceHorseList);
	}
	
	public void generateRace() {
		while (!this.raceAI.isRaceFinished()) {
			raceAI.update(timeInterval);
			raceTime += timeInterval;
		}
	}
}
