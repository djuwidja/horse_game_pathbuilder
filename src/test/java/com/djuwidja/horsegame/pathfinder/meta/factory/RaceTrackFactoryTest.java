package com.djuwidja.horsegame.pathfinder.meta.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.djuwidja.horsegame.pathfinder.db.repository.dao.RaceTrackDao;
import com.djuwidja.horsegame.pathfinder.db.repository.dao.StartPointDao;
import com.djuwidja.horsegame.pathfinder.db.repository.dao.TrackVectorDao;
import com.djuwidja.horsegame.pathfinder.meta.RaceTrack;
import com.djuwidja.horsegame.pathfinder.meta.RaceTrackVectorPath;
import com.djuwidja.horsegame.pathfinder.meta.TerrainType;
import com.djuwidja.horsegame.pathfinder.meta.path.vectorpath.ConstructorParamException;

public class RaceTrackFactoryTest {
	@Test
	public void testCreateSuccess() throws ConstructorParamException {
		Random rand = new Random();
		
		RaceTrackDao trackDao = new RaceTrackDao();
		trackDao.setTerrainType(1);
		
		int startPointDaoListSize = 11 + rand.nextInt(9);
		List<StartPointDao> startPointDaoList = createStartPointDaoList(rand, startPointDaoListSize);
		
		int trackVectorDaoListSize = 11 + rand.nextInt(9);
		List<TrackVectorDao> trackVectorDaoList = createTrackVectorDaoList(rand, trackVectorDaoListSize);
		
		RaceTrack raceTrack = RaceTrackFactory.createFromDao(trackDao, trackVectorDaoList, startPointDaoList);
		Assert.assertTrue(raceTrack.getClass() == RaceTrackVectorPath.class);
		Assert.assertEquals(TerrainType.GRASS, raceTrack.getTerrainType());
	}
	
	private List<StartPointDao> createStartPointDaoList(Random rand, int daoListSize) {
		List<StartPointDao> daoList = new ArrayList<>();
		for (int i = 0; i < daoListSize; i++) {
			StartPointDao startPointDao = new StartPointDao();
			startPointDao.setSetId(1);
			startPointDao.setLaneId(i);
			startPointDao.setX(rand.nextDouble());
			startPointDao.setZ(rand.nextDouble());
			startPointDao.setVecX(rand.nextDouble());
			startPointDao.setVecZ(rand.nextDouble());
			daoList.add(startPointDao);
		}
		return daoList;
	}
	
	private List<TrackVectorDao> createTrackVectorDaoList(Random rand, int daoListSize) {
		List<TrackVectorDao> daoList = new ArrayList<>();
		for (int i = 0; i < daoListSize; i++) {
			TrackVectorDao dao = new TrackVectorDao();
			dao.setTrackId(1);
			dao.setX(rand.nextDouble());
			dao.setZ(rand.nextDouble());
			dao.setSeq(i);
			daoList.add(dao);
		}
		return daoList;
	}
}
