package de.servicezombie.samples.rest_client;

/**
 * Interface for specific creators based on JBOSS-REST-EASY, CDI, JNDI,
 * .properties files, system properties, ... or whatever.
 * 
 */
public interface InstanceCreationStrategy {

	<T> T createClient(Class<? extends T> proxyType);

}
