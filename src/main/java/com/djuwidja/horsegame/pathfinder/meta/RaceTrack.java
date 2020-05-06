package com.djuwidja.horsegame.pathfinder.meta;

import lombok.Getter;

public abstract class RaceTrack {
	@Getter private TerrainType terrainType;
	
	public RaceTrack(TerrainType terrainType) {
		this.terrainType = terrainType;
	}
	
	public abstract StartPoint getStartPoint(int laneId) throws InvalidLaneIdException;
}
