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
import com.djuwidja.horsegame.pathfinder.db.repository.RaceTrackRepository;
import com.djuwidja.horsegame.pathfinder.db.repository.dao.RaceTrackDao;
import com.djuwidja.testbase.ExceptionFacilitator;
import com.djuwidja.testbase.TestBase;

@SpringBootTest
@ContextConfiguration(classes=TestConfig.class)
@TestInstance(Lifecycle.PER_CLASS)
public class RaceTrackDBWrapperTest extends TestBase {
	@Autowired private RaceTrackDBWrapper raceTrackDBWrapper;
	@Autowired private RaceTrackRepository raceTrackRepo;
	
	@BeforeAll
	public void setup() {
		raceTrackRepo.deleteAll();
		
		RaceTrackDao newTrack = new RaceTrackDao();
		newTrack.setTrackType(88);
		newTrack.setTerrainType(20);
		raceTrackRepo.save(newTrack);
	}
	
	@Test
	public void testContextLoad() {
		
	}
	
	@Test
	public void testFind() throws ResourceNotFoundException {
		List<RaceTrackDao> raceTrackList = raceTrackDBWrapper.findAll();
		Assert.assertEquals(1, raceTrackList.size());
		
		// success case
		RaceTrackDao raceTrack = raceTrackList.get(0);
		int trackId = raceTrack.getId();
		RaceTrackDao successTrack = raceTrackDBWrapper.findById(trackId);
		Assert.assertEquals(88, successTrack.getTrackType().intValue());
		Assert.assertEquals(20,  successTrack.getTerrainType().intValue());
		
		// fail case - not found
		this.testExceptionThrown(new ExceptionFacilitator<ResourceNotFoundException>() {
			@Override
			public void testForException() throws ResourceNotFoundException {
				raceTrackDBWrapper.findById(trackId + 1);				
			}
			
		}, ResourceNotFoundException.class);
	}
	
}
