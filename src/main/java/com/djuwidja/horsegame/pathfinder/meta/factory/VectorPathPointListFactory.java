package com.djuwidja.horsegame.pathfinder.meta.factory;

import java.awt.geom.Point2D;
import java.util.List;

import com.djuwidja.horsegame.pathfinder.db.repository.dao.TrackVectorDao;

public class VectorPathPointListFactory {
	public static Point2D[] createPtListFromDao(List<TrackVectorDao> daoList) {
		Point2D[] result = new Point2D[daoList.size()];
		for (int i = 0; i < daoList.size(); i++) {
			TrackVectorDao dao = daoList.get(i);
			Point2D pt = new Point2D.Double(dao.getX(), dao.getZ());
			result[i] = pt;
		}
		
		return result;
	}
}
