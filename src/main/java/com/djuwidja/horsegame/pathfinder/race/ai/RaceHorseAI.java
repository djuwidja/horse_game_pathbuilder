package com.djuwidja.horsegame.pathfinder.race.ai;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.djuwidja.horsegame.pathfinder.math.Line2DException;
import com.djuwidja.horsegame.pathfinder.math.Vector2D;
import com.djuwidja.horsegame.pathfinder.meta.RaceHorse;
import com.djuwidja.horsegame.pathfinder.meta.RaceTrack;
import com.djuwidja.horsegame.pathfinder.meta.StartPoint;
import com.djuwidja.horsegame.pathfinder.meta.curve.TrackSectionCurveException;
import com.djuwidja.horsegame.pathfinder.race.data.RaceHorsePathData;

import lombok.Getter;

public class RaceHorseAI implements AI {
	@Getter private RaceHorse raceHorse;
	@Getter private RaceTrack raceTrack;
	@Getter Point2D position;
	@Getter Vector2D moveVec;
	@Getter Vector2D moveNor;
	@Getter boolean isFinished;
	@Getter private List<RaceHorsePathData> positionDataList;
	
	private double normalFwdSpd;
	private double normalAngSpd;
	private HorseAIState state;
	
	private int curFinishLineActivation;
	private Vector2D moveVecWithSpd;
	private double curSpd;
	
	public RaceHorseAI(RaceHorse raceHorse, RaceTrack raceTrack, StartPoint startPoint) {
		this.raceHorse = raceHorse;
		this.raceTrack = raceTrack;
		this.position = startPoint.getStartPos();
		this.moveVec = startPoint.getStartVec();
		this.moveNor = moveVec.normal();
		
		this.state = HorseAIState.MAINTAIN_SPEED;
		this.normalFwdSpd = raceHorse.getFwdSpdMax() * 0.8d;
		this.normalAngSpd = raceHorse.getAngSpdMax() * 0.8d;
		this.curFinishLineActivation = 0;
		this.isFinished = false;
		this.moveVecWithSpd = new Vector2D();
		this.positionDataList = new ArrayList<RaceHorsePathData>();
	}

	@Override
	public void update(double timeDelta) {
		if (!this.isFinished) {
			switch (state) {
			case MAINTAIN_SPEED:
				computeMaintainSpeed();
				break;
			default:
				break;
			}
			updateMoveVecWithSpd(timeDelta);
			checkIsFinished(timeDelta);
		}	
		updatePosition(this.moveVecWithSpd);
		recordPositionData();
	}
	
	private void updateMoveVecWithSpd(double timeDelta) {
		this.moveVecWithSpd.set(this.moveVec.getX(), this.moveVec.getY());
		this.moveVecWithSpd.scalar(this.curSpd * timeDelta);
	}
	
	private void checkIsFinished(double timeDelta) {
		try {
			double timeOfImpact = raceTrack.getFinishLine().getTimeOfImpact(this.getPosition(), this.moveVecWithSpd);
			if (timeOfImpact <= timeDelta) {
				curFinishLineActivation++;
			}
			
			if (curFinishLineActivation >= this.raceTrack.getFinishLineActivation()) {
				this.isFinished = true;
			}
		}
		catch (final Line2DException e) {
			
		}
	}
		
	private void updatePosition(Vector2D moveVecWithSpd) {				
		this.position.setLocation(this.position.getX() + moveVecWithSpd.getX(), 
				  this.position.getY() + moveVecWithSpd.getY());
	}
	
	private void recordPositionData() {		
		RaceHorsePathData data = new RaceHorsePathData(this.position, this.moveVec, this.curSpd);
		this.positionDataList.add(data);
	}
		
	private void computeMaintainSpeed() {
		try {
			this.moveVec = raceTrack.getGuidingVector(this.position, this.moveNor);
			double normalSpd = 0d;
			double acc = 0d;
			if (Math.abs(moveVec.getX()) >= 0.80d) {
				normalSpd = this.normalFwdSpd;
				acc = raceHorse.getFwdAcc();
			} else {
				normalSpd = this.normalAngSpd;
				acc = raceHorse.getAngAcc();
			}
			
			if (this.curSpd < normalSpd) {
				this.curSpd += acc;
			} else {
				this.curSpd = normalSpd;
			}
		}
		catch (final TrackSectionCurveException e) {
			
		}
	}
}
