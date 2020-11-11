package de.servicezombie.samples.rest_client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * All values are in a single properties file.
 */
public class ClientConfigurationFactory {

	private final Properties properties = new Properties();
	private String prefix = "restclient";

	public ClientConfigurationFactory() {
		final String resourceName = System.getProperty(getClass().getName() + ".configuration", "endpoints.properties");
		
		final InputStream data = ClassLoader.getSystemResourceAsStream(resourceName);

		if (data != null) {
			try {
				properties.load(data);
			} catch (IOException e) {
				throw new IllegalArgumentException("unable to read configuration file " + resourceName, e);
			}
		} else {
			throw new IllegalArgumentException("cannot find configuration file in classpath " + resourceName);
		}

		this.prefix = System.getProperty(getClass().getName() + ".prefix", "restclient");
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public <T> ClientConfiguration<T> create(Class<T> specification) {
		final PropertiesFileClientConfiguration<T> result = new PropertiesFileClientConfiguration<>(
				properties, 
				specification,
				prefix);
		return result;
	}

}
