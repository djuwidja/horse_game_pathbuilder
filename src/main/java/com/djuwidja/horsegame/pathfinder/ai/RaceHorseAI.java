package com.djuwidja.horsegame.pathfinder.ai;

import java.awt.geom.Point2D;

import com.djuwidja.horsegame.pathfinder.math.Vector2D;
import com.djuwidja.horsegame.pathfinder.meta.RaceHorse;
import com.djuwidja.horsegame.pathfinder.meta.RaceTrack;
import com.djuwidja.horsegame.pathfinder.meta.StartPoint;
import com.djuwidja.horsegame.pathfinder.meta.curve.TrackSectionCurveException;

import lombok.Getter;

public class RaceHorseAI implements AI {
	@Getter private RaceHorse raceHorse;
	@Getter private RaceTrack raceTrack;
	@Getter Point2D position;
	@Getter Vector2D moveVec;
	@Getter Vector2D moveNor;
	
	private double normalFwdSpd;
	private double normalAngSpd;
	private double curSpd;
	private HorseAIState state;
	
	public RaceHorseAI(RaceHorse raceHorse, RaceTrack raceTrack, StartPoint startPoint) {
		this.raceHorse = raceHorse;
		this.raceTrack = raceTrack;
		this.position = startPoint.getStartPos();
		this.moveVec = startPoint.getStartVec();
		this.moveNor = moveVec.normal();
		
		this.state = HorseAIState.MAINTAIN_SPEED;
		this.normalFwdSpd = raceHorse.getFwdSpdMax() * 0.8d;
		this.normalAngSpd = raceHorse.getAngSpdMax() * 0.8d;
		this.curSpd = 0d;
	}

	@Override
	public void update(double timeDelta) {
		switch (state) {
		case MAINTAIN_SPEED:
			maintainSpeed(timeDelta);
			break;
		default:
			break;
		}
	}
		
	private void maintainSpeed(double timeDelta) {
		try {
			this.moveVec = raceTrack.getGuidingVector(this.position, this.moveNor);
			double normalSpd = 0d;
			double acc = 0d;
			if (Math.abs(moveVec.getX()) >= 0.80d) {
				normalSpd = this.normalFwdSpd * timeDelta;
				acc = raceHorse.getFwdAcc() * timeDelta;
			} else {
				normalSpd = this.normalAngSpd * timeDelta;
				acc = raceHorse.getAngAcc() * timeDelta;
			}
			
			if (curSpd < normalSpd) {
				curSpd += acc;
			} else {
				curSpd = normalSpd;
			}
			
			this.position.setLocation(this.position.getX() + this.moveVec.getX() * curSpd, 
									  this.position.getY() + this.moveVec.getY() * curSpd);
		}
		catch (final TrackSectionCurveException e) {
			
		}
	}
}
