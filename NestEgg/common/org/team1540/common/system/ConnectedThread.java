package org.team1540.common.system;

import org.team1540.common.core.bluetooth.Address;
import org.team1540.common.core.pattern.Callback;
import org.team1540.common.core.pattern.Tuple;

public interface ConnectedThread {
	public Address getTarget();

	public void setMessageCallback(Callback<Tuple<Address, String>> messageCallback);

	public void sendMessage(String message, Callback<Tuple<Boolean, String>> callback);
}
