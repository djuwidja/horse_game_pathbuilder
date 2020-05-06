package com.djuwidja.horsegame.pathfinder.db.repository;

import org.springframework.data.repository.CrudRepository;

import com.djuwidja.horsegame.pathfinder.db.repository.dao.StartPointDao;

public interface StartPointRepository extends CrudRepository<StartPointDao, Integer>{
	public Iterable<StartPointDao> findBySetIdOrderByLaneIdAsc(Integer setId);
}
