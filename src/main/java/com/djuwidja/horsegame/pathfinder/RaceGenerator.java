package com.djuwidja.horsegame.pathfinder;

import java.util.Map;

import com.djuwidja.horsegame.pathfinder.ai.RaceAI;
import com.djuwidja.horsegame.pathfinder.meta.InvalidLaneIdException;
import com.djuwidja.horsegame.pathfinder.meta.RaceHorse;
import com.djuwidja.horsegame.pathfinder.meta.RaceTrack;

import lombok.Getter;

public class RaceController {
	@Getter private Map<Integer, RaceHorse> raceHorseMap;
	@Getter private RaceTrack raceTrack;
	@Getter private double fps; // the fps of the race.
	
	@Getter private double raceTime; // time elapse of the race;
	private double timeInterval; // the time process of the race after each iteration.
	private RaceAI raceAI;
	
		
	public RaceController(RaceTrack raceTrack, Map<Integer, RaceHorse> raceHorseMap, double fps) throws InvalidLaneIdException {
		this.raceTrack = raceTrack;
		this.raceHorseMap = raceHorseMap;
		this.fps = fps;
		
		this.timeInterval = 1d / fps;
		this.raceTime = 0d;
		this.raceAI = new RaceAI(raceTrack, raceHorseMap);
	}
	
	public void generateRace() {
		while (!this.raceAI.isRaceFinished()) {
			raceAI.update(timeInterval);
			raceTime += timeInterval;
		}
	}
}
