package org.team1540.incubator;

import org.team1540.common.core.bluetooth.Address;
import org.team1540.common.core.bluetooth.ConnectedThreadHandler;
import org.team1540.common.core.schema.SchemaTransmitionHandler;
import org.team1540.common.core.schema.impl.StandSchema;
import org.team1540.common.core.schema.impl.TestSchema;
import org.team1540.common.system.ConnectionHandler;
import org.team1540.incubator.handler.StandHandler;
import org.team1540.incubator.handler.TestHandler;

public class HandlerManager {
	SchemaTransmitionHandler schemaHandler = new SchemaTransmitionHandler();

	public HandlerManager() {
		schemaHandler.register(TestSchema.class, new TestHandler());
		schemaHandler.register(StandSchema.class, new StandHandler());
	}

	public void register(final ConnectionHandler h, final Address a) {
		h.connectTo(a, new ConnectedThreadHandler(schemaHandler));
	}
}
