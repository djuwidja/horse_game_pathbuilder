package com.djuwidja.horsegame.pathfinder.db.repository;

import org.springframework.data.repository.CrudRepository;

import com.djuwidja.horsegame.pathfinder.db.repository.dao.TriggerDao;

public interface TriggerDaoRepository extends CrudRepository<TriggerDao, Integer> {
	public Iterable<TriggerDao> findByTrackId(Integer trackId);
}
