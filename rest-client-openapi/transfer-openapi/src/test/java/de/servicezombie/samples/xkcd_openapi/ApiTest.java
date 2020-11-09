package de.servicezombie.samples.xkcd_openapi;

import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class ApiTest {

	@Test
	public void testForInterfaces() {
		Class<?>[] types = new Class<?>[] {
				DefaultApi.class,
				Info0JsonApi.class
		};

		for (Class<?> type : types) {
			assertTrue(type + " is no interface", type.isInterface());
		}
	}

	@Test
	public void testForModels() {
		
		new XkcdComicInfo();
		
	}
	
}
