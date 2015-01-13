package edu.team1540.egg.core;

import android.app.Activity;
import android.app.Fragment;
import edu.team1540.egg.impl.drawer.NavigationDrawerWrapper.NamedSelection;

public class FragmentBasket implements NamedSelection<FragmentBasket> {
	public final String name;
	private final Fragment[] fragments;
	private final BasketDrawCallbacks callbacks;
	private int selection = 0;

	public <T extends Activity & BasketDrawCallbacks> FragmentBasket(final BasketDrawCallbacks callbacks, final String name, final Fragment... fragments) {
		this.name = name;
		this.fragments = fragments;
		this.callbacks = callbacks;
	}

	public Fragment getFragment() {
		return fragments[selection];
	}

	public void nextFragment() {
		selection++;
		callbacks.basketNeedsRedrawn(this);
	}

	public void previousFragment() {
		selection--;
		callbacks.basketNeedsRedrawn(this);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public FragmentBasket getItem() {
		return this;
	}

	public interface BasketDrawCallbacks {
		public void basketNeedsRedrawn(FragmentBasket b);
	}
}
