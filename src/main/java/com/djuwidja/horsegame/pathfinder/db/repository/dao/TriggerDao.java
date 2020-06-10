package com.djuwidja.horsegame.pathfinder.db.repository.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;

@Entity(name="track_trigger")
public class TriggerDao {
	public static TriggerDao create(int trackId, int type) {
		TriggerDao dao = new TriggerDao();
		dao.trackId = trackId;
		dao.type = type;
		return dao;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter
	public Integer id;
	
	@Getter
	public Integer trackId;
	
	@Getter
	public Integer type;
}
