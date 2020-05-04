package com.djuwidja.horsegame.pathfinder.meta;

import java.awt.geom.Point2D;
import java.util.Map;

import com.djuwidja.horsegame.pathfinder.meta.curve.vectorpath.VectorPathCurve;
import com.djuwidja.horsegame.pathfinder.meta.path.vectorpath.ConstructorParamException;

import lombok.Getter;

public class RaceTrackVectorPath extends RaceTrack{
	@Getter private VectorPathCurve path;
	private Map<Integer, StartPoint> startPointMap;

	public RaceTrackVectorPath(TrackType trackType, Point2D[] ptList, Map<Integer, StartPoint> startPointMap) throws ConstructorParamException {
		super(trackType);
		this.path = new VectorPathCurve(ptList);
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
}
