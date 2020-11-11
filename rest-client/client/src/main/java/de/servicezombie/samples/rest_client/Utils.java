package de.servicezombie.samples.rest_client;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

abstract class Utils {

	@SuppressWarnings("unchecked")
	public static <T> T newInstance(final String className) {

		try {
			final Class<?> forName = Class.forName(className);
			return (T) forName.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
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
}
