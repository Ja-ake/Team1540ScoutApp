package edu.team1540.recycle;

import edu.team1540.egg.core.FragmentBasket;
import edu.team1540.egg.core.ScoutingActivity;
import edu.team1540.recycle.basket.home.HomeFragment;
import edu.team1540.recycle.basket.stand.TeleOpFragment;

public class RecyclingActivity extends ScoutingActivity {

	@Override
	public FragmentBasket[] getPages() {
		return new FragmentBasket[] { 
				new FragmentBasket(this, "Home", new HomeFragment()), 
				new FragmentBasket(this, "Stand Scouting", new TeleOpFragment()) 
			};
	}
}
