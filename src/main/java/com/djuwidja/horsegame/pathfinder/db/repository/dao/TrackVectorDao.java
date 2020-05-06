package com.djuwidja.horsegame.pathfinder.repository.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
public class TrackVectorDao {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter
	private Integer id;
	
	@Getter
	@Setter
	private Integer trackId;
	
	@Getter
	@Setter
	private Double x;
	
	@Getter
	@Setter
	private Double z;
	
	@Getter
	@Setter
	private Integer seq;
}
