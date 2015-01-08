package org.team1540.incubator.informationProcessing;

import java.util.ArrayList;
import java.util.List;

import org.team1540.incubator.bluetooth.BluetoothServer.NewMessageListener;

public class InformationGenerator implements NewMessageListener {
	protected String currentInformationType = null;
	protected final InformationProcessor processor;
	protected final List<String> currentParts = new ArrayList<String>();

	public InformationGenerator(InformationProcessor processor) {
		this.processor = processor;
	}

	@Override
	public void newMessage(String s) {
		if (currentInformationType == null) {
			currentInformationType = s;
		} else if (currentInformationType.equals(s)) {
			String[] infoContents = new String[currentParts.size()];
			infoContents = currentParts.toArray(infoContents);
			processor.submitInformation(new Information(currentInformationType, infoContents));
			currentInformationType = null;
			currentParts.clear();
		} else {
			currentParts.add(s);
		}
	}
}
