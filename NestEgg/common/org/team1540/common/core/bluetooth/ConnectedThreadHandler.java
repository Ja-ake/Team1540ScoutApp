package org.team1540.common.core.bluetooth;

import org.team1540.common.core.pattern.Callback;
import org.team1540.common.core.pattern.Tuple;
import org.team1540.common.system.ConnectedThread;

public class ConnectedThreadHandler implements Callback<ConnectedThread> {
	private final Callback<Tuple<Address, String>> messageHandler;

	public ConnectedThreadHandler(final Callback<Tuple<Address, String>> messageHandler) {
		this.messageHandler = messageHandler;
	}

	@Override
	public void callback(final ConnectedThread value) {
		value.setMessageCallback(messageHandler);
	}
}
