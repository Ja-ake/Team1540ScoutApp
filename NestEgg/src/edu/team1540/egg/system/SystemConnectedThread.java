package edu.team1540.egg.system;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.team1540.common.core.bluetooth.Address;
import org.team1540.common.core.pattern.Callback;
import org.team1540.common.core.pattern.Tuple;
import org.team1540.common.system.ConnectedThread;

import android.bluetooth.BluetoothSocket;

public class SystemConnectedThread implements ConnectedThread{

	public final Address address;

	public final BluetoothSocket socket;

	private final BufferedWriter bufWriter;
	private final BufferedReader bufReader;

	public Callback<Tuple<Address, String>> messageCallback;

	public SystemConnectedThread(Address target, BluetoothSocket socket){
		this.address=target;
		this.socket = socket;
		try {
			bufReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bufWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			new Thread(new Runnable(){
				@Override
				public void run() {
					while(true){
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
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Address getTarget(){
		return address;
	}

	public void setMessageCallback(Callback<Tuple<Address, String>> messageCallback){
		this.messageCallback=messageCallback;
	}

	public void sendMessage(String message, Callback<Tuple<Boolean, String>> callback){
		try {
			bufWriter.write(message);
			callback.callback(new Tuple<Boolean, String>(true,message));
		} catch (IOException e) {
			callback.callback(new Tuple<Boolean, String>(false,message));
		}
	}
}
