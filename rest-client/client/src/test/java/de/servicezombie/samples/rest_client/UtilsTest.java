package de.servicezombie.samples.rest_client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Date;
import java.util.Map;

import org.junit.Test;

public class UtilsTest {


	@Test
	public void testToMapUtility() {
		Date date = new Date();
		final Map<String, Object> params = Utils.toMap(
				"comicId", 11,
				"fooBar", "xcd",
				"timestamp", date);

		assertEquals(3, params.size());
		assertSame(date, params.get("timestamp"));
		assertEquals("xcd", params.get("fooBar"));
		assertEquals(11, params.get("comicId"));
	}

}
