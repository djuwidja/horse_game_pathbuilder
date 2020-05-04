package com.djuwidja.horsegame.pathfinder.meta;

import lombok.Getter;

public class RaceTrack {
	@Getter private int trackType;
	
	public RaceTrack(int trackType) {
		this.trackType = trackType;
	}
}
