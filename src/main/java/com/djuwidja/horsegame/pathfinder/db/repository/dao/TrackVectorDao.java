package com.djuwidja.horsegame.pathfinder.db.repository.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;

@Entity(name="track_vector")
public class TrackVectorDao {
	public static TrackVectorDao create(int trackId, double x, double z, int seq) {
		TrackVectorDao dao = new TrackVectorDao();
		dao.trackId = trackId;
		dao.x = x;
		dao.z = z;
		dao.seq = seq;
		return dao;
	}
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter
	private Integer id;
	
	@Getter
	private Integer trackId;
	
	@Getter
	private Double x;
	
	@Getter
	private Double z;
	
	@Getter
	private Integer seq;
}
