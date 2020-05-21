package com.djuwidja.horsegame.pathfinder.meta.curve.vectorpath;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.djuwidja.horsegame.pathfinder.math.Vector2D;
import com.djuwidja.horsegame.pathfinder.meta.curve.TrackSectionCurveException;
import com.djuwidja.horsegame.pathfinder.meta.path.vectorpath.ConstructorParamException;

public class VectorPathCurveTest {
	@Test
	public void testInitialization() throws ConstructorParamException, TrackSectionCurveException {	
		ArrayList<Point2D> point2DListMutable = new ArrayList<>();
		point2DListMutable.add(new Point2D.Double(15d, -10d));
		point2DListMutable.add(new Point2D.Double(10d, -10d));
		point2DListMutable.add(new Point2D.Double(8d, -9d));
		point2DListMutable.add(new Point2D.Double(7d, -7d));
		point2DListMutable.add(new Point2D.Double(7d, -5d));
		point2DListMutable.add(new Point2D.Double(8d, -3d));
		point2DListMutable.add(new Point2D.Double(10d, -2d));
		point2DListMutable.add(new Point2D.Double(15d, -2d));
		
		Point2D[] pointList = point2DListMutable.toArray(new Point2D[point2DListMutable.size()]);
		Point2D controlPoint = new Point2D.Double(15d, -6d);
		
		VectorPathCurve curve = new VectorPathCurve(pointList, controlPoint);
		testTangentVector(curve, new Point2D.Double(10d, -20d), controlPoint, -1d, 0d);
		testTangentVector(curve, new Point2D.Double(8d, -10d), controlPoint, -2d / Math.sqrt(5d), 1d / Math.sqrt(5d));
		testTangentVector(curve, new Point2D.Double(7.5d, -8d), controlPoint, -1d / Math.sqrt(5d), 2d / Math.sqrt(5d));
		testTangentVector(curve, new Point2D.Double(20d, -6d), controlPoint, 0d, -1d);
		testTangentVector(curve, new Point2D.Double(7.5d, -4d), controlPoint, 1d /Math.sqrt(5d), 2d / Math.sqrt(5d));
		testTangentVector(curve, new Point2D.Double(8d, -2d), controlPoint, 2d / Math.sqrt(5d), 1d / Math.sqrt(5d));
		testTangentVector(curve, new Point2D.Double(10d, 20d), controlPoint, 1d, 0d);
	}
	
	private void testTangentVector(VectorPathCurve curve, Point2D pt, Point2D control, double expectedVecX, double expectedVecY) throws TrackSectionCurveException {
		testTangentVector(curve, pt, expectedVecX, expectedVecY);
	}
	
	@Test
	public void testRoundTrip() throws ConstructorParamException, TrackSectionCurveException {
		ArrayList<Point2D> point2DListMutable = new ArrayList<>();
		point2DListMutable.add(new Point2D.Double(429.8d, 38.2d));
		point2DListMutable.add(new Point2D.Double(-58.1d, 38.2d));
		point2DListMutable.add(new Point2D.Double(-194d, 178d));
		point2DListMutable.add(new Point2D.Double(-59d, 328d));
		point2DListMutable.add(new Point2D.Double(433d, 328d));
		point2DListMutable.add(new Point2D.Double(601d, 188d));
		
		Point2D controlPt = new Point2D.Double(200d, 180d);
		Point2D[] pointList = point2DListMutable.toArray(new Point2D[point2DListMutable.size()]);
		
		VectorPathCurve curve = new VectorPathCurve(pointList, controlPt);
		testTangentVector(curve, new Point2D.Double(-58.2d, 40d), -0.697034446d, 0.717037642d);
		testTangentVector(curve, new Point2D.Double(-58.2d, 38.2d), -0.697034446d, 0.717037642d);
		testTangentVector(curve, new Point2D.Double(-58.2d, 31.2d), -1, 0d);
		testTangentVector(curve, new Point2D.Double(-205.2d, 187d), 0.668964732d, 0.743294146d);
		testTangentVector(curve, new Point2D.Double(479.90951900490893d, -2.6463901925219737d), -1d, 0d);
		testTangentVector(curve, new Point2D.Double(486.15951900490893d, -2.6463901925219737d), -1d, 0d);
	}
	
	private void testTangentVector(VectorPathCurve curve, Point2D pt, double expectedVecX, double expectedVecY) throws TrackSectionCurveException {
		Vector2D vec = curve.getTangentVector(pt);
		Assert.assertEquals(expectedVecX, vec.getX(), 0.000000001d);
		Assert.assertEquals(expectedVecY, vec.getY(), 0.000000001d);
	}
	
}
