package edu.team1540.egg.system;

import java.io.IOException;

import org.team1540.common.core.bluetooth.Address;
import org.team1540.common.core.pattern.Callback;
import org.team1540.common.system.ConnectedThread;
import org.team1540.common.system.ConnectionHandler;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class SystemConnectionHandler implements ConnectionHandler {

	@Override
	public void setupToRecive(final Address myAddress, final Callback<ConnectedThread> connectingThreads) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void connectTo(final Address a, final Callback<ConnectedThread> result) {
		try {
			final BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
//			if (adapter == null) {
//				return;
//			}
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
									Log.i("eggo","swag");
									return;
								} catch (final IOException connectException) {
									Log.i("eggo",connectException.toString());
									Thread.sleep(10000);
								}
							}
						} catch (final InterruptedException closeException) {
						}
						socket.close();
					} catch (final IOException closeException) {
					}
				}
			}).start();
		} catch (final IOException e1) {
			result.callback(null);
		}
	}
}
