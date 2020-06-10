package com.djuwidja.horsegame.pathfinder.db.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import com.djuwidja.horsegame.pathfinder.db.repository.dao.TriggerLineDao;

public interface TriggerLineDaoRepository extends CrudRepository<TriggerLineDao, Integer> {
	public Iterable<TriggerLineDao> findAllByTrackTriggerIdIn(Collection<Integer> trackTriggerIds);
}
