package com.djuwidja.horsegame.pathfinder.meta;

import java.awt.geom.Point2D;

import com.djuwidja.horsegame.pathfinder.math.Vector2D;
import com.djuwidja.horsegame.pathfinder.meta.curve.TrackSectionCurveException;

import lombok.Getter;

public abstract class RaceTrack {
	@Getter private TerrainType terrainType;
	
	public RaceTrack(TerrainType terrainType) {
		this.terrainType = terrainType;
	}
	
	public abstract StartPoint getStartPoint(int laneId) throws InvalidLaneIdException;
	public abstract Vector2D getGuidingVector(Point2D position, Vector2D normal) throws TrackSectionCurveException;
}
