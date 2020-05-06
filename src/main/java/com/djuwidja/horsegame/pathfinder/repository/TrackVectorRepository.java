package com.djuwidja.horsegame.pathfinder.repository;

import org.springframework.data.repository.CrudRepository;

import com.djuwidja.horsegame.pathfinder.repository.dao.TrackVectorDao;

public interface TrackVectorRepository extends CrudRepository<TrackVectorDao, Integer> {
	public Iterable<TrackVectorDao> findByTrackIdOrderBySeqAsc(Integer trackId);
}
