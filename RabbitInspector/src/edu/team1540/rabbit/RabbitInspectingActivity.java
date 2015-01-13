package edu.team1540.rabbit;

import edu.team1540.egg.core.FragmentBasket;
import edu.team1540.egg.core.ScoutingActivity;
import edu.team1540.egg.impl.dispatching.DispatchingBasket;
import edu.team1540.egg.util.coms.bluetooth.ComBridge;
import edu.team1540.inspector.R;
import edu.team1540.rabbit.basket.home.HomeFragment;
import edu.team1540.rabbit.basket.pit.PitHomeFragment;
import edu.team1540.rabbit.basket.pit.PitSendFragment;
import edu.team1540.rabbit.schema.RabbitSchemas;

public class RabbitInspectingActivity extends ScoutingActivity {
	@Override
	public FragmentBasket[] getPages() {
		ComBridge uplink = new ComBridge(this, "bunnyBotsScouting", getString(R.string.server_address), getString(R.string.server_UUID));
		return new FragmentBasket[] { new FragmentBasket(this, "Home", new HomeFragment()),
				new DispatchingBasket(uplink, RabbitSchemas.PIT_SCOUT.SCHEME, this, "Pit Scouting!", new PitHomeFragment(), new PitSendFragment()), };
	}
}
