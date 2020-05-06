package com.djuwidja.horsegame.pathfinder.repository;

import org.springframework.data.repository.CrudRepository;
import com.djuwidja.horsegame.pathfinder.repository.dao.RaceTrackDao;

public interface RaceTrackRepository extends CrudRepository<RaceTrackDao, Integer> {

}
