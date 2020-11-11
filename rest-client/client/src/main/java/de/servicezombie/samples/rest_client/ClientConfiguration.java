package de.servicezombie.samples.rest_client;

public interface ClientConfiguration<T> {

	/**
	 * An interface used to describe a special endpoint, e.g. 
	 * JAX-RS Annotated or plain.
	 * @return instance, should never be null
	 */
	Class<T> getType();
	
	boolean exists(final String key);
	String load(final String key);
	String load(final String key, final String defaultValue);

}
