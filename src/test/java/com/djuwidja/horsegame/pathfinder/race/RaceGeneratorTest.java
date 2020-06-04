package com.djuwidja.horsegame.pathfinder.race;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.djuwidja.horsegame.pathfinder.race.data.RaceData;
import com.djuwidja.horsegame.pathfinder.race.data.io.RaceDataWriter;
import com.djuwidja.networktype.compression.CompressionUtilsException;

@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(Lifecycle.PER_CLASS)
public class RaceGeneratorTest {
	@Autowired
	private RaceTrackRepository raceTrackRepo;
	@Autowired
	private StartPointRepository startPointRepo;
	@Autowired
	private TrackVectorRepository trackVectorRepo;

	@Autowired
	private RaceGenerator raceGenerator;
	@Autowired
	private RaceDataWriter raceDataWriter;

	@Test
	public void testContextLoad() {

	}

	@Test
	public void testStraightLineRaceWithMultipleHorses() throws ResourceNotFoundException, ConstructorParamException,
			InvalidLaneIdException, CompressionUtilsException {
		int numHorse = 12;

		int startPointSetId = 1;
		int trackId = createStraightLineTrack(startPointSetId);
		Map<Integer, RaceHorse> raceHorseMap = new HashMap<>();
		for (int i = 0; i < numHorse; i++) {
			RaceHorse raceHorse = new RaceHorse(i, i + 1, 1, 1.0d, 1.0d, 0.1d, 0.1d);

			raceHorseMap.put(raceHorse.getLaneId(), raceHorse);
		}
		RaceData raceData = raceGenerator.generateRace(trackId, startPointSetId, raceHorseMap);

		Assert.assertTrue(raceData.getTotalRaceTime() >= 10d);
		Assert.assertEquals(numHorse, raceData.getHorsePathDataMap().size());
	}

	@Test
	public void testModelledRaceWithMultipleHorses() throws ResourceNotFoundException, ConstructorParamException,
			InvalidLaneIdException, IOException, CompressionUtilsException {
		int trackId = createModelledTrack();

		int numHorses = 1;
		int startPointSetId = 1;
		Map<Integer, RaceHorse> raceHorseMap = new HashMap<>();
		List<Integer> expectedRankList = new ArrayList<>();
		for (int i = 0; i < numHorses; i++) {
			expectedRankList.add(i + 1);
		}
		Collections.shuffle(expectedRankList);

		double forwardSpdMax = 25d;
		double angularSpdMax = 25d;
		double forwardAcc = 4d;
		double angularAcc = 4d;

		for (int i = 0; i < numHorses; i++) {
			int laneId = i;
			int rank = expectedRankList.get(i);
			RaceHorse raceHorse = new RaceHorse(i, laneId, rank, forwardSpdMax, angularSpdMax, forwardAcc, angularAcc);
			raceHorseMap.put(laneId, raceHorse);
		}

		RaceData raceData = raceGenerator.generateRace(trackId, startPointSetId, raceHorseMap);
		raceDataWriter.generateIdAndWriteToFile(raceData);
		System.out.println(String.format("total race time = %f", raceData.getTotalRaceTime()));
	}

	private int createStraightLineTrack(int startPointSetId) {
		raceTrackRepo.deleteAll();
		startPointRepo.deleteAll();
		trackVectorRepo.deleteAll();

		// create a straight line track from 0.0d to 10.0d with finishing lines at x = 9;
		RaceTrackDao raceTrackDao = raceTrackRepo.save(RaceTrackDao.create(1, 1, 9.0d, -10.0d, 9.0d, 10.0d, 5d, -5d, 1));
		int trackId = raceTrackDao.getId();
		trackVectorRepo.save(TrackVectorDao.create(trackId, 0.0d, 0.0d, 1));
		trackVectorRepo.save(TrackVectorDao.create(trackId, 10.0d, 0.0d, 2));
		for (int i = 0; i < 12; i++) {
			startPointRepo.save(StartPointDao.create(startPointSetId, i + 1, 0.0d, 0.0d, 1.0d, 0.0d));
		}

		return trackId;
	}

