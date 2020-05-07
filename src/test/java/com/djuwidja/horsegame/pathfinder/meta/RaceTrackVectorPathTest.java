package com.djuwidja.horsegame.pathfinder.meta;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.djuwidja.horsegame.pathfinder.math.Vector2D;
import com.djuwidja.horsegame.pathfinder.meta.path.vectorpath.ConstructorParamException;
import com.djuwidja.testbase.ExceptionFacilitator;
import com.djuwidja.testbase.TestBase;

public class RaceTrackVectorPathTest extends TestBase {
	@Test
	public void testInitialization() throws ConstructorParamException {
		// success case
		Random rand = new Random();
		Point2D finishLinePtA = new Point2D.Double(rand.nextDouble(), rand.nextDouble());
		Point2D finishLinePtB = new Point2D.Double(rand.nextDouble(), rand.nextDouble());
		int finishLineActivation = 1 + rand.nextInt(9);
		Point2D[] pointList = generatePointList(rand, 10);
		Map<Integer, StartPoint> startPointMap = generateStartPointMap(rand, 5);
		new RaceTrackVectorPath(TerrainType.GRASS, finishLinePtA, finishLinePtB, finishLineActivation, pointList, startPointMap);
		
		// fail case - no point list
		this.testExceptionThrown(new ExceptionFacilitator<ConstructorParamException>() {

			@Override
			public void testForException() throws ConstructorParamException {
				Point2D[] failPointList = generatePointList(rand, 0);
				new RaceTrackVectorPath(TerrainType.GRASS, finishLinePtA, finishLinePtB, finishLineActivation, failPointList, startPointMap);			
			}
		}, ConstructorParamException.class);
		
		// fail case - no start points
		this.testExceptionThrown(new ExceptionFacilitator<ConstructorParamException>() {

			@Override
			public void testForException() throws ConstructorParamException {
				Map<Integer, StartPoint> failStartPointMap = generateStartPointMap(rand, 0);
				new RaceTrackVectorPath(TerrainType.GRASS, finishLinePtA, finishLinePtB, finishLineActivation, pointList, failStartPointMap);			
			}
		}, ConstructorParamException.class);
	}
	
	@Test
	public void testGetStartPoint() throws ConstructorParamException, InvalidLaneIdException {
		Random rand = new Random();
		int numPoint = 1 + rand.nextInt(9);
		
		Point2D finishLinePtA = new Point2D.Double(rand.nextDouble(), rand.nextDouble());
		Point2D finishLinePtB = new Point2D.Double(rand.nextDouble(), rand.nextDouble());
		int finishLineActivation = 1 + rand.nextInt(9);
		Point2D[] pointList = generatePointList(rand, 10);
		Map<Integer, StartPoint> startPointMap = generateStartPointMap(rand, numPoint);
		RaceTrackVectorPath track = new RaceTrackVectorPath(TerrainType.GRASS, finishLinePtA, finishLinePtB, finishLineActivation, pointList, startPointMap);
		
		for (int i = 0; i < numPoint; i++) {
			StartPoint expected = startPointMap.get(i);
			StartPoint pt = track.getStartPoint(i);
			Assert.assertEquals(expected.getStartPos().getX(), pt.getStartPos().getX(), 0.000000001);
			Assert.assertEquals(expected.getStartPos().getY(), pt.getStartPos().getY(), 0.000000001);
			Assert.assertEquals(expected.getStartVec().getX(), pt.getStartVec().getX(), 0.000000001);
			Assert.assertEquals(expected.getStartVec().getY(), pt.getStartVec().getY(), 0.000000001);
		}
		
		// fail case - lane not found
		this.testExceptionThrown(new ExceptionFacilitator<InvalidLaneIdException>() {
			@Override
			public void testForException() throws InvalidLaneIdException {
				track.getStartPoint(22);				
			}
			
		}, InvalidLaneIdException.class);
		
	}
	
	private Point2D[] generatePointList(Random rand, int size) {
		Point2D[] ptList = new Point2D[size];
		
		for (int i = 0; i < size; i++) {
			Point2D pt = new Point2D.Double(rand.nextDouble(), rand.nextDouble());
			ptList[i] = pt;
		}
		
		return ptList;
	}
	
	private Map<Integer, StartPoint> generateStartPointMap(Random rand, int size){
		Map<Integer, StartPoint> startPtMap = new HashMap<>();
		for (int i = 0; i < size; i++) {
			Point2D pt = new Point2D.Double(rand.nextDouble(), rand.nextDouble());
			Vector2D vec = new Vector2D(rand.nextDouble(), rand.nextDouble());
			vec.normalize();
			StartPoint startPt = new StartPoint(pt, vec);
			startPtMap.put(i, startPt);
		}
		
		return startPtMap;
	}
}
