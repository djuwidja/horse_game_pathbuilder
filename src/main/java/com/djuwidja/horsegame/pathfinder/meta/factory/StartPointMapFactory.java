package com.djuwidja.horsegame.pathfinder.meta.factory;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.djuwidja.horsegame.pathfinder.db.repository.dao.StartPointDao;
import com.djuwidja.horsegame.pathfinder.math.Vector2D;
import com.djuwidja.horsegame.pathfinder.meta.StartPoint;

public class StartPointMapFactory {
	public static Map<Integer, StartPoint> createStartPointMapFromDao(List<StartPointDao> daoList){
		Map<Integer, StartPoint> result = new HashMap<>();
		for (StartPointDao dao : daoList) {
			Point2D startPos = new Point2D.Double(dao.getX(), dao.getZ());
			Vector2D vec = new Vector2D(dao.getVecX(), dao.getVecZ());
			StartPoint startPoint = new StartPoint(startPos, vec);
			result.put(dao.getLaneId(), startPoint);
		}
		
		return result;
	}
}