	private int createModelledTrack() {
		raceTrackRepo.deleteAll();
		startPointRepo.deleteAll();
		trackVectorRepo.deleteAll();

		String modelTrackJson = "{\"terrainType\":1,\"trackType\":1,\"finishLinePt1X\":0.0,\"finishLinePt1Z\":-34.0,\"finishLinePt2X\":0.0,\"finishLinePt2Z\":366.0,\"controlPtX\":200.0,\"controlPtZ\":180.0,\"finishLineActivation\":3,\"startPoints\":[{\"setId\":1,\"laneId\":0,\"x\":400.0,\"z\":38.75,\"vecX\":-1.0,\"vecZ\":0.0},{\"setId\":1,\"laneId\":1,\"x\":400.0,\"z\":36.25,\"vecX\":-1.0,\"vecZ\":0.0},{\"setId\":1,\"laneId\":2,\"x\":400.0,\"z\":33.75,\"vecX\":-1.0,\"vecZ\":0.0},{\"setId\":1,\"laneId\":3,\"x\":400.0,\"z\":31.25,\"vecX\":-1.0,\"vecZ\":0.0},{\"setId\":1,\"laneId\":4,\"x\":400.0,\"z\":28.75,\"vecX\":-1.0,\"vecZ\":0.0},{\"setId\":1,\"laneId\":5,\"x\":400.0,\"z\":26.25,\"vecX\":-1.0,\"vecZ\":0.0},{\"setId\":1,\"laneId\":6,\"x\":400.0,\"z\":23.75,\"vecX\":-1.0,\"vecZ\":0.0},{\"setId\":1,\"laneId\":7,\"x\":400.0,\"z\":21.25,\"vecX\":-1.0,\"vecZ\":0.0},{\"setId\":1,\"laneId\":8,\"x\":400.0,\"z\":18.75,\"vecX\":-1.0,\"vecZ\":0.0},{\"setId\":1,\"laneId\":9,\"x\":400.0,\"z\":16.25,\"vecX\":-1.0,\"vecZ\":0.0},{\"setId\":1,\"laneId\":10,\"x\":400.0,\"z\":13.75,\"vecX\":-1.0,\"vecZ\":0.0},{\"setId\":1,\"laneId\":11,\"x\":400.0,\"z\":11.25,\"vecX\":-1.0,\"vecZ\":0.0}],\"trackVectors\":[{\"trackId\":1,\"x\":430.0,\"z\":12.0,\"seq\":0},{\"trackId\":1,\"x\":-58.099998474121097,\"z\":12.0,\"seq\":1},{\"trackId\":1,\"x\":-84.30000305175781,\"z\":17.0,\"seq\":2},{\"trackId\":1,\"x\":-100.9000015258789,\"z\":21.700000762939454,\"seq\":3},{\"trackId\":1,\"x\":-123.0,\"z\":31.899999618530275,\"seq\":4},{\"trackId\":1,\"x\":-138.0,\"z\":40.29999923706055,\"seq\":5},{\"trackId\":1,\"x\":-158.0,\"z\":55.79999923706055,\"seq\":6},{\"trackId\":1,\"x\":-177.3000030517578,\"z\":77.0999984741211,\"seq\":7},{\"trackId\":1,\"x\":-189.0,\"z\":93.5999984741211,\"seq\":8},{\"trackId\":1,\"x\":-202.39999389648438,\"z\":120.5999984741211,\"seq\":9},{\"trackId\":1,\"x\":-213.3000030517578,\"z\":160.0,\"seq\":10},{\"trackId\":1,\"x\":-215.3000030517578,\"z\":188.8000030517578,\"seq\":11},{\"trackId\":1,\"x\":-213.1999969482422,\"z\":213.10000610351563,\"seq\":12},{\"trackId\":1,\"x\":-208.10000610351563,\"z\":233.60000610351563,\"seq\":13},{\"trackId\":1,\"x\":-194.39999389648438,\"z\":259.8999938964844,\"seq\":14},{\"trackId\":1,\"x\":-170.89999389648438,\"z\":290.70001220703127,\"seq\":15},{\"trackId\":1,\"x\":-148.1999969482422,\"z\":312.70001220703127,\"seq\":16},{\"trackId\":1,\"x\":-118.30000305175781,\"z\":332.8999938964844,\"seq\":17},{\"trackId\":1,\"x\":-80.9000015258789,\"z\":346.0,\"seq\":18},{\"trackId\":1,\"x\":433.6000061035156,\"z\":346.0,\"seq\":19},{\"trackId\":1,\"x\":468.79998779296877,\"z\":343.70001220703127,\"seq\":20},{\"trackId\":1,\"x\":498.29998779296877,\"z\":338.3999938964844,\"seq\":21},{\"trackId\":1,\"x\":520.5999755859375,\"z\":331.29998779296877,\"seq\":22},{\"trackId\":1,\"x\":549.2000122070313,\"z\":316.8999938964844,\"seq\":23},{\"trackId\":1,\"x\":574.7000122070313,\"z\":296.20001220703127,\"seq\":24},{\"trackId\":1,\"x\":591.4000244140625,\"z\":275.79998779296877,\"seq\":25},{\"trackId\":1,\"x\":605.2999877929688,\"z\":250.6999969482422,\"seq\":26},{\"trackId\":1,\"x\":616.0999755859375,\"z\":220.1999969482422,\"seq\":27},{\"trackId\":1,\"x\":620.0,\"z\":193.60000610351563,\"seq\":28},{\"trackId\":1,\"x\":620.0,\"z\":174.3000030517578,\"seq\":29},{\"trackId\":1,\"x\":617.2999877929688,\"z\":155.1999969482422,\"seq\":30},{\"trackId\":1,\"x\":612.0999755859375,\"z\":137.8000030517578,\"seq\":31},{\"trackId\":1,\"x\":603.5,\"z\":115.5999984741211,\"seq\":32},{\"trackId\":1,\"x\":591.0999755859375,\"z\":93.30000305175781,\"seq\":33},{\"trackId\":1,\"x\":574.0999755859375,\"z\":72.5,\"seq\":34},{\"trackId\":1,\"x\":550.4000244140625,\"z\":52.099998474121097,\"seq\":35},{\"trackId\":1,\"x\":532.2000122070313,\"z\":41.400001525878909,\"seq\":36},{\"trackId\":1,\"x\":501.20001220703127,\"z\":26.600000381469728,\"seq\":37},{\"trackId\":1,\"x\":473.6000061035156,\"z\":15.0,\"seq\":38}]}";
		JSONObject jsonObj = new JSONObject(modelTrackJson);

		RaceTrackDao raceTrackDao = RaceTrackDao.create(jsonObj.getInt("terrainType"), jsonObj.getInt("trackType"),
				jsonObj.getDouble("finishLinePt1X"), jsonObj.getDouble("finishLinePt1Z"),
				jsonObj.getDouble("finishLinePt2X"), jsonObj.getDouble("finishLinePt2Z"),
				jsonObj.getDouble("controlPtX"), jsonObj.getDouble("controlPtZ"),
				jsonObj.getInt("finishLineActivation"));
		raceTrackDao = raceTrackRepo.save(raceTrackDao);
		int trackId = raceTrackDao.getId();

		JSONArray trackVectorArr = jsonObj.getJSONArray("trackVectors");
		for (int i = 0; i < trackVectorArr.length(); i++) {
			JSONObject trackVectorObj = (JSONObject) trackVectorArr.get(i);
			TrackVectorDao trackVectorDao = TrackVectorDao.create(trackId, trackVectorObj.getDouble("x"),
					trackVectorObj.getDouble("z"), trackVectorObj.getInt("seq"));
			trackVectorRepo.save(trackVectorDao);
		}

		JSONArray startPointArr = jsonObj.getJSONArray("startPoints");
		for (int i = 0; i < startPointArr.length(); i++) {
			JSONObject startPointObj = (JSONObject) startPointArr.get(i);
			StartPointDao startPointDao = StartPointDao.create(startPointObj.getInt("setId"),
					startPointObj.getInt("laneId"), startPointObj.getDouble("x"), startPointObj.getDouble("z"),
					startPointObj.getDouble("vecX"), startPointObj.getDouble("vecZ"));
			startPointRepo.save(startPointDao);
		}

		return trackId;
	}
}
