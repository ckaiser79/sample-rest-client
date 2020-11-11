package de.servicezombie.samples.rest_client;

import java.util.HashMap;
import java.util.Map;

public class SimpleClientConfiguration<T> implements ClientConfiguration<T> {

	private final Class<T> type;

	private final Map<String, String> data = new HashMap<>();

	public SimpleClientConfiguration(final Class<T> type) {
		this.type = type;
	}

	@Override
	public Class<T> getType() {
		return type;
	}

	@Override
	public boolean exists(String key) {
		return data.containsKey(key);
	}

	@Override
	public String load(String key) {
		final String result = data.get(key);
		if (result == null)
			throw new IllegalArgumentException("key does not exist: " + key);
		return result;
	}

	@Override
	public String load(String key, String defaultValue) {
		final String result = data.get(key);
		if (result == null && defaultValue == null)
			throw new IllegalArgumentException("key does not exist: " + key);
		return result == null ? defaultValue : result;
	}

	public void putData(String key, String value) {
		data.put(key, value);
	}

}
