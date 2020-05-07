package com.djuwidja.horsegame.pathfinder.db.repository.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;

@Entity(name="start_point")
public class StartPointDao {
	public static StartPointDao create(int setId, int laneId, double x, double z, double vecX, double vecZ) {
		StartPointDao dao = new StartPointDao();
		dao.setId = setId;
		dao.laneId = laneId;
		dao.x = x;
		dao.z = z;
		dao.vecX = vecX;
		dao.vecZ = vecZ;
		return dao;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter
	private Integer Id;
	
	@Getter
	private Integer setId;
	
	@Getter
	private Integer laneId;
	
	@Getter
	private Double x;
	
	@Getter
	private Double z;
	
	@Getter
	@Column(name="vec_x")
	private Double vecX;
	
	@Getter
	@Column(name="vec_z")
	private Double vecZ;
}
