package org.team1540.incubator.system;

import java.io.IOException;

import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

import org.team1540.common.core.bluetooth.Address;
import org.team1540.common.core.pattern.Callback;
import org.team1540.common.system.ConnectedThread;
import org.team1540.common.system.ConnectionHandler;

public class SystemConnectionHandler implements ConnectionHandler {

	@Override
	public void setupToRecive(final Address myAddress, final Callback<ConnectedThread> connectingThreads) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					final String connURL = "btspp://localhost:"
							+ myAddress.uuid.toString() + ";name="
							+ myAddress.serviceName;
					final StreamConnectionNotifier scn = (StreamConnectionNotifier) Connector.open(connURL);
					while (true) {
						StreamConnection conn;
						LocalDevice.getLocalDevice().setDiscoverable(DiscoveryAgent.GIAC);
						conn = scn.acceptAndOpen();
						final RemoteDevice rd = RemoteDevice.getRemoteDevice(conn);
						rd.authenticate();
						final ConnectedThread connection = new SystemConnectedThread(conn);
						connectingThreads.callback(connection);
					}
				} catch (final IOException e) {
					throw new RuntimeException(e);
				}
			}
		});
	}

	@Override
	public void connectTo(final Address a, final Callback<ConnectedThread> result) {
		throw new UnsupportedOperationException();
	}

}
