package org.team1540.incubator.informationProcessing;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.team1540.incubator.bluetooth.ConnectedThread;
import org.team1540.incubator.bluetooth.BluetoothServer.NewConnectionListener;



public class InformationProcessor implements NewConnectionListener{

	public boolean autoProcess=false;
	public Map<String,TypeProcessor> subProcessors=new ConcurrentHashMap<String,TypeProcessor>();
	protected Map<String,Queue<Information>> typeInformationMap=new ConcurrentHashMap<String,Queue<Information>>();


	public InformationProcessor(TypeProcessor[] tps){
		for(TypeProcessor tp:tps){
			subProcessors.put(tp.processesWhat, tp);
		}
	}

	public void submitInformation(Information i){
		if(!typeInformationMap.containsKey(i.typeOfInformation)){
			typeInformationMap.put(i.typeOfInformation, new ConcurrentLinkedQueue<Information>());
		}
		typeInformationMap.get(i.typeOfInformation).add(i);
		if(autoProcess){
			try {
				processInformationType(i.typeOfInformation);
			} catch (NotProcessableException e) {
				throw new RuntimeException("WTF???");
			}
		}
	}

	public void processInformationType(String type) throws NotProcessableException{
		if(!typeInformationMap.containsKey(type)){
			return;
		}
		if(!subProcessors.containsKey(type)){
			throw new NotProcessableException("No type processor for:"+type);
		}
		TypeProcessor toProcessWith=subProcessors.get(type);
		for(Information i:typeInformationMap.get(type)){
			toProcessWith.proccessInformation(i);
		}
	}

	public void processAllInformation() throws NotProcessableException{
		for(String s:typeInformationMap.keySet()){
			processInformationType(s);
		}
	}

	public boolean hasInformationToProcess(){
		for (Queue<Information> cell:typeInformationMap.values()){
			if (!cell.isEmpty()){
				return true;
			}
		}
		return false;
	}

	public void clearInformation(){
		for (Queue<Information> cell:typeInformationMap.values()){
			cell.clear();
		}
	}

	@Override
	public void newConnection(ConnectedThread c) {
		c.listeners.add(new InformationGenerator(this));
	}

	@SuppressWarnings("serial")//This class is not designed to be serialized
	public static class NotProcessableException extends Exception {

		public NotProcessableException(){
			super();
		}

		public NotProcessableException(Exception e) {
			super(e);
		}

		public NotProcessableException(String string) {
			super(string);
		}

		public NotProcessableException(String string, Exception e) {
			super(string,e);
		}
	}
}
