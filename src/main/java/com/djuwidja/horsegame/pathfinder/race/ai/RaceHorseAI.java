package com.djuwidja.horsegame.pathfinder.race.ai;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.djuwidja.horsegame.pathfinder.math.Vector2D;
import com.djuwidja.horsegame.pathfinder.math.physics2d.CollisionResult;
import com.djuwidja.horsegame.pathfinder.math.physics2d.Line2DCollision;
import com.djuwidja.horsegame.pathfinder.meta.RaceHorse;
import com.djuwidja.horsegame.pathfinder.meta.RaceTrack;
import com.djuwidja.horsegame.pathfinder.meta.StartPoint;
import com.djuwidja.horsegame.pathfinder.meta.curve.TangentVector;
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
	private Vector2D compensationVec;
	private double curSpd;
	private double curDistanceToEdge;
	
	private double inFrameNorAcc;
	private double inFrameAngAcc;
	
	public RaceHorseAI(RaceHorse raceHorse, RaceTrack raceTrack, StartPoint startPoint) {
		this.raceHorse = raceHorse;
		this.raceTrack = raceTrack;
		this.position = startPoint.getStartPos();
		this.moveVec = startPoint.getStartVec();
		this.moveVec.normalize();
		this.normalVec = moveVec.normal();
		this.curDistanceToEdge = 0f;
		
		this.state = HorseAIState.MAINTAIN_SPEED;
		this.normalFwdSpd = raceHorse.getFwdSpdMax();
		this.normalAngSpd = raceHorse.getAngSpdMax();
		this.curFinishLineActivation = 0;
		this.isFinished = false;
		this.moveVecWithSpd = new Vector2D();
		this.compensationVec = new Vector2D();
		this.positionDataList = new ArrayList<RaceHorsePathData>();
		//capture the first frame
		this.recordPositionData();
		
		this.inFrameNorAcc = 0f;
		this.inFrameAngAcc = 0f;
		this.raceTime = 0f;
	}

	@Override
	public void update(double timeDelta) {
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
			checkIsFinished(timeDelta);
		}	
		updatePosition(this.moveVecWithSpd);
		recordPositionData();
	}

	private void checkIsFinished(double timeDelta) {
		CollisionResult collisonResult = Line2DCollision.checkCollision(this.raceTrack.getFinishLine(), this.position, this.moveVec, this.curSpd, timeDelta);
		if (collisonResult.isHasCollide()) {
			this.curFinishLineActivation++;
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
		
	private void updatePosition(Vector2D moveVecWithSpd) {				
		this.position.setLocation(this.position.getX() + moveVecWithSpd.getX() + compensationVec.getX(), 
				  this.position.getY() + moveVecWithSpd.getY() + compensationVec.getY());
	}
	
	private void recordPositionData() {		
		RaceHorsePathData data = new RaceHorsePathData(this.position.getX(), this.position.getY(), this.moveVec.getX(), this.moveVec.getY(), this.curSpd);
		this.positionDataList.add(data);
	}
	
	private void computeMaintainSpeed(double timeDelta) {
		try {
			TangentVector tangent = raceTrack.getGuidingVector(this.position, this.normalVec);
			this.moveVec = tangent.getVector();
			this.normalVec = this.moveVec.normal();
			
			if (curDistanceToEdge == 0f) {
				this.curDistanceToEdge = tangent.getTimeOfImpact();
			}
			else 
			{
				double distanceToEdgeDiff = tangent.getTimeOfImpact() - this.curDistanceToEdge;
				this.compensationVec.set(this.normalVec.getX() * distanceToEdgeDiff, this.normalVec.getY() * distanceToEdgeDiff);
			}
			
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
