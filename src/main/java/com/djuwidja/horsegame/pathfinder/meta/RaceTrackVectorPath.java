package com.djuwidja.horsegame.pathfinder.meta;

import java.awt.geom.Point2D;
import java.util.Map;

import com.djuwidja.horsegame.pathfinder.math.Vector2D;
import com.djuwidja.horsegame.pathfinder.meta.curve.TrackSectionCurveException;
import com.djuwidja.horsegame.pathfinder.meta.curve.vectorpath.VectorPathCurve;
import com.djuwidja.horsegame.pathfinder.meta.path.vectorpath.ConstructorParamException;

import lombok.Getter;

public class RaceTrackVectorPath extends RaceTrack {
	@Getter private VectorPathCurve path;
	private Map<Integer, StartPoint> startPointMap;

	public RaceTrackVectorPath(
			TerrainType terrainType, 
			Point2D finishLinePtA, 
			Point2D finishLinePtB, 
			int finishLineActivation, 
			Point2D[] ptList,
			Point2D controlPt,
			Map<Integer, StartPoint> startPointMap) throws ConstructorParamException {
		super(terrainType, finishLinePtA, finishLinePtB, finishLineActivation);
		this.path = new VectorPathCurve(ptList, controlPt);
		if (startPointMap.size() == 0) {
			throw new ConstructorParamException("Start Point Map is empty.");
		}		
		this.startPointMap = startPointMap;
	}

	@Override
	public StartPoint getStartPoint(int laneId) throws InvalidLaneIdException {
		if (startPointMap.containsKey(laneId)) {
			return startPointMap.get(laneId);
		} else {
			throw new InvalidLaneIdException();
		}
	}
	
	@Override
	public Vector2D getGuidingVector(Point2D position) throws TrackSectionCurveException {
		return path.getTangentVector(position);
	}
}
