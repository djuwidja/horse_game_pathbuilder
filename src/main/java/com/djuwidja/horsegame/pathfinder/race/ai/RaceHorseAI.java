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
	@Getter Vector2D normalVec;
	@Getter boolean isFinished;
	@Getter private List<RaceHorsePathData> positionDataList;
	@Getter private double raceTime;
	
	private double normalFwdSpd;
	private double normalAngSpd;
	private HorseAIState state;
	
	private int curFinishLineActivation;
	private Vector2D moveVecWithSpd;
	private double curSpd;
	
	private double inFrameNorAcc;
	private double inFrameAngAcc;
	
	public RaceHorseAI(RaceHorse raceHorse, RaceTrack raceTrack, StartPoint startPoint) {
		this.raceHorse = raceHorse;
		this.raceTrack = raceTrack;
		this.position = startPoint.getStartPos();
		this.moveVec = startPoint.getStartVec();
		this.moveVec.normalize();
		this.normalVec = moveVec.normal();
		
		this.state = HorseAIState.MAINTAIN_SPEED;
		this.normalFwdSpd = raceHorse.getFwdSpdMax();
		this.normalAngSpd = raceHorse.getAngSpdMax();
		this.curFinishLineActivation = 0;
		this.isFinished = false;
		this.moveVecWithSpd = new Vector2D();
		this.positionDataList = new ArrayList<RaceHorsePathData>();
		//capture the first frame
		this.recordPositionData();
		
		this.inFrameNorAcc = 0f;
		this.inFrameAngAcc = 0f;
		this.raceTime = 0f;
	}

	@Override
	public void update(double timeDelta) {
		FinishLineActivationResult timeOfActivationBefore = null;
		FinishLineActivationResult timeOfActivationAfter = null;
		if (!this.isFinished) {
			this.raceTime += timeDelta;
			
			computeInFrameVar(timeDelta);
			
			switch (state) {
			case MAINTAIN_SPEED:
				computeMaintainSpeed(timeDelta);
				break;
			default:
				break;
			}
			updateMoveVecWithSpd(timeDelta);
			timeOfActivationBefore = getTimeOfImpactBefore();
		}	
		updatePosition(this.moveVecWithSpd);
		timeOfActivationAfter = getTimeOfImpactAfter();
		
		if (!this.isFinished) {
			computeIsFinished(timeDelta, timeOfActivationBefore, timeOfActivationAfter);	
		}	
		recordPositionData();
	}

	private void computeIsFinished(double timeDelta, FinishLineActivationResult timeOfActivationBefore, FinishLineActivationResult timeOfActivationAfter) {
		if (timeOfActivationBefore.isSuccess() && timeOfActivationAfter.isSuccess()) {
			if (timeOfActivationBefore.getTimeOfImpact() >= 0f && timeOfActivationBefore.getTimeOfImpact() <= timeDelta) {
				curFinishLineActivation++;
			} else if (timeOfActivationBefore.getTimeOfImpact() >= 0f && timeOfActivationAfter.getTimeOfImpact() < 0f) {
				curFinishLineActivation++;
			}
			
			if (curFinishLineActivation >= this.raceTrack.getFinishLineActivation()) {
				this.isFinished = true;
			}
		}
	}
	
	private void computeInFrameVar(double timeDelta) {
		this.inFrameNorAcc = raceHorse.getFwdAcc() * timeDelta;
		this.inFrameAngAcc = raceHorse.getAngAcc() * timeDelta;
	}
	
	private void updateMoveVecWithSpd(double timeDelta) {
		this.moveVecWithSpd.set(this.moveVec.getX(), this.moveVec.getY());
		this.moveVecWithSpd.scalar(this.curSpd * timeDelta);
	}
	
	private FinishLineActivationResult getTimeOfImpactBefore() {
		try {
			double timeOfImpact = raceTrack.getFinishLine().getTimeOfImpact(this.getPosition(), this.moveVecWithSpd);
			return new FinishLineActivationResult(true, timeOfImpact);
		}
		catch (final Line2DException e) {
			return new FinishLineActivationResult(false, 0f);
		}
	}
	
	private FinishLineActivationResult getTimeOfImpactAfter() {
		try {
			double timeOfImpact = raceTrack.getFinishLine().getTimeOfImpact(this.getPosition(), this.moveVec);
			return new FinishLineActivationResult(true, timeOfImpact);
		}
		catch (final Line2DException e) {
			return new FinishLineActivationResult(false, 0f);
		}
	}
		
	private void updatePosition(Vector2D moveVecWithSpd) {				
		this.position.setLocation(this.position.getX() + moveVecWithSpd.getX(), 
				  this.position.getY() + moveVecWithSpd.getY());
	}
	
	private void recordPositionData() {		
		RaceHorsePathData data = new RaceHorsePathData(this.position.getX(), this.position.getY(), this.moveVec.getX(), this.moveVec.getY(), this.curSpd);
		this.positionDataList.add(data);
	}
	
	private void computeMaintainSpeed(double timeDelta) {
		try {
			this.moveVec = raceTrack.getGuidingVector(this.position, this.normalVec);
			this.normalVec = this.moveVec.normal();
			
			double normalSpd = 0d;
			double acc = 0d;
			if (Math.abs(moveVec.getX()) >= 0.80d) {
				normalSpd = this.normalFwdSpd;
				acc = this.inFrameNorAcc;
			} else {
				normalSpd = this.normalAngSpd;
				acc = this.inFrameAngAcc;
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
