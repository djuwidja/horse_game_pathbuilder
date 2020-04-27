package com.djuwidja.horsegame.pathfinder.meta.path.vectorpath;

import java.awt.geom.Point2D;

import com.djuwidja.horsegame.pathfinder.math.Vector2D;
import com.djuwidja.horsegame.pathfinder.meta.curve.TrackSectionCurve;

import lombok.Getter;

public class VectorPath implements TrackSectionCurve{
	@Getter private VectorPathSect[] vectorList;
	
	public VectorPath(Point2D[] pointList) throws ConstructorParamException {
		if (pointList.length < 2) {
			throw new ConstructorParamException("Length of point list must be at least 2.");
		}
		
		vectorList = new VectorPathSect[pointList.length - 1];
		
		for (int i = 1; i < pointList.length; i++) {
			Point2D startPt = pointList[i-1];
			Point2D endPt = pointList[i];
			
			vectorList[i - 1] = new VectorPathSect(startPt, endPt);
		}
	}

	@Override
	public Vector2D getTangentVector(Point2D pt) {
		// TODO Auto-generated method stub
		return null;
	}
}