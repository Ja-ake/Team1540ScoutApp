package edu.team1540.egg.util.coms.bluetooth.bridge;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class ConnectedThread extends Thread {
	public final BluetoothSocket socket;
	private final BufferedWriter bufWriter;
	private final BufferedReader bufReader;
	private boolean stopped = false;
	public final Queue<String> values = new ConcurrentLinkedQueue<String>();

	public ConnectedThread(final BluetoothSocket socket) {
		this.socket = socket;
		try {
			bufReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bufWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			start();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void run() {
		String in;
		// Keep listening to the InputStream until an exception occurs
		while (true) {
			try {
				in = bufReader.readLine();
				values.add(in);
			} catch (final IOException e) {
				stopped = true;
				break;
			}
		}
	}

	/* Call this from the main activity to send data to the remote device */
	public void write(final String toWrite) throws IOException {
		if (socket.isConnected()) {
			Log.i("NEST_EGG", "Writing:" + toWrite);
			bufWriter.write(toWrite + "\n");
			bufWriter.flush();
			Log.i("NEST_EGG", "Writen:" + toWrite);
		} else {
			Log.w("NEST_EGG", "Can't write:" + toWrite);
		}
	}

	/* Call this from the main activity to shutdown the connection */
	public void cancel() {
		try {
			socket.close();
		} catch (final IOException e) {
			stopped = true;
		}
	}

	public boolean isDone() {
		return stopped;
	}
}
