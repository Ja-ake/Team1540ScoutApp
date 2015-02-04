package org.team1540.common.core.schema;

import java.util.HashMap;
import java.util.Map;

import org.team1540.common.core.bluetooth.Address;
import org.team1540.common.core.pattern.Callback;
import org.team1540.common.core.pattern.Tuple;

public class SchemaTransmitionHandler implements Callback<Tuple<Address, String>> {
	public final Map<Class<? extends Schema>, Callback<Schema>> handlers = new HashMap<Class<? extends Schema>, Callback<Schema>>();

	@Override
	public void callback(final Tuple<Address, String> value) {
		final Schema deserialized = Schema.deserilizeSchema(value.b);
		deserialized.from = value.a;
		handlers.get(deserialized.getClass()).callback(deserialized);
	}

	public <T extends Schema> void register(final Class<T> classType, final Callback<T> callback) {
		handlers.put(classType, new Callback<Schema>() {
			@Override
			public void callback(final Schema value) {
				callback(classType.cast(value));
			}
		});
	}
}
