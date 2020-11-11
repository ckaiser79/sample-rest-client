package de.servicezombie.samples.rest_client;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.UriBuilder;

import de.servicezombie.samples.xkcd_transfer.XkcdComInfoEndpoint;

public class RestClientFascade {

	private static final String SLASH = "/";
	private final static Map<Class<?>, Object> PROXY_CLIENT_CACHE = new HashMap<>();

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

		final String configurationClass = System.getProperty("rest.default.configurationClass");
		final String creationStrategy = System.getProperty("rest.default.creationStrategy");

		try {

			if (creationStrategy == null) {
				instanceCreationStrategy = new RestEasyJaxRsCreationStrategy();
			} else {
				instanceCreationStrategy = newInstance(creationStrategy);
			}

			if (configurationClass == null) {
				clientConfiguration = new RestEasyJaxRsCreationStrategy();
			} else {
				clientConfiguration = newInstance(configurationClass);
			}

		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException
				| ClassNotFoundException e) {
			throw new IllegalStateException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private <T> T newInstance(final String type) throws InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException {
		final Class<?> forName = Class.forName(type);
		return (T) forName.getDeclaredConstructor().newInstance();
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

	@SuppressWarnings("unchecked")
	public <T> T createClient(Class<? extends T> proxyType) {
		if (PROXY_CLIENT_CACHE.containsKey(proxyType))
			return (T) PROXY_CLIENT_CACHE.get(proxyType);

		T client = instanceCreationStrategy.createClient(proxyType);
		PROXY_CLIENT_CACHE.put(proxyType, client);
		return client;
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

		String endpointPrefix = clientConfiguration.load("endpoint", proxyType);

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

		if (result.toString().matches("\\{[a-zA-Z0-9_]\\}")) {
			throw new IllegalArgumentException("uri contains unmatched parameters: " + result);
		}

		return result;
	}

	// for testing
	static void clearCachedProxies() {
		PROXY_CLIENT_CACHE.clear();
	}
}
