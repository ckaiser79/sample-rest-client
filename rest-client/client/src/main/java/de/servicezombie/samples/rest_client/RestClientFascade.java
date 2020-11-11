package de.servicezombie.samples.rest_client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import javax.ws.rs.client.WebTarget;

public class RestClientFascade {

	private final ClientCreationFactory instanceCreationFactory;
	private final ClientConfigurationFactory clientConfigurationFactory;

	/**
	 * find strategy using System.getProperty Parameters, as Log4J or JNDI does
	 */
	public RestClientFascade() {
		instanceCreationFactory = new RestEasyJaxRsCreationFactory();
		clientConfigurationFactory = new ClientConfigurationFactory();
	}

	public <T> T createClient(Class<T> proxyType) {

		final ClientConfiguration<T> configuration = clientConfigurationFactory.create(proxyType);
		final T client = instanceCreationFactory.createJaxRsClient(configuration);

		return client;
	}

	public <T> WebTarget createWebTarget(
			final Class<T> definition,
			final String path) {

		final ClientConfiguration<T> configuration = clientConfigurationFactory.create(definition);
		final WebTarget webTarget = instanceCreationFactory.createWebTarget(configuration, path);
		return webTarget;

	}

	public URI createURI(
			final Class<?> definition,
			final String pathWithoutParameter) throws URISyntaxException {

		return createURI(definition, pathWithoutParameter, null);
	}

	/**
	 * Create a URI ready to use
	 * 
	 * @param definition        wher endpoint and prefix should be taken from
	 * @param pathWithParameter a relatvive user defined path with optional
	 *                          <code>{pathParameterNames}</code>
	 * @param namedParameter    a map with values for the path parameters
	 * @return a valid uri with parsed parameters, never null
	 * @throws IllegalArgumentException, if there are unmatched parameters
	 * 
	 */
	public URI createURI(
			final Class<?> definition,
			final String pathWithParameter,
			final Map<String, Object> namedParameter) {

		final WebTarget webTarget = createWebTarget(definition, pathWithParameter);
		final URI result;

		if (namedParameter == null) {
			result = webTarget.getUri();
		} else {
			result = webTarget.resolveTemplates(namedParameter).getUri();
		}

		return result;
	}

}
