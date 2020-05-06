package com.djuwidja.horsegame.pathfinder.repository;

import org.springframework.data.repository.CrudRepository;

import com.djuwidja.horsegame.pathfinder.repository.dao.TrackVector;

public interface TrackVectorRepository extends CrudRepository<TrackVector, Integer> {
	public Iterable<TrackVector> findByTrackIdOrderBySeqAsc(Integer trackId);
}
