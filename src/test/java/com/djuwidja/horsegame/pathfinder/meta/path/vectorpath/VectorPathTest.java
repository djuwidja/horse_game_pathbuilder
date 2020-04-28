package com.djuwidja.horsegame.pathfinder.meta.path.vectorpath;

import org.junit.jupiter.api.Test;

import com.djuwidja.horsegame.pathfinder.math.Arithmetic;
import com.djuwidja.horsegame.pathfinder.math.Vector2D;
import com.djuwidja.testbase.ExceptionFacilitator;
import com.djuwidja.testbase.TestBase;

import java.awt.geom.Point2D;
import java.util.Random;

import org.junit.Assert;

public class VectorPathTest extends TestBase {
	@Test
	public void testInitialization() throws ConstructorParamException {
		Random rand = new Random();
		
		//Failed case - not enough points to form a path
		this.testExceptionThrown(new ExceptionFacilitator<ConstructorParamException>() {
			@Override
			public void testForException() throws ConstructorParamException {
				Point2D[] failPointList = generatePointList(rand, 1);
				new VectorPath(failPointList);
			}	
		}, ConstructorParamException.class);
		
		//Success case
		int numPoints = 10;
		Point2D[] successPointList = generatePointList(rand, numPoints);
		VectorPath curve = new VectorPath(successPointList);
		VectorPathSect[] vectorSectList = curve.getVectorList();
		
		Assert.assertEquals(numPoints - 1, vectorSectList.length);		
		for (int i = 1; i < numPoints; i++) {
			Point2D startPt = successPointList[i - 1];
			Point2D endPt = successPointList[i];
			
			VectorPathSect sect = vectorSectList[i - 1];
			Assert.assertEquals(startPt, sect.getStartPt());
			Assert.assertEquals(endPt, sect.getEndPt());
			
			Vector2D sectVec = sect.getVector();
			
			double vecX = endPt.getX() - startPt.getX();
			double vecY = endPt.getY() - startPt.getY();
			
			double mag = Math.sqrt(Arithmetic.square(vecX) + Arithmetic.square(vecY));
			
			Assert.assertEquals(vecX / mag, sectVec.getX(), 0.00000001d);
			Assert.assertEquals(vecY / mag, sectVec.getY(), 0.00000001d);		
		}	
	}
	
	private Point2D[] generatePointList(Random rand, int pointListLen) {
		Point2D[] pointList = new Point2D[pointListLen];
		
		for (int i = 0; i < pointListLen; i++) {
			Point2D pt = new Point2D.Double(rand.nextDouble(), rand.nextDouble());
			pointList[i] = pt;
		}
		
		return pointList;
	}
}
