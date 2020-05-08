package com.djuwidja.horsegame.pathfinder.race;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.djuwidja.horsegame.pathfinder.db.RaceTrackDBWrapper;
import com.djuwidja.horsegame.pathfinder.db.ResourceNotFoundException;
import com.djuwidja.horsegame.pathfinder.db.StartPointDBWrapper;
import com.djuwidja.horsegame.pathfinder.db.TrackVectorDBWrapper;
import com.djuwidja.horsegame.pathfinder.db.repository.dao.RaceTrackDao;
import com.djuwidja.horsegame.pathfinder.db.repository.dao.StartPointDao;
import com.djuwidja.horsegame.pathfinder.db.repository.dao.TrackVectorDao;
import com.djuwidja.horsegame.pathfinder.meta.InvalidLaneIdException;
import com.djuwidja.horsegame.pathfinder.meta.RaceHorse;
import com.djuwidja.horsegame.pathfinder.meta.RaceTrack;
import com.djuwidja.horsegame.pathfinder.meta.factory.RaceTrackFactory;
import com.djuwidja.horsegame.pathfinder.meta.path.vectorpath.ConstructorParamException;
import com.djuwidja.horsegame.pathfinder.race.ai.RaceAI;
import com.djuwidja.horsegame.pathfinder.race.data.RaceData;

@Component
public class RaceGenerator {
	@Autowired private RaceTrackDBWrapper raceTrackDBWrapper;
	@Autowired private TrackVectorDBWrapper trackVectorDBWrapper;
	@Autowired private StartPointDBWrapper startPointDBWrapper;
		
	@Value("${com.djuwidja.horsegame.pathfinder.race-fps:60}")
	private double fps; // the fps of the race.
	
	public RaceData generateRace(int raceTrackId, int startPointSetId, Map<Integer, RaceHorse> raceHorseLaneIdMap) throws ResourceNotFoundException, ConstructorParamException, InvalidLaneIdException {
		RaceTrackDao raceTrackDao = raceTrackDBWrapper.findById(raceTrackId);
		List<TrackVectorDao> trackVectorDaoList = trackVectorDBWrapper.findByTrackIdOrderBySeqAsc(raceTrackId);
		List<StartPointDao> startPointDaoList = startPointDBWrapper.findBySetIdOrderByLaneIdAsc(startPointSetId); 
		
		RaceTrack raceTrack = RaceTrackFactory.createFromDao(raceTrackDao, trackVectorDaoList, startPointDaoList);
		double timeInterval = 1d / this.fps;
		RaceAI raceAI = new RaceAI(raceTrack, raceHorseLaneIdMap);
		
		while (!raceAI.isRaceFinished()) {
			raceAI.update(timeInterval);
		}
		
		return raceAI.getRaceData();
	}
}
