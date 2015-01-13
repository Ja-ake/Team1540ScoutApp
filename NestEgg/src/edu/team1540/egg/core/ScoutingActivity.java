package edu.team1540.egg.core;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import edu.team1540.egg.R;
import edu.team1540.egg.core.FragmentBasket.BasketDrawCallbacks;
import edu.team1540.egg.impl.drawer.NavigationDrawerWrapper;
import edu.team1540.egg.impl.drawer.NavigationDrawerWrapper.DrawerCallback;

public abstract class ScoutingActivity extends Activity implements DrawerCallback<FragmentBasket>, BasketDrawCallbacks {

	/**
	 * A fragment for defining the navigation drawer
	 */
	private NavigationDrawerWrapper<FragmentBasket> navigationDrawerWrapper;
	private FragmentBasket currentBasket = null;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scouting);
		Log.i("NEST_EGG", "creating scouting activity...");
		navigationDrawerWrapper = new NavigationDrawerWrapper<FragmentBasket>(this, getPages());
		navigationDrawerWrapper.setUp(this);
		navigationDrawerWrapper.callback = this;
		navigationDrawerWrapper.onNavigationDrawerItemSelected(0);
	}

	@Override
	public void itemSelected(final FragmentBasket item) {
		// update the main content by replacing fragments
		currentBasket = item;
		Log.i("NEST_EGG", item.toString());
		final FragmentManager fragmentManager = getFragmentManager();
		final Fragment target = item.getFragment();
		if (target == null) {
			throw new NullPointerException();
		}
		fragmentManager.beginTransaction().replace(R.id.container, target).commit();
	}

	public void restoreActionBar() {
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(R.string.title_activity_scouting);
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		if (!navigationDrawerWrapper.isDrawerOpen()) {
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void basketNeedsRedrawn(final FragmentBasket source) {
		final FragmentManager fragmentManager = getFragmentManager();
		final Fragment target = source.getFragment();
		if (target == null) {
			throw new NullPointerException();
		}
		fragmentManager.beginTransaction().replace(R.id.container, target).commit();
	}

	public void incrementCurrentBasket() {
		currentBasket.nextFragment();
	}

	public void decrementCurrentBasket() {
		currentBasket.previousFragment();
	}

	public abstract FragmentBasket[] getPages();
}
