package com.djuwidja.horsegame.pathfinder;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.djuwidja.horsegame.pathfinder.db.ResourceNotFoundException;
import com.djuwidja.horsegame.pathfinder.db.repository.RaceTrackRepository;
import com.djuwidja.horsegame.pathfinder.db.repository.StartPointRepository;
import com.djuwidja.horsegame.pathfinder.db.repository.TrackVectorRepository;
import com.djuwidja.horsegame.pathfinder.db.repository.dao.RaceTrackDao;
import com.djuwidja.horsegame.pathfinder.db.repository.dao.StartPointDao;
import com.djuwidja.horsegame.pathfinder.db.repository.dao.TrackVectorDao;
import com.djuwidja.horsegame.pathfinder.meta.InvalidLaneIdException;
import com.djuwidja.horsegame.pathfinder.meta.RaceHorse;
import com.djuwidja.horsegame.pathfinder.meta.path.vectorpath.ConstructorParamException;
import com.djuwidja.networktype.compression.CompressionUtils;
import com.djuwidja.networktype.compression.CompressionUtilsException;
import com.sun.istack.logging.Logger;

@SpringBootTest
@ContextConfiguration(classes=TestConfig.class)
@TestInstance(Lifecycle.PER_CLASS)
public class RaceGeneratorTest {
	@Autowired private RaceTrackRepository raceTrackRepo;
	@Autowired private StartPointRepository startPointRepo;
	@Autowired private TrackVectorRepository trackVectorRepo;
	
	@Autowired private RaceGenerator raceGenerator;
	@Autowired private CompressionUtils compressUtil;
	
	@Test
	public void testContextLoad() {
		
	}
	
	@Test
	public void testStraightLineRaceWithOneHorse() throws ResourceNotFoundException, ConstructorParamException, InvalidLaneIdException, CompressionUtilsException {
		int startPointSetId = 1;
		int trackId = createStraightLineTrack(startPointSetId);	
		Map<Integer, RaceHorse> raceHorseMap = new HashMap<>();
		for (int i = 0; i < 12; i++) {
			RaceHorse raceHorse = new RaceHorse(i, i + 1, 1, 1.0d, 1.0d, 0.1d, 0.1d);
			
			raceHorseMap.put(raceHorse.getLaneId(), raceHorse);
		}
		RaceData raceData = raceGenerator.generateRace(trackId, startPointSetId, raceHorseMap);
		
		Assert.assertTrue(raceData.getTotalRaceTime() >= 120d);
		Assert.assertEquals(12, raceData.getHorsePathDataMap().size());
		
		byte[] byteArray = raceData.serialize();
		byte[] compressedArray = compressUtil.compress(byteArray);	
		Logger.getLogger(this.getClass()).log(Level.INFO, String.format("raw=%d, compressed=%d", byteArray.length, compressedArray.length));
		
		byte[] decompressedArray = compressUtil.decompress(compressedArray);
		Assert.assertArrayEquals(byteArray, decompressedArray);
	}
	
	private int createStraightLineTrack(int startPointSetId) {
		raceTrackRepo.deleteAll();
		startPointRepo.deleteAll();
		trackVectorRepo.deleteAll();
		
		//create a straight line track from 0.0d to 10.0d with finishing lines at x = 9;
		RaceTrackDao raceTrackDao = raceTrackRepo.save(RaceTrackDao.create(1, 1, 120.0d, -10.0d, 120.0d, 10.0d, 1));
		int trackId = raceTrackDao.getId();
		trackVectorRepo.save(TrackVectorDao.create(trackId, 0.0d, 0.0d, 1));
		trackVectorRepo.save(TrackVectorDao.create(trackId, 125.0d, 0.0d, 2));
		for (int i = 0; i < 12; i++) {
			startPointRepo.save(StartPointDao.create(startPointSetId, i + 1, 0.0d, 0.0d, 1.0d, 0.0d));
		}
		
		return trackId;		
	}
}
