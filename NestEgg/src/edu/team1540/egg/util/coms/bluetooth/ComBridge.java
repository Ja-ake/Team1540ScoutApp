package edu.team1540.egg.util.coms.bluetooth;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import edu.team1540.egg.util.cache.CacheManager;
import edu.team1540.egg.util.coms.bluetooth.bridge.ConnectedThread;
import edu.team1540.egg.util.coms.bluetooth.bridge.ConnecterThread;
import edu.team1540.egg.util.coms.bluetooth.bridge.ConnecterThread.SocketManager;

public class ComBridge {

	private ConnectedThread connection;

	private final Queue<Message> queuedMessages=new ConcurrentLinkedQueue<Message>();
	private final CacheManager<Message> cacheManager;

	public final String targetAddress;
	public final String targetUUID;

	public ComBridge(Activity a,String dispatchName,String targetAddress, String targetUUID){
		this.targetAddress=targetAddress;
		this.targetUUID=targetUUID;
		this.cacheManager=new CacheManager<Message>(a, "comBridge_"+dispatchName);
	}

	public synchronized AtomicReference<Condition> reconnect(){
		if(connection!=null && !connection.isDone()){
			connection.cancel();
		}
		BluetoothAdapter adapter=BluetoothAdapter.getDefaultAdapter();
		final AtomicReference<Condition> condition=new AtomicReference<Condition>(Condition.WORKING);
		condition.set(Condition.WORKING);
		new ConnecterThread(adapter, adapter.getRemoteDevice(targetAddress),targetUUID, new SocketManager(){
			@Override
			public void manageSocket(BluetoothSocket socket) {
				synchronized(condition){
					connection=new ConnectedThread(socket);
					condition.set(Condition.COMPLETED);
					condition.notifyAll();
				}
			}

			@Override
			public void failed(){
				condition.set(Condition.FAILED);
			}
		},25);
		return condition;
	}

	public synchronized boolean blockReconnect(){
		AtomicReference<Condition> condition=reconnect();
		synchronized(condition){
			while(condition.get()==Condition.WORKING){
				try {
					condition.wait(10);
				} catch (InterruptedException e) {}
			}
			switch(condition.get()){
			case COMPLETED:
				return true;
			case FAILED:
				return false;
			default:
				throw new IllegalStateException("WTF?");
			}
		}
	}

	public void queueMessage(Message m){
		queuedMessages.add(m);
	}

	public synchronized boolean attemptClearBacklog(){
		boolean cleared=true;
		while(hasQueuedMessages()){
			cleared=attemptQueuedMessage() && cleared;
		}
		while(!cacheManager.cacheEmpty()){
			cleared=attemptCachedMessage() && cleared;
		}
		return cleared;
	}

	private synchronized boolean sendMessage(Message target){
		Log.i("NEST_EGG","sending message...");
		if(target==null){
			return true;
		}
		if(connection==null || connection.isDone()){
			if(!blockReconnect()){
				return false;
			}
		}
		Log.i("NEST_EGG","passed checks...");
		try {
			connection.write(target.type);
			connection.write(target.message);
			connection.write(target.type);
			return true;
		} catch (IOException e1) {
			return false;
		}
	}

	public synchronized boolean attemptQueuedMessage(){
		Message m=queuedMessages.poll();
		if(sendMessage(m)){
			cacheManager.freeze(m);
			return true;
		}else{
			cacheManager.cache(m);
			return false;
		}
	}

	public synchronized boolean attemptCachedMessage(){
		Message m=cacheManager.getCached();
		if(sendMessage(m)){
			cacheManager.unCache(m);
			cacheManager.freeze(m);
			return true;
		}
		return false;
	}

	public boolean hasQueuedMessages(){
		return !queuedMessages.isEmpty();
	}

	public enum Condition{
		WORKING, FAILED, COMPLETED
	}
}