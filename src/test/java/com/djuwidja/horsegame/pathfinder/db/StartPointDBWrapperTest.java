package com.djuwidja.horsegame.pathfinder.db;

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
import com.djuwidja.horsegame.pathfinder.db.repository.StartPointRepository;
import com.djuwidja.horsegame.pathfinder.db.repository.dao.StartPointDao;
import com.djuwidja.testbase.ExceptionFacilitator;
import com.djuwidja.testbase.TestBase;



@SpringBootTest
@ContextConfiguration(classes=TestConfig.class)
@TestInstance(Lifecycle.PER_CLASS)
public class StartPointDBWrapperTest extends TestBase {
	@Autowired private StartPointDBWrapper startPointDBWrapper;
	@Autowired private StartPointRepository startPointRepo;
	
	@BeforeAll
	public void setup() {
		startPointRepo.deleteAll();	
		startPointRepo.save(createStartPointDao(1, 1, 0.681d, 0.772d, 0.316d, 0.1208d));
		startPointRepo.save(createStartPointDao(1, 2, 0.12d, 0.821d, 0.084d, 0.487d));
		startPointRepo.save(createStartPointDao(1, 3, 0.439d, 0.249d, 0.549d, 0.919d));
		startPointRepo.save(createStartPointDao(2, 1, 0.977d, 0.578d, 0.349d, 0.5477d));		
	}
	
	private StartPointDao createStartPointDao(int setId, int laneId, double x, double z, double vecX, double vecZ) {
		StartPointDao startPointDao = new StartPointDao();
		startPointDao.setSetId(setId);
		startPointDao.setLaneId(laneId);
		startPointDao.setX(x);
		startPointDao.setZ(z);
		startPointDao.setVecX(vecX);
		startPointDao.setVecZ(vecZ);
		return startPointDao;
	}
	
	@Test
	public void testContextLoad() {
		
	}
	
	@Test
	public void testFind() throws ResourceNotFoundException {
		List<StartPointDao> startPointList = startPointDBWrapper.findAll();
		Assert.assertEquals(4, startPointList.size());
		
		int computeMaxId = 0;
		for (StartPointDao startPoint : startPointList) {
			if (startPoint.getId() > computeMaxId) {
				computeMaxId = startPoint.getId();
			}
		}
		
		final int maxId = computeMaxId;
		
		// success case - findById
		StartPointDao startPoint = startPointDBWrapper.findById(maxId);
		assertStartPoint(startPoint, 2, 1, 0.977d, 0.578d, 0.349d, 0.5477d);
		
		// fail case
		this.testExceptionThrown(new ExceptionFacilitator<ResourceNotFoundException>() {
			@Override
			public void testForException() throws ResourceNotFoundException {
				startPointDBWrapper.findById(maxId + 1);			
			}	
		}, ResourceNotFoundException.class);
		
		// success case - findBySetIdOrderByLaneId
		List<StartPointDao> set1List = startPointDBWrapper.findBySetIdOrderByLaneIdAsc(1);
		Assert.assertEquals(3, set1List.size());
		for (int i = 0; i < set1List.size(); i++) {
			int laneId = i + 1;
			Assert.assertEquals(laneId, set1List.get(i).getLaneId().intValue());
		}
		
		List<StartPointDao> set2List = startPointDBWrapper.findBySetIdOrderByLaneIdAsc(2);
		Assert.assertEquals(1, set2List.size());
		
		List<StartPointDao> set3List = startPointDBWrapper.findBySetIdOrderByLaneIdAsc(3);
		Assert.assertEquals(0, set3List.size());
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
