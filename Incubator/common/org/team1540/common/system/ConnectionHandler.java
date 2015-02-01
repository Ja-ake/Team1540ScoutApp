package org.team1540.common.system;

import org.team1540.common.core.bluetooth.Address;
import org.team1540.common.core.pattern.Callback;

public interface ConnectionHandler {
	public void setupToRecive(Address myAddress, Callback<ConnectedThread> connectingThreads);

	public void connectTo(Address a, Callback<ConnectedThread> result);
}
