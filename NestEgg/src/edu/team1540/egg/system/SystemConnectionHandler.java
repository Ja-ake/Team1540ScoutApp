package edu.team1540.egg.system;

import java.io.IOException;

import org.team1540.common.core.bluetooth.Address;
import org.team1540.common.core.pattern.Callback;
import org.team1540.common.system.ConnectedThread;
import org.team1540.common.system.ConnectionHandler;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

public class SystemConnectionHandler implements ConnectionHandler {

	public void setupToRecive(Address myAddress, Callback<ConnectedThread> connectingThreads) {
		throw new UnsupportedOperationException();
	}

	public void connectTo(final Address a, final Callback<ConnectedThread> result) {
		try {
			final BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
			final BluetoothDevice device = adapter.getRemoteDevice(a.address);
			final BluetoothSocket socket = device.createRfcommSocketToServiceRecord(a.uuid);
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						try {
							for (int index = 0; index < 10; index++) {
								try {
									adapter.cancelDiscovery();
									socket.connect();
									result.callback(new SystemConnectedThread(a, socket));
									return;
								} catch (final IOException connectException) {
									Thread.sleep(10000);
								}
							}
						} catch (final InterruptedException closeException) {
						}
						socket.close();
					} catch (final IOException closeException) {
					}
				}
			});
		} catch (IOException e1) {
			result.callback(null);
		}
	}
}
