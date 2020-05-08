package com.djuwidja.horsegame.pathfinder;

import java.nio.ByteBuffer;
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
	
	public byte[] serialize() {
		ByteBuffer buffer = ByteBuffer.allocate(4500000);
		buffer.putDouble(totalRaceTime); // 8
		buffer.putInt(timeTrack.size());
		for (Double timeInterval : timeTrack) {
			buffer.putDouble(timeInterval);
		}
		buffer.putInt(horsePathDataMap.size()); // 4
		for (Map.Entry<Integer, List<RaceHorsePathData>> entry : horsePathDataMap.entrySet()) {
			int horseId = entry.getKey();
			List<RaceHorsePathData> pathData = entry.getValue();
			buffer.putInt(horseId); // 4
			buffer.putInt(pathData.size()); // 4
			for (RaceHorsePathData data : pathData) {
				buffer.putDouble(data.getPosition().getX()); // 8
				buffer.putDouble(data.getPosition().getY()); // 8
				buffer.putDouble(data.getDirection().getX()); // 8
				buffer.putDouble(data.getDirection().getY()); // 8
				buffer.putDouble(data.getSpeed()); // 8
			}
		}
		
		int lastPosition = buffer.position();
		byte[] result = new byte[lastPosition];
		buffer.position(0);
		buffer.get(result, 0, lastPosition);
		return result;
	}
}
