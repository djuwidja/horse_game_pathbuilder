package com.djuwidja.horsegame.pathfinder.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DBWrapper<T> {
	protected T verifySingleResult(int id, Optional<T> result, Class<T> cls) throws ResourceNotFoundException {
		if (result.isEmpty()) {
			throw new ResourceNotFoundException(String.format("%s with id=%s does not exist.", cls.getName(), id));
		}
		
		return result.get();
	}
	
	protected List<T> extractListResult(Iterable<T> iterable) {
		List<T> result = new ArrayList<>();		
		iterable.forEach(result::add);
		
		return result;
	}
}
