package de.servicezombie.samples.rest_client;

import java.util.Properties;

public class PropertiesFileClientConfiguration<T> implements ClientConfiguration<T> {

	private final Properties configuration;
	private final Class<T> definition;
	
	private final String prefix;

	public PropertiesFileClientConfiguration(final Properties p, final Class<T> definition, final String prefix) {
		configuration = p;
		this.definition = definition;
		this.prefix = prefix;
	}

	@Override
	public String load(final String key) {
		String result = null;

		result = get(key);

		if (result == null)
			throw new IllegalArgumentException("No key defined for type " + definition + "." + key);

		return result;
	}

	private String get(final String key) {

		String result = configuration.getProperty(prefix + ".type." + definition.getName() + "." + key);

		if (result == null) {
			final String group = getGroup();
			if (group != null) {
				result = configuration.getProperty(prefix + ".group." + group + "." + key);
			}
		}

		if (result == null) {
			result = configuration.getProperty(prefix + ".default." + key);
		}

		return result;
	}

	private String getGroup() {
		return configuration.getProperty(prefix + ".type." + definition.getName() + ".group");
	}

	@Override
	public boolean exists(String key) {
		return get(key) != null;
	}

	@Override
	public String load(final String key, final String defaultValue) {
		final String result = get(key);

		if (result == null && defaultValue == null) {
			throw new IllegalArgumentException("Null return values is not allowed");
		}
		return result == null ? defaultValue : result;
	}

	@Override
	public Class<T> getType() {
		return definition;
	}

}
