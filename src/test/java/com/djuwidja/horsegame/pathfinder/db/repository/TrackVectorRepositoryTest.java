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
import com.djuwidja.horsegame.pathfinder.db.repository.dao.TrackVectorDao;

@SpringBootTest
@ContextConfiguration(classes=TestConfig.class)
@TestInstance(Lifecycle.PER_CLASS)
public class TrackVectorRepositoryTest {
	@Autowired private TrackVectorRepository trackVectorRepo;
	
	@BeforeAll
	public void setup() {
		trackVectorRepo.deleteAll();
		
		TrackVectorDao trackVector1 = TrackVectorDao.create(1, 20.58d, -147.82d, 1);		
		trackVectorRepo.save(trackVector1);
		
		TrackVectorDao trackVector2 = TrackVectorDao.create(1, -70.22d, 217.47d, 3);	
		trackVectorRepo.save(trackVector2);
		
		TrackVectorDao trackVector3 = TrackVectorDao.create(1, 0.71d, 51.44d, 2);		
		trackVectorRepo.save(trackVector3);
		
		TrackVectorDao trackVector4 = TrackVectorDao.create(2, 0.08d, 12.18d, 1);		
		trackVectorRepo.save(trackVector4);
	}
	
	@Test
	public void testContextLoad() {
		
	}
	
	@Test
	public void testRetrieveData() {
		List<TrackVectorDao> trackVectorList = new ArrayList<>();
		Iterable<TrackVectorDao> iterable = trackVectorRepo.findAll();
		iterable.forEach(trackVectorList::add);
		
		Assert.assertEquals(4, trackVectorList.size());
		assertTrackVector(trackVectorList.get(0), 1, 20.58d, -147.82d, 1);
		assertTrackVector(trackVectorList.get(1), 1, -70.22d, 217.47d, 3);
		assertTrackVector(trackVectorList.get(2), 1, 0.71d, 51.44d, 2);
		assertTrackVector(trackVectorList.get(3), 2, 0.08d, 12.18d, 1);
	}
	
	@Test
	public void testFindByTrackId() {
		List<TrackVectorDao> trackVectorList = new ArrayList<>();
		Iterable<TrackVectorDao> iterable = trackVectorRepo.findByTrackIdOrderBySeqAsc(1);
		iterable.forEach(trackVectorList::add);
		
		Assert.assertEquals(3,  trackVectorList.size());
		assertTrackVector(trackVectorList.get(0), 1, 20.58d, -147.82d, 1);
		assertTrackVector(trackVectorList.get(1), 1, 0.71d, 51.44d, 2);
		assertTrackVector(trackVectorList.get(2), 1, -70.22d, 217.47d, 3);
	}
	
	private void assertTrackVector(TrackVectorDao trackVector, int trackId, double x, double z, int seq) {
		Assert.assertEquals(trackId, trackVector.getTrackId().intValue());
		Assert.assertEquals(x, trackVector.getX().doubleValue(), 0.00000001d);
		Assert.assertEquals(z, trackVector.getZ().doubleValue(), 0.00000001d);
		Assert.assertEquals(seq, trackVector.getSeq().intValue());
	}
}
