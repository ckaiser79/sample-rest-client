package de.servicezombie.samples.rest_client;

import javax.ws.rs.client.WebTarget;

/**
 * Interface for specific creators based on JBOSS-REST-EASY, CDI, JNDI,
 * .properties files, system properties, ... or whatever.
 * 
 */
public interface ClientCreationFactory {
	
	/**
	 * Low level access to a request with endpoint set and preconfigured. 
 	 * 
 	 * <pre>
 	 * 	WebTarget target = createEndpoint(endpointDefinition, "/foo.json")
 	 *  target.queryParam("id", 1);
 	 *  target.path("dataset.json")
 	 *  Dataset result = target.request().get(Dataset.class);
 	 * </pre>
 	 * 
 	 * Would call http://host:port/application/foo.json, where the path may 
 	 * contain <code>{templateVariables}</code>
 	 * 
	 * @param endpointDefinition, use <code>endpointDefinition.</code> 
	 * @return a valid WebTarget
	 */
	WebTarget createWebTarget(final ClientConfiguration<?> configuration,
			final String path);
	
	/**
	 * @param <T> endpoint interface
	 * @param proxyType an A JAX-RS annotated class
	 * @return proxy instance, which does the remote calls
	 */
	<T> T createJaxRsClient(final ClientConfiguration<T> configuration);

}
