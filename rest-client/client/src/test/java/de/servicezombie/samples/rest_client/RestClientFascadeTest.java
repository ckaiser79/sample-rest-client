package de.servicezombie.samples.rest_client;

import static de.servicezombie.samples.rest_client.Utils.toMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.servicezombie.samples.xkcd_transfer.XkcdComInfoEndpoint;
import de.servicezombie.samples.xkcd_transfer.XkcdComicInfo;

public class RestClientFascadeTest {

	// A default configurationLocator will lookup in a pre-defined property file.
	// Alternative Lookup: JNDI Name. Check if on classpath
	private RestClientFascade testee;
	private XkcdComInfoEndpoint endpoint;

	@Before
	public void configure() {

		// configure the classes, used by the fascade. Maybe better configure the
		// fascade?
		System.setProperty(ClientConfigurationFactory.class.getName() + ".prefix", "rest");

		testee = new RestClientFascade();
	}

	@Test
	public void testCreateJaxRsClient() throws URISyntaxException {
		URI uri;
		final Map<String, Object> params = toMap("comicId", 11);

		uri = testee.createURI(XkcdComInfoEndpoint.class, "/info.0.json");
		assertEquals("http://xkcd.com:80/info.0.json", uri.toString());

		uri = testee.createURI(XkcdComInfoEndpoint.class, "/{comicId}/info.0.json", params);
		assertEquals("http://xkcd.com:80/11/info.0.json", uri.toString());

	}

	/**
	 * in this case the client knows more about the uri to call, but not on which
	 * server it is
	 */
	@Test
	public void testCreateUri() throws URISyntaxException {
		URI uri;
		final Map<String, Object> params = toMap("comicId", 11);

		uri = testee.createURI(XkcdComInfoEndpoint.class, "/info.0.json");
		assertEquals("http://xkcd.com:80/info.0.json", uri.toString());

		uri = testee.createURI(XkcdComInfoEndpoint.class, "/{comicId}/info.0.json", params);
		assertEquals("http://xkcd.com:80/11/info.0.json", uri.toString());

	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateUriWithUnknownParameters() throws URISyntaxException {

		// not sure how the uri build, I use reacts on this
		final Map<String, Object> params = toMap("comicId", 11);
		final URI uri = testee.createURI(XkcdComInfoEndpoint.class, "/{xxx}/info.0.json", params);

		assertEquals("http://xkcd.com:80/{xxx}/info.0.json", uri.toString());
		fail("Unknown Parameter accepted: {xxx}");

	}

	// ignore for now
	// @Test
	public void testCreateUriWithEscapedParameters() throws URISyntaxException {
		final Map<String, Object> params = toMap("comicId", 11);
		final URI uri = testee.createURI(XkcdComInfoEndpoint.class, "/\\{xxx\\}/info.0.json", params);
		assertEquals("http://xkcd.com:80/{xxx}/info.0.json", uri.toString());
	}

	@Test
	public void testWebTargetManual() throws JsonParseException, JsonMappingException, IOException {

		XkcdComicInfo value;

		final WebTarget webTarget = testee.createWebTarget(
				XkcdComInfoEndpoint.class,
				"/info.{version}.json");
		
		// doing everything manual
		Invocation invocation = webTarget
				.resolveTemplates(toMap("version", "0"))
				.request()
				.buildGet();
		Response response = invocation.invoke();
		assertEquals(200, response.getStatus());

		final ObjectMapper om = new ObjectMapper();
		om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		InputStream inputStream = (InputStream) response.getEntity();
		value = om.readValue(inputStream, XkcdComicInfo.class);
		assertNotNull(value);

	}

	@Test
	public void testWebTargetObject() throws JsonParseException, JsonMappingException, IOException {

		XkcdComicInfo value;

		final WebTarget webTarget = testee.createWebTarget(
				XkcdComInfoEndpoint.class,
				"/info.{version}.json");

		// let jax-rs do the deserialisation
		value = webTarget
				.resolveTemplates(toMap("version", "0"))
				.request()
				.buildGet()
				.invoke(XkcdComicInfo.class);
		assertNotNull(value);
	}

	/**
	 * Mocked data from src/test/resources can be returned in this TC currently, but
	 * current
	 * works with real data.
	 */
	@Test
	public void testCreateClient() {

		endpoint = testee.createClient(XkcdComInfoEndpoint.class);

		final XkcdComicInfo info1 = endpoint.info();

		assertTrue(info1.getDay() > 0);
		assertTrue(info1.getMonth() > 0);
		assertTrue(info1.getYear() >= 1987);

		assertNotNull(info1.getSafe_title());

		final XkcdComicInfo info2 = endpoint.info(11);
		assertTrue(info2.getDay() > 0);
		assertTrue(info2.getMonth() > 0);
		assertTrue(info2.getYear() >= 1987);

		try {
			endpoint.info(-1); // force a 404
		} catch (NotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
