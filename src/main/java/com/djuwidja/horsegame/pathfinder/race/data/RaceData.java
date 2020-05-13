package com.djuwidja.horsegame.pathfinder.race.data;

import java.util.List;
import java.util.Map;

import lombok.Getter;

public class RaceData {
	private static final String STR_FORMAT = "RaceData[totalRaceTime=%f timeTrack=%s horsePathDataMap=%s]";
	
	private static final String HORSE_PATH_STR_FORMAT = "{id=%d, path=[%s]}";
	
	@Getter private double totalRaceTime;
	@Getter private List<Double> timeTrack;
	@Getter private Map<Integer, List<RaceHorsePathData>> horsePathDataMap;
	
	public RaceData(double totalRaceTime, List<Double> timeTrack, Map<Integer, List<RaceHorsePathData>> horsePathDataMap) {
		this.totalRaceTime = totalRaceTime;
		this.timeTrack = timeTrack;
		this.horsePathDataMap = horsePathDataMap;
	}
	
	@Override
	public String toString() {
		String timeTrackStr = "";
		for (int i = 0; i < timeTrack.size(); i++) {
			if (i != 0) {
				timeTrackStr += ",";
			}
			timeTrackStr += String.format("%f", timeTrack.get(i));
		}
		
		boolean isFirst = true;
		String horseMapStr = "";
		for (Map.Entry<Integer, List<RaceHorsePathData>> entry : horsePathDataMap.entrySet()) {
			if (!isFirst) {
				horseMapStr += ",";
			}
			int horseId = entry.getKey();
			List<RaceHorsePathData> path = entry.getValue();
			
			String horsePathStr = String.format(HORSE_PATH_STR_FORMAT, horseId, getHorsePathStr(path));
			horseMapStr += horsePathStr;
			isFirst = false;
		}
		
		return String.format(STR_FORMAT, totalRaceTime, timeTrackStr, horseMapStr);
	}
	
	private String getHorsePathStr(List<RaceHorsePathData> path) {
		String result = "";
		for (int i = 0; i < path.size(); i++) {
			if (i != 0) {
				result += ",";
			}
			result += path.get(i).toString();
		}
		
		return result;
	}
}
