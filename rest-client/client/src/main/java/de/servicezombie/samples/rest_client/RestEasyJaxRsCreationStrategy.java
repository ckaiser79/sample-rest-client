package de.servicezombie.samples.rest_client;

import java.io.IOException;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient43Engine;

public class RestEasyJaxRsCreationStrategy implements InstanceCreationStrategy, ClientConfiguration {

	private final Properties configuration;
	private String group = "";
	private ApacheHttpClient43Engine engine;

	public RestEasyJaxRsCreationStrategy() {
		
		configuration =  new Properties();
		try {
			configuration.load(ClassLoader.getSystemResourceAsStream("endpoints.properties"));
		} catch (IOException e) {
			throw new IllegalArgumentException("No endpoints.properties can be found in classpath");
		}
		
		engine = new ApacheHttpClient43Engine();
		engine.setFollowRedirects(true);
		
	}

	private String loadApplication(Class<?> type) {
		return load("application", type);
	}
	
	private String loadEndpoint(Class<?> type) {
		return load("endpoint", type);
	}

	@Override
	public <T> T createClient(Class<? extends T> proxyType) {

		final String endpoint = loadEndpoint(proxyType) + loadApplication(proxyType);
		
		final ResteasyClientBuilder restClientbuild = (ResteasyClientBuilder)ClientBuilder.newBuilder();
		final Client client = restClientbuild.httpEngine(engine)
				.register(FieldIgnoringJacksonProvider.class)
				.build();
		
		final WebTarget target = client.target(endpoint);
		final ResteasyWebTarget rtarget = (ResteasyWebTarget) target;

		final T proxy = rtarget.proxy(proxyType);
		return proxy;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	@Override
	public String load(final String key, final Class<?> type) {
		String result = null;

		result = configuration.getProperty("rest.default." + key);

		if (result == null) {
			result = configuration.getProperty("rest.group." + group + "." + key);
		}

		if (result == null) {
			result = configuration.getProperty("rest.type." + type.getName() + "." + key);
		}

		if(result == null) throw new IllegalArgumentException("No key defined for type " + type +"." + key);
		
		return result;
	}
}
