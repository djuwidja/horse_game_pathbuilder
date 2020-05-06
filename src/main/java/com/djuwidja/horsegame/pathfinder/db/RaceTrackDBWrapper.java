package com.djuwidja.horsegame.pathfinder.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.djuwidja.horsegame.pathfinder.db.repository.RaceTrackRepository;
import com.djuwidja.horsegame.pathfinder.db.repository.dao.RaceTrackDao;

import java.util.List;
import java.util.Optional;

@Component
public class RaceTrackDBWrapper extends DBWrapper<RaceTrackDao> {
	@Autowired private RaceTrackRepository raceTrackRepo;
	
	public RaceTrackDao findById(int id) throws ResourceNotFoundException {
		Optional<RaceTrackDao> result = raceTrackRepo.findById(id);
		return verifySingleResult(id, result, RaceTrackDao.class);
	}
	
	public List<RaceTrackDao> findAll() {
		Iterable<RaceTrackDao> iterable = raceTrackRepo.findAll();
		return extractListResult(iterable);
	}
}
