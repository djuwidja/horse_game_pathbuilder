package com.djuwidja.horsegame.pathfinder.db.repository.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;

@Entity(name="track_trigger_line")
public class TriggerLineDao {
	public static TriggerLineDao create(int trackTriggerId, double pt1x, double pt1z, double pt2x, double pt2z) {
		TriggerLineDao dao = new TriggerLineDao();
		dao.trackTriggerId = trackTriggerId;
		dao.pt1x = pt1x;
		dao.pt1z = pt1z;
		dao.pt2x = pt2x;
		dao.pt2z = pt2z;
		return dao;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter
	public Integer id;
	
	@Getter
	public Integer trackTriggerId;
	
	@Getter
	@Column(name="pt1_x")
	public Double pt1x;
	
	@Getter
	@Column(name="pt1_z")
	public Double pt1z;
	
	@Getter
	@Column(name="pt2_x")
	public Double pt2x;
	
	@Getter
	@Column(name="pt2_z")
	public Double pt2z;
}
