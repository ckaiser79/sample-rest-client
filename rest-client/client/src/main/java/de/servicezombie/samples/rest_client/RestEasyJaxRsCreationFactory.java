package de.servicezombie.samples.rest_client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient43Engine;

public class RestEasyJaxRsCreationFactory implements ClientCreationFactory {

	// should this be a local variable or a member?
	private ApacheHttpClient43Engine engine;

	public RestEasyJaxRsCreationFactory() {
		engine = new ApacheHttpClient43Engine();
		engine.setFollowRedirects(true);
	}

	private String loadApplication(final ClientConfiguration<?> configuration) {
		return configuration.load("application", "");
	}

	private String loadEndpoint(final ClientConfiguration<?> configuration) {
		return configuration.load("endpoint");
	}

	/**
	 * Create a web target accepting json messages.
	 * @param path path to append after the configured endpoint, null is treated 
	 * as an empty string. 
	 */
	@Override
	public WebTarget createWebTarget(final ClientConfiguration<?> configuration,
			final String path) {

		final String endpoint;
		if (path != null) {
			endpoint = loadEndpoint(configuration) +
					loadApplication(configuration) +
					path;
		}
		else {
			endpoint = loadEndpoint(configuration) +
					loadApplication(configuration);
		}
		
		final ResteasyClientBuilder restClientbuild = (ResteasyClientBuilder) ClientBuilder.newBuilder();
		final Client client = restClientbuild.httpEngine(engine)
				.register(FieldIgnoringJacksonProvider.class)
				.build();

		WebTarget target = client.target(endpoint);

		// can only consumer json, because no other providers are configured.
		return target;
	}

	@Override
	public <T> T createJaxRsClient(final ClientConfiguration<T> configuration) {

		final WebTarget target = createWebTarget(configuration, null);
		final ResteasyWebTarget rtarget = (ResteasyWebTarget) target;

		final Class<T> proxyTarget = configuration.getType();
		final T proxy = rtarget.proxy(proxyTarget);
		return proxy;
	}

}
