package com.djuwidja.horsegame.pathfinder;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.djuwidja.horsegame.pathfinder.ai.RaceAI;
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

import lombok.Getter;

public class RaceGenerator {
	@Autowired private RaceTrackDBWrapper raceTrackDBWrapper;
	@Autowired private TrackVectorDBWrapper trackVectorDBWrapper;
	@Autowired private StartPointDBWrapper startPointDBWrapper;
	
	@Getter private Map<Integer, RaceHorse> raceHorseMap;
	@Getter private RaceTrack raceTrack;
	@Getter private double fps; // the fps of the race.
	
	@Getter private double raceTime; // time elapse of the race;
	private double timeInterval; // the time process of the race after each iteration.
	private RaceAI raceAI;
	
		
	public RaceGenerator(int raceTrackId, int startPointSetId, Map<Integer, RaceHorse> raceHorseMap, double fps) throws InvalidLaneIdException, ResourceNotFoundException, ConstructorParamException {	
		RaceTrackDao raceTrackDao = raceTrackDBWrapper.findById(raceTrackId);
		List<TrackVectorDao> trackVectorDaoList = trackVectorDBWrapper.findByTrackIdOrderBySeqAsc(raceTrackId);
		List<StartPointDao> startPointDaoList = startPointDBWrapper.findBySetIdOrderByLaneIdAsc(startPointSetId);
		
		this.raceTrack = RaceTrackFactory.createFromDao(raceTrackDao, trackVectorDaoList, startPointDaoList);
		this.raceHorseMap = raceHorseMap;
		this.fps = fps;
		
		this.timeInterval = 1d / fps;
		this.raceTime = 0d;
		this.raceAI = new RaceAI(raceTrack, raceHorseMap);
	}
	
	public void generateRace() {
		while (!this.raceAI.isRaceFinished()) {
			raceAI.update(timeInterval);
			raceTime += timeInterval;
		}
	}
}
