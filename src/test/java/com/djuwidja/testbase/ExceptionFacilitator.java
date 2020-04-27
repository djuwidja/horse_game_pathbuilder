package com.djuwidja.testbase;

public interface ExceptionFacilitator<T extends Exception>  {
	public void testForException() throws T;
}
