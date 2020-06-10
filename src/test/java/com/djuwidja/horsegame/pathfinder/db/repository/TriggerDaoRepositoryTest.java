package com.djuwidja.horsegame.pathfinder.db.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.djuwidja.horsegame.pathfinder.TestConfig;
import com.djuwidja.horsegame.pathfinder.db.repository.dao.TriggerDao;

import org.junit.Assert;

@SpringBootTest
@ContextConfiguration(classes=TestConfig.class)
@TestInstance(Lifecycle.PER_CLASS)
public class TriggerDaoRepositoryTest {
	@Autowired private TriggerDaoRepository triggerDaoRepo;
	
	@BeforeAll
	public void setup() {
		triggerDaoRepo.deleteAll();
		
		TriggerDao trigger1 = TriggerDao.create(1, 1);
		triggerDaoRepo.save(trigger1);
		
		TriggerDao trigger2 = TriggerDao.create(1, 1);
		triggerDaoRepo.save(trigger2);
		
		TriggerDao trigger3 = TriggerDao.create(1, 1);
		triggerDaoRepo.save(trigger3);
		
		TriggerDao trigger4 = TriggerDao.create(1, 1);
		triggerDaoRepo.save(trigger4);
		
		TriggerDao trigger5 = TriggerDao.create(2, 2);	
		triggerDaoRepo.save(trigger5);
	}
	
	@Test
	public void testContextLoad() {
	
	}
	
	@Test
	public void testRetrieveData() {
		List<TriggerDao> daoList = new ArrayList<>();
		Iterable<TriggerDao> daoIter = triggerDaoRepo.findAll();
		daoIter.forEach(daoList::add);
		
		Assert.assertEquals(5, daoList.size());
		assertTriggerDao(daoList.get(0), 1, 1);
		assertTriggerDao(daoList.get(1), 1, 1);
		assertTriggerDao(daoList.get(2), 1, 1);
		assertTriggerDao(daoList.get(3), 1, 1);
		assertTriggerDao(daoList.get(4), 2, 2);
	}
	
	@Test
	public void testGetByTrackId() {
		List<TriggerDao> daoList = new ArrayList<>();
		Iterable<TriggerDao> daoIter = triggerDaoRepo.findByTrackId(1);
		daoIter.forEach(daoList::add);
		
		Assert.assertEquals(4, daoList.size());
		for (TriggerDao dao : daoList) {
			Assert.assertEquals(1, dao.trackId.intValue());
		}
	}
	
	private void assertTriggerDao(TriggerDao result, int expectedTrackId, int expectedType) {
		Assert.assertNotNull(result.id);
		Assert.assertEquals(expectedTrackId, result.trackId.intValue());
		Assert.assertEquals(expectedType, result.type.intValue());
	}
}
