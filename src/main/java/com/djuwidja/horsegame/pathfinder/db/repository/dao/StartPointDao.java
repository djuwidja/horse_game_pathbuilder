package com.djuwidja.horsegame.pathfinder.db.repository.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity(name="start_point")
public class StartPointDao {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter
	private Integer Id;
	
	@Getter
	@Setter
	private Integer setId;
	
	@Getter
	@Setter
	private Integer laneId;
	
	@Getter
	@Setter
	private Double x;
	
	@Getter
	@Setter
	private Double z;
	
	@Getter
	@Setter
	@Column(name="vec_x")
	private Double vecX;
	
	@Getter
	@Setter
	@Column(name="vec_z")
	private Double vecZ;
}
