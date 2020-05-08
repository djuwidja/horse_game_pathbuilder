package com.djuwidja.horsegame.pathfinder.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.djuwidja.horsegame.pathfinder.RaceData;
import com.djuwidja.horsegame.pathfinder.RaceHorsePathData;
import com.djuwidja.horsegame.pathfinder.meta.InvalidLaneIdException;
import com.djuwidja.horsegame.pathfinder.meta.RaceHorse;
import com.djuwidja.horsegame.pathfinder.meta.RaceTrack;
import com.djuwidja.horsegame.pathfinder.meta.StartPoint;

import lombok.Getter;

public class RaceAI implements AI {
	@Getter private RaceTrack raceTrack;
	private List<RaceHorseAI> raceHorseAIList;
	private List<Double> timeTrack;
	
	@Getter private boolean isRaceFinished;
	private Random rand;
	private double raceTime;
	
	public RaceAI(RaceTrack raceTrack, Map<Integer, RaceHorse> raceHorseMap) throws InvalidLaneIdException {
		this.rand = new Random();
		this.raceTrack = raceTrack;
		this.raceHorseAIList = new ArrayList<>(raceHorseMap.size());
		for (Map.Entry<Integer, RaceHorse> entry : raceHorseMap.entrySet()) {
			int laneId = entry.getKey();
			RaceHorse horse = entry.getValue();
			StartPoint startPoint = raceTrack.getStartPoint(laneId);
			RaceHorseAI horseAI = new RaceHorseAI(horse, raceTrack, startPoint);
			raceHorseAIList.add(horseAI);
		}

		this.isRaceFinished = false;
		this.raceTime = 0d;
		this.timeTrack = new ArrayList<>();
	}
	
	@Override
	public void update(double timeDelta) {
		if (!this.isRaceFinished) {
			raceTime += timeDelta;
			timeTrack.add(raceTime);
			Collections.shuffle(raceHorseAIList, rand);
			this.isRaceFinished = true;
			
			for (RaceHorseAI ai : raceHorseAIList) {
				ai.update(timeDelta);
				if (!ai.isFinished) {
					this.isRaceFinished = false;
				}
			}
		}	
	}
	
	public RaceData getRaceData() {
		Map<Integer, List<RaceHorsePathData>> pathDataMap = new HashMap<>();
		for (RaceHorseAI ai : raceHorseAIList) {
			int id = ai.getRaceHorse().getHorseId();
			List<RaceHorsePathData> pathData = ai.getPositionDataList();
			pathDataMap.put(id, pathData);
		}
		return new RaceData(this.raceTime, this.timeTrack, pathDataMap);
	}
}
