package edu.team1540.recycle;

import edu.team1540.egg.core.FragmentBasket;
import edu.team1540.egg.core.ScoutingActivity;
import edu.team1540.recycle.basket.home.HomeFragment;

public class RecyclingActivity extends ScoutingActivity {

	@Override
	public FragmentBasket[] getPages() {
		return new FragmentBasket[] { new FragmentBasket(this, "Home", new HomeFragment()) };
	}
}
