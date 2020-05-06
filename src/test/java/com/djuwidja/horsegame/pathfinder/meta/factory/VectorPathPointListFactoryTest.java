package com.djuwidja.horsegame.pathfinder.meta.factory;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.djuwidja.horsegame.pathfinder.db.repository.dao.TrackVectorDao;

public class VectorPathPointListFactoryTest {
	@Test
	public void testCreate() {
		Random rand = new Random();
		int daoListSize = 11 + rand.nextInt(9);
		
		List<TrackVectorDao> daoList = createDaoList(rand, daoListSize);
		
		Point2D[] ptList = VectorPathPointListFactory.createPtListFromDao(daoList);
		Assert.assertEquals(daoListSize, ptList.length);
		for (int i = 0; i < daoListSize; i++) {
			Point2D pt = ptList[i];
			TrackVectorDao dao = daoList.get(i);
			Assert.assertEquals(dao.getX().doubleValue(), pt.getX(), 0.0000001d);
			Assert.assertEquals(dao.getZ().doubleValue(), pt.getY(), 0.0000001d);
		}
	}

	private List<TrackVectorDao> createDaoList(Random rand, int daoListSize) {
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
