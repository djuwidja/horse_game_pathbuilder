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
import com.djuwidja.horsegame.pathfinder.db.repository.TrackVectorRepository;
import com.djuwidja.horsegame.pathfinder.db.repository.dao.TrackVectorDao;
import com.djuwidja.testbase.ExceptionFacilitator;
import com.djuwidja.testbase.TestBase;

@SpringBootTest
@ContextConfiguration(classes=TestConfig.class)
@TestInstance(Lifecycle.PER_CLASS)
public class TrackVectorDBWrapperTest extends TestBase {
	@Autowired private TrackVectorDBWrapper trackVectorDBWrapper;
	@Autowired private TrackVectorRepository trackVectorRepo;
	
	@BeforeAll
	public void setup() {
		trackVectorRepo.deleteAll();
		
		TrackVectorDao trackVector1 = new TrackVectorDao();
		trackVector1.setTrackId(1);
		trackVector1.setX(20.58d);
		trackVector1.setZ(-147.82d);
		trackVector1.setSeq(1);
		
		trackVectorRepo.save(trackVector1);
		
		TrackVectorDao trackVector2 = new TrackVectorDao();
		trackVector2.setTrackId(1);
		trackVector2.setX(-70.22d);
		trackVector2.setZ(217.47d);
		trackVector2.setSeq(3);
		
		trackVectorRepo.save(trackVector2);
		
		TrackVectorDao trackVector3 = new TrackVectorDao();
		trackVector3.setTrackId(1);
		trackVector3.setX(0.71d);
		trackVector3.setZ(51.44d);
		trackVector3.setSeq(2);
		
		trackVectorRepo.save(trackVector3);
		
		TrackVectorDao trackVector4 = new TrackVectorDao();
		trackVector4.setTrackId(2);
		trackVector4.setX(0.08d);
		trackVector4.setZ(12.18d);
		trackVector4.setSeq(1);
		
		trackVectorRepo.save(trackVector4);
	}
	
	@Test
	public void testContextLoad() {
		
	}
	
	@Test
	public void testFind() throws ResourceNotFoundException {
		List<TrackVectorDao> trackVectorDaoList = trackVectorDBWrapper.findAll();
		Assert.assertEquals(4, trackVectorDaoList.size());
		
		int computedMaxId = 0;
		for (TrackVectorDao vec: trackVectorDaoList){
			if (vec.getId() > computedMaxId) {
				computedMaxId = vec.getId();
			}
		}
		
		final int maxId = computedMaxId;
		// success case - findById
		TrackVectorDao trackVector = trackVectorDBWrapper.findById(maxId);
		Assert.assertEquals(2, trackVector.getTrackId().intValue());
		Assert.assertEquals(0.08d, trackVector.getX().doubleValue(), 0.0000001d);
		Assert.assertEquals(12.18d,  trackVector.getZ().doubleValue(), 0.0000001d);
		Assert.assertEquals(1, trackVector.getSeq().intValue());
		
		// fail case
		this.testExceptionThrown(new ExceptionFacilitator<ResourceNotFoundException>() {
			@Override
			public void testForException() throws ResourceNotFoundException {
				trackVectorDBWrapper.findById(maxId + 1);
			}
		}, ResourceNotFoundException.class);
		
		// success case - findByTrackIdOrderBySeqAsc
		List<TrackVectorDao> trackId1List = trackVectorDBWrapper.findByTrackIdOrderBySeqAsc(1);
		Assert.assertEquals(3, trackId1List.size());
		
		List<TrackVectorDao> trackId2List = trackVectorDBWrapper.findByTrackIdOrderBySeqAsc(2);
		Assert.assertEquals(1, trackId2List.size());
		
		List<TrackVectorDao> trackId3List = trackVectorDBWrapper.findByTrackIdOrderBySeqAsc(3);
		Assert.assertEquals(0, trackId3List.size());
	}
}
