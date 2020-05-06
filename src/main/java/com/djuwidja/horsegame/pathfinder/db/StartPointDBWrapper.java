package com.djuwidja.horsegame.pathfinder.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.djuwidja.horsegame.pathfinder.db.repository.StartPointRepository;
import com.djuwidja.horsegame.pathfinder.db.repository.dao.StartPointDao;

@Component
public class StartPointDBWrapper extends DBWrapper<StartPointDao> {
	@Autowired private StartPointRepository startPointRepo;
	
	public StartPointDao findById(int id) throws ResourceNotFoundException {
		Optional<StartPointDao> result = startPointRepo.findById(id);
		return verifySingleResult(id, result, StartPointDao.class);
	}
	
	public List<StartPointDao> findAll() {
		Iterable<StartPointDao> result = startPointRepo.findAll();
		return extractListResult(result);
	}
	
	public List<StartPointDao> findBySetIdOrderByLaneIdAsc(int setId) {
		Iterable<StartPointDao> result = startPointRepo.findBySetIdOrderByLaneIdAsc(setId);
		return extractListResult(result);
	}
}
