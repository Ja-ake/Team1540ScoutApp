package edu.team1540.recycle;

import org.team1540.common.core.bluetooth.Address;
import org.team1540.common.core.schema.impl.StandSchema;

import edu.team1540.egg.core.FragmentBasket;
import edu.team1540.egg.core.ScoutingActivity;
import edu.team1540.egg.impl.dispatching.DispatchingBasket;
import edu.team1540.egg.system.SystemConnectionHandler;
import edu.team1540.recycle.basket.home.HomeFragment;
import edu.team1540.recycle.basket.stand.AutonomousFragment;
import edu.team1540.recycle.basket.stand.TeleOpFragment;

public final class RecyclingActivity extends ScoutingActivity {
	
	@Override
	@SuppressWarnings("unchecked")
	public FragmentBasket[] getPages() {
		final Address a = new Address("60:C5:47:90:D9:15");
		return new FragmentBasket[] {
				new FragmentBasket(this, "Home", new HomeFragment()),
				new DispatchingBasket<StandSchema>(
						a, new SystemConnectionHandler(), new StandSchema(), this, "Stand Scouting", new TeleOpFragment(), new AutonomousFragment()) };
	}
}

