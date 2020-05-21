package com.djuwidja.horsegame.pathfinder.meta;

import java.awt.geom.Point2D;

import com.djuwidja.horsegame.pathfinder.math.Line2D;
import com.djuwidja.horsegame.pathfinder.math.Vector2D;
import com.djuwidja.horsegame.pathfinder.meta.curve.TrackSectionCurveException;

import lombok.Getter;

public abstract class RaceTrack {
	@Getter private TerrainType terrainType;
	@Getter private Line2D finishLine;
	@Getter private int finishLineActivation;
	
	public RaceTrack(TerrainType terrainType, Point2D finishLinePtA, Point2D finishLinePt2, int finishLineActivation) {
		this.terrainType = terrainType;
		this.finishLine = new Line2D(finishLinePtA, finishLinePt2);
		this.finishLineActivation = finishLineActivation;
	}
	
	public abstract StartPoint getStartPoint(int laneId) throws InvalidLaneIdException;
	public abstract Vector2D getGuidingVector(Point2D position) throws TrackSectionCurveException;
}
