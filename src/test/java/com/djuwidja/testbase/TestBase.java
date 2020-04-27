package com.djuwidja.testbase;

import org.junit.Assert;

public class TestBase {
	public <T extends Exception> void testExceptionThrown(ExceptionFacilitator<T> facilitator, Class<T> exceptionCls) {
		boolean isExceptionThrown = false;
		try {
			facilitator.testForException();
		}
		catch (final Exception e) {
			Assert.assertEquals(exceptionCls, e.getClass());			
			isExceptionThrown = true;
		}
		
		if (!isExceptionThrown) {
			Assert.fail(String.format("This exception %s was never thrown.", exceptionCls.getName()));
		}
	}
}
