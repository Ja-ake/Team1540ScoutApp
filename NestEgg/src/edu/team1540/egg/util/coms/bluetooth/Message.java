package edu.team1540.egg.util.coms.bluetooth;

import java.io.Serializable;

public final class Message implements Serializable{
	/**
	 * REMEBER TO CHANGE THIS IF YOU EDIT THIS CLASS, WHICH YOU SHOULDN'T
	 */
	private static final long serialVersionUID = 7;
	public final String message;
	public final String type;

	public Message(String type, String message){
		this.message=message;
		this.type=type;
	}
}
