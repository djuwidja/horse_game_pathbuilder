package com.djuwidja.horsegame.pathfinder.ai;

import java.util.Map;

import com.djuwidja.horsegame.pathfinder.meta.InvalidLaneIdException;
import com.djuwidja.horsegame.pathfinder.meta.RaceHorse;
import com.djuwidja.horsegame.pathfinder.meta.RaceTrack;
import com.djuwidja.horsegame.pathfinder.meta.StartPoint;

import lombok.Getter;

public class RaceAI implements AI{
	@Getter private RaceTrack raceTrack;
	private RaceHorseAI[] raceHorseAIList;
	
	@Getter private boolean isRaceFinished;
	
	public RaceAI(RaceTrack raceTrack, Map<Integer, RaceHorse> raceHorseMap) throws InvalidLaneIdException {
		this.raceTrack = raceTrack;
		this.raceHorseAIList = new RaceHorseAI[raceHorseMap.size()];
		int i = 0;
		for (Map.Entry<Integer, RaceHorse> entry : raceHorseMap.entrySet()) {
			int laneId = entry.getKey();
			RaceHorse horse = entry.getValue();
			StartPoint startPoint = raceTrack.getStartPoint(laneId);
			RaceHorseAI horseAI = new RaceHorseAI(horse, raceTrack, startPoint);
			raceHorseAIList[i] = horseAI;
			i++;
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
