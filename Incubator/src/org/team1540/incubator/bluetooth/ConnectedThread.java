package org.team1540.incubator.bluetooth;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.microedition.io.StreamConnection;

import org.team1540.incubator.bluetooth.BluetoothServer.NewMessageListener;

public class ConnectedThread extends Thread {
	private final StreamConnection socket;
	private final BufferedWriter bufWriter;
	private final BufferedReader bufReader;
	public final Queue<String> messageHistory = new ConcurrentLinkedQueue<String>();
	public final Queue<NewMessageListener> listeners = new ConcurrentLinkedQueue<NewMessageListener>();
	private boolean connected = false;

	public ConnectedThread(StreamConnection socket) {
		this.socket = socket;
		InputStream tmpIn = null;
		OutputStream tmpOut = null;
		// Get the input and output streams, using temp objects because
		// member streams are final
		try {
			tmpIn = socket.openInputStream();
			tmpOut = socket.openOutputStream();
		} catch (IOException e) {
		}
		bufReader = new BufferedReader(new InputStreamReader(tmpIn));
		bufWriter = new BufferedWriter(new OutputStreamWriter(tmpOut));
		this.connected = true;
	}

	public void run() {
		// Keep listening until an exception occurs
		while (true) {
			try {
				final String in = bufReader.readLine();
				System.out.println(in);
				if (in == null) {
					throw new IOException("Invalid Connection");
				}
				messageHistory.add(in);
				for (NewMessageListener l : listeners) {
					l.newMessage(in);
				}

			} catch (IOException e) {
				e.printStackTrace();
				connected = false;
				System.err.println("No longer recieving!");
				break;
			}
		}

	}

	/* Call this from the main thread to send data to the remote device */
	public void write(String toWrite) {
		try {
			bufWriter.write(toWrite);
			bufWriter.flush();
		} catch (IOException e) {
		}
	}

	/* Call this from the main thread to shutdown the connection */
	public void cancel() {
		try {
			socket.close();
		} catch (IOException e) {
		}
	}

	public boolean isConnected() {
		return connected;
	}
}
