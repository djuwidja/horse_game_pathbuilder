package com.djuwidja.horsegame.pathfinder.db.repository.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity(name="race_track")
public class RaceTrackDao {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter
	private Integer Id;
	
	@Getter 
	@Setter
	private Integer terrainType;
	
	@Getter
	@Setter
	private Integer trackType;
}
