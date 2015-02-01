package org.team1540.incubator;

import java.util.UUID;

import org.team1540.common.core.bluetooth.Address;
import org.team1540.incubator.system.SystemConnectionHandler;

public class Launcher {
	public static final UUID myUUID = UUID.fromString("513A4666E63443E0A2FADC74058D74A2");
	public static final String myBluetoothAddress = "60:C5:47:90:D9:15";
	public static final Address address = new Address(Launcher.myBluetoothAddress, Launcher.myUUID, "Team_1540_Competitive_Analysis");

	public static void main(final String[] args) throws InterruptedException {
		final HandlerManager manager = new HandlerManager();
		manager.register(new SystemConnectionHandler(), Launcher.address);
	}
}
