package org.team1540.incubator.system;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.bluetooth.RemoteDevice;
import javax.microedition.io.StreamConnection;

import org.team1540.common.core.bluetooth.Address;
import org.team1540.common.core.pattern.Callback;
import org.team1540.common.core.pattern.Tuple;
import org.team1540.common.system.ConnectedThread;

public class SystemConnectedThread implements ConnectedThread {

	private final BufferedWriter bufWriter;
	private final Address address;
	private Callback<Tuple<Address, String>> messageCallback = null;

	public SystemConnectedThread(final StreamConnection socket) throws IOException {
		final RemoteDevice device = RemoteDevice.getRemoteDevice(socket);
		final BufferedReader bufReader = new BufferedReader(new InputStreamReader(socket.openInputStream()));
		bufWriter = new BufferedWriter(new OutputStreamWriter(socket.openOutputStream()));
		address = new Address(device.getBluetoothAddress());
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						final String message = bufReader.readLine();
						if (messageCallback != null) {
							messageCallback.callback(new Tuple<Address, String>(address, message));
						}
					} catch (final IOException e) {
						break;
					}
				}
			}
		});
	}

	@Override
	public Address getTarget() {
		return address;
	}

	@Override
	public void setMessageCallback(final Callback<Tuple<Address, String>> messageCallback) {
		this.messageCallback = messageCallback;
	}

	@Override
	public void sendMessage(final String message, final Callback<Tuple<Boolean, String>> callback) {
		try {
			bufWriter.write(message);
			callback.callback(new Tuple<Boolean, String>(true, message));
		} catch (final IOException e) {
			callback.callback(new Tuple<Boolean, String>(false, message));
		}
	}
}
