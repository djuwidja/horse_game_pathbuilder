package com.djuwidja.horsegame.pathfinder.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.djuwidja.horsegame.pathfinder.repository.dao.RaceTrack;

@SpringBootTest
@ContextConfiguration(classes=TestConfig.class)
@TestInstance(Lifecycle.PER_CLASS)
public class RaceTrackRepositoryTest {
	@Autowired private RaceTrackRepository raceTrackRepo;
	
	@BeforeAll
	public void setup() {
		raceTrackRepo.deleteAll();
		
		RaceTrack newTrack = new RaceTrack();
		newTrack.setTrackType(88);
		newTrack.setTerrainType(20);
		raceTrackRepo.save(newTrack);
	}
	
	@Test
	public void testContextLoad() {
		
	}
	
	@Test
	public void TestRetrieveData() {
		List<RaceTrack> trackList = new ArrayList<>();
		Iterable<RaceTrack> iterable = raceTrackRepo.findAll();
		iterable.forEach(trackList::add);
		
		Assert.assertEquals(1, trackList.size());
		int trackId = trackList.get(0).getId();
		
		Optional<RaceTrack> qResult1 = raceTrackRepo.findById(trackId);
		Assert.assertNotEquals(Optional.empty(), qResult1);
		
		RaceTrack track = qResult1.get();
		Assert.assertEquals(88, track.getTrackType().intValue());
		Assert.assertEquals(20, track.getTerrainType().intValue());
		
		Optional<RaceTrack> raceTrack2 = raceTrackRepo.findById(trackId + 1);
		Assert.assertEquals(Optional.empty(), raceTrack2);
	}
}
