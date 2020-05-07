package com.djuwidja.horsegame.pathfinder.db.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.djuwidja.horsegame.pathfinder.TestConfig;
import com.djuwidja.horsegame.pathfinder.db.repository.dao.StartPointDao;

@SpringBootTest
@ContextConfiguration(classes=TestConfig.class)
@TestInstance(Lifecycle.PER_CLASS)
public class StartPointrepositoryTest {
	@Autowired private StartPointRepository startPointRepo;
	
	@BeforeAll
	public void setup() {
		startPointRepo.deleteAll();	
		startPointRepo.save(StartPointDao.create(1, 1, 0.681d, 0.772d, 0.316d, 0.1208d));
		startPointRepo.save(StartPointDao.create(1, 2, 0.12d, 0.821d, 0.084d, 0.487d));
		startPointRepo.save(StartPointDao.create(1, 3, 0.439d, 0.249d, 0.549d, 0.919d));
		startPointRepo.save(StartPointDao.create(2, 1, 0.977d, 0.578d, 0.349d, 0.5477d));		
	}
		
	@Test
	public void testContextLoad() {
		
	}
	
	@Test
	public void testFind() {
		List<StartPointDao> startPointList = new ArrayList<>();
		Iterable<StartPointDao> iterable = startPointRepo.findAll();
		iterable.forEach(startPointList::add);
		
		Assert.assertEquals(4, startPointList.size());
		assertStartPoint(startPointList.get(0), 1, 1, 0.681d, 0.772d, 0.316d, 0.1208d);
		assertStartPoint(startPointList.get(1), 1, 2, 0.12d, 0.821d, 0.084d, 0.487d);
		assertStartPoint(startPointList.get(2), 1, 3, 0.439d, 0.249d, 0.549d, 0.919d);
		assertStartPoint(startPointList.get(3), 2, 1, 0.977d, 0.578d, 0.349d, 0.5477d);		
	}
	
	@Test
	public void testFindBySetIdOrderByLaneIdAsc() {
		List<StartPointDao> startPointList = new ArrayList<>();
		Iterable<StartPointDao> iterable = startPointRepo.findBySetIdOrderByLaneIdAsc(1);
		iterable.forEach(startPointList::add);
		
		Assert.assertEquals(3,  startPointList.size());
		for (int i = 0; i < startPointList.size(); i++) {
			int laneId = i + 1;
			Assert.assertEquals(laneId, startPointList.get(i).getLaneId().intValue());
		}
	}
	
	private void assertStartPoint(StartPointDao startPoint, int setId, int laneId, double x, double z, double vecX, double vecZ) {
		Assert.assertEquals(setId, startPoint.getSetId().intValue());
		Assert.assertEquals(laneId, startPoint.getLaneId().intValue());
		Assert.assertEquals(x, startPoint.getX().doubleValue(), 0.00000001d);
		Assert.assertEquals(z, startPoint.getZ().doubleValue(), 0.00000001d);
		Assert.assertEquals(vecX, startPoint.getVecX().doubleValue(), 0.00000001d);
		Assert.assertEquals(vecZ, startPoint.getVecZ().doubleValue(), 0.00000001d);
	}
}
