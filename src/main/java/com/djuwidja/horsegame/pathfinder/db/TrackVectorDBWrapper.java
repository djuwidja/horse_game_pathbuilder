package com.djuwidja.horsegame.pathfinder.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.djuwidja.horsegame.pathfinder.db.repository.TrackVectorRepository;
import com.djuwidja.horsegame.pathfinder.db.repository.dao.TrackVectorDao;

@Component
public class TrackVectorDBWrapper extends DBWrapper<TrackVectorDao> {
	@Autowired private TrackVectorRepository trackVectorRepo;
	
	public TrackVectorDao findById(int id) throws ResourceNotFoundException {
		Optional<TrackVectorDao> result = trackVectorRepo.findById(id);
		return verifySingleResult(id, result, TrackVectorDao.class);
	}
	
	public List<TrackVectorDao> findAll(){
		Iterable<TrackVectorDao> iterable = trackVectorRepo.findAll();
		return extractListResult(iterable);
	}
	
	public List<TrackVectorDao> findByTrackIdOrderBySeqAsc(int trackId){
		Iterable<TrackVectorDao> iterable = trackVectorRepo.findByTrackIdOrderBySeqAsc(trackId);
		return extractListResult(iterable);
	}
}
