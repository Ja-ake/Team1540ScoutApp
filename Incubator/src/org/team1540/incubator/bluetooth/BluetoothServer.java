package org.team1540.incubator.bluetooth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

public class BluetoothServer {
	public final Thread collector;
	public final List<ConnectedThread> connected = new ArrayList<ConnectedThread>();
	public final Queue<NewConnectionListener> connectionListeners=new ConcurrentLinkedQueue<NewConnectionListener>();

	public BluetoothServer(String uuid, String serviceName) {
		try {
			String connURL = "btspp://localhost:" + uuid.toString()
					+ ";name=" + serviceName;
			final StreamConnectionNotifier scn = (StreamConnectionNotifier) Connector.open(connURL);
			collector = new Thread(new Runnable() {
				@Override
				public void run() {
					while(true){
						StreamConnection conn;
						try {
							LocalDevice.getLocalDevice().setDiscoverable(DiscoveryAgent.GIAC);
							conn = scn.acceptAndOpen();
							RemoteDevice rd = RemoteDevice.getRemoteDevice(conn);
							rd.authenticate();
							System.out.println("New device connected:"+rd.getFriendlyName(false));
							ConnectedThread connection=new ConnectedThread(conn);
							for(NewConnectionListener l:connectionListeners){
								l.newConnection(connection);
							}
							connected.add(connection);
							connection.start();
						} catch (IOException e) {
							throw new RuntimeException(e);
						}
					}
				}
			});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public void start(){
		collector.start();
	}

	public interface NewConnectionListener {
		public void newConnection(ConnectedThread c);
	}

	public interface NewMessageListener {
		public void newMessage(String s);
	}

}
