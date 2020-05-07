package com.djuwidja.horsegame.pathfinder.meta.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.djuwidja.horsegame.pathfinder.db.repository.dao.StartPointDao;
import com.djuwidja.horsegame.pathfinder.meta.StartPoint;

public class StartPointMapFactoryTest {
	@Test
	public void testCreate() {
		Random rand = new Random();
		int daoListSize = 11 + rand.nextInt(10);
		
		List<StartPointDao> daoList = createDaoList(rand, daoListSize);
		
		Map<Integer, StartPoint> startPointMap = StartPointMapFactory.createStartPointMapFromDao(daoList);
		Assert.assertEquals(daoListSize, startPointMap.size());
		
		for (int i = 0; i < daoListSize; i++) {
			StartPointDao dao = daoList.get(i);
			int laneId = dao.getLaneId();
			
			Assert.assertTrue(startPointMap.containsKey(laneId));
			StartPoint startPoint = startPointMap.get(laneId);
			
			Assert.assertEquals(dao.getX().doubleValue(), startPoint.getStartPos().getX(), 0.000001d);
			Assert.assertEquals(dao.getZ().doubleValue(), startPoint.getStartPos().getY(), 0.000001d);
			Assert.assertEquals(dao.getVecX().doubleValue(), startPoint.getStartVec().getX(), 0.000001d);
			Assert.assertEquals(dao.getVecZ().doubleValue(), startPoint.getStartVec().getY(), 0.000001d);
		}
	}

	private List<StartPointDao> createDaoList(Random rand, int daoListSize) {
		List<StartPointDao> daoList = new ArrayList<>();
		for (int i = 0; i < daoListSize; i++) {
			StartPointDao startPointDao = StartPointDao.create(1, i, rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), rand.nextDouble());
			daoList.add(startPointDao);
		}
		return daoList;
	}
}
