package org.team1540.incubator.informationProcessing.util;

import java.util.HashMap;
import java.util.Map;

import org.team1540.incubator.informationProcessing.Information;
import org.team1540.incubator.informationProcessing.InformationProcessor.NotProcessableException;
import org.team1540.incubator.informationProcessing.TypeProcessor;

public abstract class MappingTypeProcessor extends TypeProcessor{

	protected final boolean nonPairedBecomeEmpty;

	public MappingTypeProcessor(String type,boolean nonPairedBecomeEmpty) {
		super(type);
		this.nonPairedBecomeEmpty=nonPairedBecomeEmpty;
	}

	public MappingTypeProcessor(String type){
		this(type,false);
	}

	@Override
	protected void proccessInformation(Information i) throws NotProcessableException{
		Map<String,String> mapTo=new HashMap<String,String>();
		for(String s:i.informationContent){
			String[] mapping=s.split("[:]");
			try{
				mapTo.put(mapping[0], mapping[1]);
			}catch(ArrayIndexOutOfBoundsException e){
				if(!nonPairedBecomeEmpty) throw new NotProcessableException(s,e);
				else mapTo.put(mapping[0], "");
			}
		}
		processMap(mapTo);
	}

	public abstract void processMap(Map<String,String> output) throws NotProcessableException;
}
