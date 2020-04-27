package com.djuwidja.horsegame.pathfinder.math;

public class Line2DException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String Y_CANNOT_BE_DETERMINED = "Y cannot be determined on a X Plane.";
	private static final String X_CANNOT_BE_DETERMINED = "X cannot be determined on a Y Plane.";
	private static final String LINES_ARE_PARALLEL_TO_EACH_OTHER = "Lines are parallel to each other.";
	private static final String LINE_IS_NOT_X_PLANE = "Line is not x plane.";
	private static final String LINE_IS_NOT_Y_PLANE = "Line is not y plane.";
	
	public static final Line2DException createYCanontBeDeterminedException() {
		return new Line2DException(Y_CANNOT_BE_DETERMINED);
	}
	
	public static final Line2DException createXCanontBeDeterminedException() {
		return new Line2DException(X_CANNOT_BE_DETERMINED);
	}
	
	public static final Line2DException createLinesAreParallelException() {
		return new Line2DException(LINES_ARE_PARALLEL_TO_EACH_OTHER);
	}
	
	public static final Line2DException createLineIsNotXPlaneException() {
		return new Line2DException(LINE_IS_NOT_X_PLANE);
	}
	
	public static final Line2DException createLineIsNotYPlaneException() {
		return new Line2DException(LINE_IS_NOT_Y_PLANE);
	}
	
	protected Line2DException(String errorMsg) {
		super(errorMsg);
	}
}
