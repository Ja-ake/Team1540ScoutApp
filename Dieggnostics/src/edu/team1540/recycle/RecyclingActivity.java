package edu.team1540.recycle;

import java.io.IOException;
import java.util.UUID;

import org.team1540.common.core.bluetooth.Address;
import org.team1540.common.core.schema.impl.StandSchema;

import android.os.Bundle;
import edu.team1540.egg.core.FragmentBasket;
import edu.team1540.egg.core.ScoutingActivity;
import edu.team1540.egg.impl.dispatching.DispatchingBasket;
import edu.team1540.egg.system.SystemConnectionHandler;
import edu.team1540.recycle.basket.home.HomeFragment;
import edu.team1540.recycle.basket.stand.AutonomousFragment;
import edu.team1540.recycle.basket.stand.TeleOpFragment;
import edu.team1540.recycle.user.Properties;

public final class RecyclingActivity extends ScoutingActivity {
	
	public Properties properties;
	
	@Override
	@SuppressWarnings("unchecked")
	public FragmentBasket[] getPages() {
		final Address a = new Address("60:C5:47:90:D9:15", UUID.fromString("513A4666-E634-43E0-A2FA-DC74058D74A2"));
		return new FragmentBasket[] {
				new FragmentBasket(this, "Home", new HomeFragment()),
				new DispatchingBasket<StandSchema>(
						a, new SystemConnectionHandler(), new StandSchema(), this, "Stand Scouting", new TeleOpFragment(), new AutonomousFragment()) };
	}
	
	@Override
	public void onCreate(Bundle b) {
		super.onCreate(b);
		properties = new Properties();
		try {
			properties.load("userid.txt", this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

