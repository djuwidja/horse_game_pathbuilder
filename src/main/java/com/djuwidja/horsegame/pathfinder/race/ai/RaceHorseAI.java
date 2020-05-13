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
	
	private double inFrameNorFwdSpd;
	private double inFrameNorAngSpd;
	private double inFrameNorAcc;
	private double inFrameAngAcc;
	
	public RaceHorseAI(RaceHorse raceHorse, RaceTrack raceTrack, StartPoint startPoint) {
		this.raceHorse = raceHorse;
		this.raceTrack = raceTrack;
		this.position = startPoint.getStartPos();
		this.moveVec = startPoint.getStartVec();
		this.moveNor = moveVec.normal();
		
		this.state = HorseAIState.MAINTAIN_SPEED;
		this.normalFwdSpd = raceHorse.getFwdSpdMax();
		this.normalAngSpd = raceHorse.getAngSpdMax();
		this.curFinishLineActivation = 0;
		this.isFinished = false;
		this.moveVecWithSpd = new Vector2D();
		this.positionDataList = new ArrayList<RaceHorsePathData>();
		
		this.inFrameNorFwdSpd = 0f;
		this.inFrameNorAngSpd = 0f;
		this.inFrameNorAcc = 0f;
		this.inFrameAngAcc = 0f;
	}

	@Override
	public void update(double timeDelta) {
		if (!this.isFinished) {
			computeInFrameVar(timeDelta);
			
			switch (state) {
			case MAINTAIN_SPEED:
				computeMaintainSpeed();
				break;
			default:
				break;
			}
			updateMoveVecWithSpd();
			checkIsFinished(timeDelta);
		}	
		updatePosition(this.moveVecWithSpd);
		recordPositionData();
	}
	
	private void computeInFrameVar(double timeDelta) {
		this.inFrameNorFwdSpd = this.normalFwdSpd * timeDelta;
		this.inFrameNorAngSpd = this.normalAngSpd * timeDelta;
		this.inFrameNorAcc = raceHorse.getFwdAcc() * timeDelta;
		this.inFrameAngAcc = raceHorse.getAngAcc() * timeDelta;
	}
	
	private void updateMoveVecWithSpd() {
		this.moveVecWithSpd.set(this.moveVec.getX(), this.moveVec.getY());
		this.moveVecWithSpd.scalar(this.curSpd);
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
		RaceHorsePathData data = new RaceHorsePathData(this.position.getX(), this.position.getY(), this.moveVec.getX(), this.moveVec.getY(), this.curSpd);
		this.positionDataList.add(data);
	}
		
	private void computeMaintainSpeed() {
		try {
			this.moveVec = raceTrack.getGuidingVector(this.position, this.moveNor);
			double normalSpd = 0d;
			double acc = 0d;
			if (Math.abs(moveVec.getX()) >= 0.80d) {
				normalSpd = this.inFrameNorFwdSpd;
				acc = this.inFrameNorAcc;
			} else {
				normalSpd = this.inFrameNorAngSpd;
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
