package com.djuwidja.horsegame.pathfinder.race.data;

import java.util.List;
import java.util.Map;

import lombok.Getter;

public class RaceData {
	@Getter private double totalRaceTime;
	@Getter private List<Double> timeTrack;
	@Getter private Map<Integer, List<RaceHorsePathData>> horsePathDataMap;
	
	public RaceData(double totalRaceTime, List<Double> timeTrack, Map<Integer, List<RaceHorsePathData>> horsePathDataMap) {
		this.totalRaceTime = totalRaceTime;
		this.timeTrack = timeTrack;
		this.horsePathDataMap = horsePathDataMap;
	}
}
