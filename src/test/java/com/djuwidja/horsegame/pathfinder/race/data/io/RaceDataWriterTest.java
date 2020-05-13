package com.djuwidja.horsegame.pathfinder.race.data.io;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.djuwidja.horsegame.pathfinder.TestConfig;
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
import com.djuwidja.horsegame.pathfinder.race.RaceGenerator;
import com.djuwidja.horsegame.pathfinder.race.data.RaceData;
import com.djuwidja.networktype.compression.CompressionUtilsException;

@SpringBootTest
@ContextConfiguration(classes=TestConfig.class)
@TestInstance(Lifecycle.PER_CLASS)
public class RaceDataWriterTest {
	@Autowired private RaceTrackRepository raceTrackRepo;
	@Autowired private StartPointRepository startPointRepo;
	@Autowired private TrackVectorRepository trackVectorRepo;
	
	@Autowired private RaceGenerator raceGenerator;
	@Autowired private RaceDataWriter raceDataWriter;
	
	@Value("${com.djuwidja.horsegame.pathfinder.race-data-dir:./raceData}")
	private String raceDataDir;
	
	@Value("${com.djuwidja.horsegame.pathfinder.race-data-ext:rac}")
	private String raceDataExt;
	
	@Test
	public void testSerialization() throws ResourceNotFoundException, ConstructorParamException, InvalidLaneIdException {
		RaceData raceData = createRaceData();
		raceDataWriter.serialize(raceData);
	}
	
	@Test
	public void testWriteFile() throws ResourceNotFoundException, ConstructorParamException, InvalidLaneIdException, CompressionUtilsException, IOException {
		RaceData raceData = createRaceData();
		String filename = raceDataWriter.generateIdAndWriteToFile(raceData);
		
		File file = new File(raceDataDir + "/" + filename + "." + raceDataExt);
		Assert.assertTrue(file.exists() && !file.isDirectory());
	}

	private RaceData createRaceData()
			throws ResourceNotFoundException, ConstructorParamException, InvalidLaneIdException {
		int startPointSetId = 1;
		int trackId = createStraightLineTrack(startPointSetId);	
		Map<Integer, RaceHorse> raceHorseMap = new HashMap<>();
		for (int i = 0; i < 1; i++) {
			RaceHorse raceHorse = new RaceHorse(i, i + 1, 1, 1.0d, 1.0d, 1d, 1d);
			
			raceHorseMap.put(raceHorse.getLaneId(), raceHorse);
		}
		RaceData raceData = raceGenerator.generateRace(trackId, startPointSetId, raceHorseMap);
		return raceData;
	}
	
	private int createStraightLineTrack(int startPointSetId) {
		raceTrackRepo.deleteAll();
		startPointRepo.deleteAll();
		trackVectorRepo.deleteAll();
		
		//create a straight line track from 0.0d to 10.0d with finishing lines at x = 9;
		RaceTrackDao raceTrackDao = raceTrackRepo.save(RaceTrackDao.create(1, 1, 0.9d, -10.0d, 0.9d, 10.0d, 1));
		int trackId = raceTrackDao.getId();
		trackVectorRepo.save(TrackVectorDao.create(trackId, 0.0d, 0.0d, 1));
		trackVectorRepo.save(TrackVectorDao.create(trackId, 1.0d, 0.0d, 2));
		for (int i = 0; i < 12; i++) {
			startPointRepo.save(StartPointDao.create(startPointSetId, i + 1, 0.0d, 0.0d, 1.0d, 0.0d));
		}
		
		return trackId;		
	}
}
