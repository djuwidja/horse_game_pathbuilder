package com.djuwidja.horsegame.pathfinder.meta.path.vectorpath;

import java.awt.geom.Point2D;

import lombok.Getter;

public class VectorPath {
	@Getter private VectorPathSect[] vectorList;
	
	public VectorPath(Point2D[] pointList) throws ConstructorParamException {
		if (pointList.length < 2) {
			throw new ConstructorParamException("Length of point list must be at least 2.");
		}
		
		vectorList = new VectorPathSect[pointList.length];
		
		for (int i = 0; i < pointList.length; i++) {
			Point2D startPt;
			Point2D endPt;
			if (i == pointList.length - 1) {
				// path loop back to first point at the end
				startPt = pointList[i];
				endPt = pointList[0];
			} else {
				startPt = pointList[i];
				endPt = pointList[i + 1];
			}
			
			vectorList[i] = new VectorPathSect(startPt, endPt);
		}
	}
}
