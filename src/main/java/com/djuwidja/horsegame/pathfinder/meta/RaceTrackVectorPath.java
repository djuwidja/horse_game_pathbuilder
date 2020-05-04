package com.djuwidja.horsegame.pathfinder.meta;

import java.awt.geom.Point2D;

import com.djuwidja.horsegame.pathfinder.meta.curve.vectorpath.VectorPathCurve;
import com.djuwidja.horsegame.pathfinder.meta.path.vectorpath.ConstructorParamException;

import lombok.Getter;

public class RaceTrackVectorPath extends RaceTrack{
	@Getter private VectorPathCurve path;

	public RaceTrackVectorPath(int trackType, Point2D[] ptList) throws ConstructorParamException {
		super(trackType);
		this.path = new VectorPathCurve(ptList);
	}

}
