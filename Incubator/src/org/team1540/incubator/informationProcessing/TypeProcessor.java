package org.team1540.incubator.informationProcessing;

import org.team1540.incubator.informationProcessing.InformationProcessor.NotProcessableException;

public abstract class TypeProcessor {
	public final String processesWhat;

	public TypeProcessor(String type){
		processesWhat=type;
	}

	protected abstract void proccessInformation(Information i) throws NotProcessableException;

	public final void processInformation(String type,Information i) throws NotProcessableException{
		if(processesWhat!=type)
			throw new IllegalArgumentException("Type processor does not support type:"+type);
		else proccessInformation(i);
	}
}
