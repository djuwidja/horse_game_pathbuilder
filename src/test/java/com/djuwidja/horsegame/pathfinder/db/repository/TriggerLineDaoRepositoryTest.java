package com.djuwidja.horsegame.pathfinder.db.repository;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.djuwidja.horsegame.pathfinder.db.repository.dao.TriggerLineDao;

@SpringBootTest
@ContextConfiguration(classes=TestConfig.class)
@TestInstance(Lifecycle.PER_CLASS)
public class TriggerLineDaoRepositoryTest {
	@Autowired private TriggerLineDaoRepository triggerLineDaoRepo;
	
	@BeforeAll
	public void setup() {
		triggerLineDaoRepo.deleteAll();
		
		TriggerLineDao line1 = TriggerLineDao.create(1, 0d, 10d, 0d, -10d);
		triggerLineDaoRepo.save(line1);
		
		TriggerLineDao line2 = TriggerLineDao.create(2, 0d, 10d, 0d, -10d);
		triggerLineDaoRepo.save(line2);
		
		TriggerLineDao line3 = TriggerLineDao.create(3, 0d, 10d, 0d, -10d);
		triggerLineDaoRepo.save(line3);
		
		TriggerLineDao line4 = TriggerLineDao.create(4, 0d, 10d, 0d, -10d);
		triggerLineDaoRepo.save(line4);
	}
	
	@Test
	public void testContextLoad() {
	
	}
	
	@Test
	public void testRetrieveData() {
		List<TriggerLineDao> daoList = new ArrayList<>();
		Iterable<TriggerLineDao> daoIter = triggerLineDaoRepo.findAll();
		daoIter.forEach(daoList::add);
		
		Assert.assertEquals(4, daoList.size());
	}
	
	@Test
	public void testFindTriggerLineByTrackIds() {
		List<Integer> idList = Arrays.asList(new Integer[] {1, 2, 3});
	
		List<TriggerLineDao> daoList = new ArrayList<>();
		Iterable<TriggerLineDao> daoIter = triggerLineDaoRepo.findAllByTrackTriggerIdIn(idList);
		daoIter.forEach(daoList::add);
		
		Assert.assertEquals(3, daoList.size());		
	}
}
