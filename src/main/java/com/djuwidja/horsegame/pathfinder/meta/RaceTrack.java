package com.djuwidja.horsegame.pathfinder.meta;

import lombok.Getter;

public abstract class RaceTrack {
	@Getter private TrackType trackType;
	
	public RaceTrack(TrackType trackType) {
		this.trackType = trackType;
	}
	
	public abstract StartPoint getStartPoint(int laneId) throws InvalidLaneIdException;
}
