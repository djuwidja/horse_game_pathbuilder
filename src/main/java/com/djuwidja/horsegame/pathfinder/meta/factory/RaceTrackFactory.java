package com.djuwidja.horsegame.pathfinder.meta.factory;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;

import com.djuwidja.horsegame.pathfinder.db.repository.dao.RaceTrackDao;
import com.djuwidja.horsegame.pathfinder.db.repository.dao.StartPointDao;
import com.djuwidja.horsegame.pathfinder.db.repository.dao.TrackVectorDao;
import com.djuwidja.horsegame.pathfinder.meta.RaceTrack;
import com.djuwidja.horsegame.pathfinder.meta.RaceTrackVectorPath;
import com.djuwidja.horsegame.pathfinder.meta.StartPoint;
import com.djuwidja.horsegame.pathfinder.meta.TerrainType;
import com.djuwidja.horsegame.pathfinder.meta.path.vectorpath.ConstructorParamException;

public class RaceTrackFactory {
	public static RaceTrack createFromDao(
			RaceTrackDao raceTrackDao, 
			List<TrackVectorDao> trackVectorDaoList,
			List<StartPointDao> startPointDaoList) throws ConstructorParamException {
		TerrainType terrainType = getTerrainType(raceTrackDao.getTerrainType());
		
		Point2D finishLinePtA = new Point2D.Double(raceTrackDao.getFinishLinePt1X(), raceTrackDao.getFinishLinePt1Z());
		Point2D finishLinePtB = new Point2D.Double(raceTrackDao.getFinishLinePt2X(), raceTrackDao.getFinishLinePt2Z());
		
		int finishLineActivation = raceTrackDao.getFinishLineActivation();
		
		Point2D[] ptList = VectorPathPointListFactory.createPtListFromDao(trackVectorDaoList);
		Map<Integer, StartPoint> startPointMap = StartPointMapFactory.createStartPointMapFromDao(startPointDaoList);
		
		return new RaceTrackVectorPath(terrainType, finishLinePtA, finishLinePtB, finishLineActivation, ptList, startPointMap);
	}
	
	private static TerrainType getTerrainType(int id) throws ConstructorParamException {
		switch (id) {
		case 1:
			return TerrainType.GRASS;
		case 2:
			return TerrainType.MUD;
		default:
			throw new ConstructorParamException(String.format("Unknown terrain type: %d.", id));
		}
	}
}
