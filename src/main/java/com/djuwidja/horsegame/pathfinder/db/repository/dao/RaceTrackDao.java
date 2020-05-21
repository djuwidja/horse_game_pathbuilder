package com.djuwidja.horsegame.pathfinder.db.repository.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;

@Entity(name="race_track")
public class RaceTrackDao {
	public static RaceTrackDao create(
			int terrainType, 
			int trackType, 
			double finishLinePt1X, 
			double finishLinePt1Z, 
			double finishLinePt2X, 
			double finishLinePt2Z,
			double controlPtX,
			double controlPtZ,
			int finishLineActivation) {
		RaceTrackDao dao = new RaceTrackDao();
		dao.terrainType = terrainType;
		dao.trackType = trackType;
		dao.finishLinePt1X = finishLinePt1X;
		dao.finishLinePt1Z = finishLinePt1Z;
		dao.finishLinePt2X = finishLinePt2X;
		dao.finishLinePt2Z = finishLinePt2Z;
		dao.controlPtX = controlPtX;
		dao.controlPtZ = controlPtZ;
		dao.finishLineActivation = finishLineActivation;
		
		return dao;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter
	private Integer Id;
	
	@Getter 
	private Integer terrainType;
	
	@Getter
	private Integer trackType;
	
	@Getter
	@Column(name="finish_line_pt1_x")
	private Double finishLinePt1X;
	
	@Getter
	@Column(name="finish_line_pt1_z")
	private Double finishLinePt1Z;
	
	@Getter
	@Column(name="finish_line_pt2_x")
	private Double finishLinePt2X;
	
	@Getter
	@Column(name="finish_line_pt2_z")
	private Double finishLinePt2Z;
	
	@Getter
	@Column(name="control_pt_x")
	private Double controlPtX;
	
	@Getter
	@Column(name="control_pt_z")
	private Double controlPtZ;
	
	@Getter
	private int finishLineActivation;
}
