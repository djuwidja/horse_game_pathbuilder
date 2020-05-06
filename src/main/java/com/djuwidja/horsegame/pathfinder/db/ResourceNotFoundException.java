package com.djuwidja.horsegame.pathfinder.db;

public class ResourceNotFoundException extends DBWrapperException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(String msg) {
		super(msg);
	}
}
