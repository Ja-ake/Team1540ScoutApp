package edu.team1540.egg.util.coms.bluetooth.bridge;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class ConnecterThread extends Thread {
	private final BluetoothSocket mmSocket;
	private final BluetoothAdapter mBluetoothAdapter;
	private final SocketManager toManage;
	private final int attempts;

	public ConnecterThread(final BluetoothAdapter adapter, final BluetoothDevice device, final String uuid, final SocketManager s,int attempts) {
		BluetoothSocket tmp = null;
		try {
			tmp = device.createRfcommSocketToServiceRecord(UUID.fromString(uuid));
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		mmSocket = tmp;
		mBluetoothAdapter = adapter;
		toManage = s;
		this.attempts=attempts;
		this.start();
	}

	@Override
	public void run() {
		for (int x = 0; x < attempts; x++) {
			try {
				mBluetoothAdapter.cancelDiscovery();
				mmSocket.connect();
				toManage.manageSocket(mmSocket);
				Log.i("NEST_EGG", "Sucess connecting!");
				return;
			} catch (final IOException connectException) {
				final int sleepTime = (int) (Math.random() * 1000 + 500);
				Log.e("NEST_EGG", "Bad connect attempt:"+x+", retrying in a "+ sleepTime / 1000.0 + " seconds");
				try {
					Thread.sleep(sleepTime);
				} catch (final InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
		Log.e("NEST_EGG", "giving up and closing socket");
		try {
			mmSocket.close();
		} catch (final IOException closeException) {
		}

	}

	public void cancel() {
		try {
			mmSocket.close();
		} catch (final IOException e) {
		}
	}

	public static interface SocketManager {
		public void manageSocket(BluetoothSocket socket);
		public void failed();
	}
}
