package de.servicezombie.samples.rest_client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.UriBuilder;

import de.servicezombie.samples.xkcd_transfer.XkcdComInfoEndpoint;

public class RestClientFascade {

	private static final String SLASH = "/";
	private final InstanceCreationStrategy instanceCreationStrategy;
	private final ClientConfiguration clientConfiguration;

	/**
	 * CTOR for DI or Test environments
	 * 
	 * @param instanceCreationStrategy the strategy to use
	 */
	public RestClientFascade(
			final InstanceCreationStrategy instanceCreationStrategy,
			final ClientConfiguration clientConfiguration) {

		this.instanceCreationStrategy = instanceCreationStrategy;
		this.clientConfiguration = clientConfiguration;
	}

	/**
	 * find strategy using System.getProperty Parameters, as Log4J or JNDI does
	 */
	public RestClientFascade() {
		this.clientConfiguration = null;
		this.instanceCreationStrategy = null;
	}

	public static Map<String, Object> toMap(Object... mapKeyValuePairs) {
		Map<String, Object> result = new HashMap<String, Object>();

		if (mapKeyValuePairs.length % 2 == 1)
			throw new IllegalArgumentException("Must have even number of arguments");

		for (int i = 0; i < mapKeyValuePairs.length; i += 2) {
			final Object key = mapKeyValuePairs[i];

			if (!(key instanceof String))
				throw new IllegalArgumentException("key " + key + " at  position " + i + " must be of type string");

			final Object value = mapKeyValuePairs[i + 1];
			result.put(key.toString(), value);
		}

		return result;
	}

	public <T> T createClient(Class<? extends T> proxyType) {
		return instanceCreationStrategy.createClient(proxyType);
	}

	public URI createURI(
			final Class<XkcdComInfoEndpoint> proxyType,
			final String pathWithoutParameter) throws URISyntaxException {

		return createURI(proxyType, pathWithoutParameter, null);
	}

	public URI createURI(
			final Class<XkcdComInfoEndpoint> proxyType,
			final String pathWithParameter,
			final Map<String, Object> namedParameter) throws URISyntaxException {

		final String endpointPrefix = clientConfiguration.load("endpoint");

		// add missing / to avoid bugs
		final String spacer;
		if (endpointPrefix.endsWith(SLASH))
			spacer = "";
		else if (pathWithParameter.startsWith(SLASH))
			spacer = "";
		else
			spacer = SLASH;

		final UriBuilder builder = UriBuilder.fromUri(endpointPrefix + spacer + pathWithParameter);
		final URI result;

		if (namedParameter == null) {
			result = builder.build();
		} else {
			result = builder.buildFromMap(namedParameter);
		}

		if(result.toString().matches("\\{[a-zA-Z0-9_]\\}")) {
			throw new IllegalArgumentException("uri contains unmatched parameters: " + result); 
		}
		
		return result;
	}
}
