package de.servicezombie.samples.rest_client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Map;

import javax.ws.rs.NotFoundException;

import org.junit.Before;
import org.junit.Test;

import de.servicezombie.samples.xkcd_transfer.XkcdComInfoEndpoint;
import de.servicezombie.samples.xkcd_transfer.XkcdComicInfo;

public class RestClientFascadeTest {
	// A default configurationLocator will lookup in a pre-defined property file.
	// Alternative Lookup: JNDI Name. Check if on classpath
	private RestClientFascade fascade1;
	private RestClientFascade fascade2;

	private XkcdComInfoEndpoint endpoint1;
	private XkcdComInfoEndpoint endpoint2;

	@Before
	public void configure() {
		fascade1 = new RestClientFascade();
		fascade2 = new RestClientFascade();

		clearCaches();

		endpoint1 = null;
		endpoint2 = null;
	}

	//@Test
	public void testProxyCaching() {

		endpoint1 = fascade1.createClient(XkcdComInfoEndpoint.class);
		endpoint2 = fascade2.createClient(XkcdComInfoEndpoint.class);
		assertSame(endpoint1, endpoint2);

		clearCaches();
		endpoint2 = fascade1.createClient(XkcdComInfoEndpoint.class);

		assertNotSame(endpoint1, endpoint2);

	}

	@Test
	public void testToMapUtility() {
		Date date = new Date();
		final Map<String, Object> params = RestClientFascade.toMap(
				"comicId", 11,
				"fooBar", "xcd",
				"timestamp", date);

		assertEquals(3, params.size());
		assertSame(date, params.get("timestamp"));
		assertEquals("xcd", params.get("fooBar"));
		assertEquals(11, params.get("comicId"));
	}

	/**
	 * in this case the client knows more about the uri to call, but not on which
	 * server it is
	 */
	//@Test
	public void testCreateUri() throws URISyntaxException {
		URI uri;

		uri = fascade1.createURI(XkcdComInfoEndpoint.class, "/info.0.json");
		assertEquals("http://xkcd.com/info.0.json", uri.toString());

		final Map<String, Object> params = RestClientFascade.toMap("comicId", 11);
		uri = fascade1.createURI(XkcdComInfoEndpoint.class, "/{comicId}/info.0.json", params);
		assertEquals("http://xkcd.com/11/info.0.json", uri.toString());

		uri = fascade1.createURI(XkcdComInfoEndpoint.class, "/\\{xxx\\}/info.0.json", params);
		assertEquals("http://xkcd.com/{xxx}/info.0.json", uri.toString());
		
		// not sure how the uri build, I use reacts on this
		try {
			uri = fascade1.createURI(XkcdComInfoEndpoint.class, "/{xxx}/info.0.json", params);
			assertEquals("http://xkcd.com/{xxx}/info.0.json", uri.toString());
			fail("Unknown Parameter accepted: {xxx}");
		}
		catch(IllegalArgumentException e) {
			// OK
		}
	}

	/**
	 * Mocked data from src/test/resources can be returned in this TC currently
	 */
	//@Test
	public void testCreateClient() {

		endpoint1 = fascade1.createClient(XkcdComInfoEndpoint.class);

		final XkcdComicInfo info1 = endpoint1.info();
		assertEquals(6, info1.getDay());
		assertEquals(11, info1.getDay());
		assertEquals("Ballot Tracker Tracker", info1.getSafe_title());
		
		final XkcdComicInfo info2 = endpoint1.info(11);
		assertEquals(6, info2.getDay());
		assertEquals(11, info2.getDay());
		assertEquals("Ballot Tracker Tracker", info2.getSafe_title());
		
		
		try {
			endpoint1.info(-1); // force a 404
		} catch (NotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	private void clearCaches() {
		// FIXME Cannot clean caches when there is not implementation yet :-)
	}
	
}
