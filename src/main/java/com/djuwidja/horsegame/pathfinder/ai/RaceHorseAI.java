package com.djuwidja.horsegame.pathfinder.ai;

import java.awt.geom.Point2D;

import com.djuwidja.horsegame.pathfinder.math.Vector2D;
import com.djuwidja.horsegame.pathfinder.meta.RaceHorse;
import com.djuwidja.horsegame.pathfinder.meta.StartPoint;

import lombok.Getter;

public class RaceHorseAI implements AI {
	@Getter private RaceHorse raceHorse;
	@Getter Point2D position;
	@Getter Vector2D moveVec;
	@Getter Vector2D moveNor;
	
	public RaceHorseAI(RaceHorse raceHorse, StartPoint startPoint) {
		this.raceHorse = raceHorse;
		this.position = startPoint.getStartPos();
		this.moveVec = startPoint.getStartVec();
		this.moveNor = moveVec.normal();
	}

	@Override
	public void update(double timeDelta) {
		
	}
}
