package edu.team1540.egg.impl.drawer;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import edu.team1540.egg.R;
import edu.team1540.egg.impl.drawer.NavigationDrawerFragment.NavigationDrawerCallbacks;

public class NavigationDrawerWrapper<T> implements NavigationDrawerCallbacks {

	private final NavigationDrawerFragment represents;
	private final NamedSelection<T>[] options;
	public DrawerCallback<T> callback;

	public NavigationDrawerWrapper(final Activity a, final NamedSelection<T>[] options) {
		this.options = options;
		represents = (NavigationDrawerFragment) a.getFragmentManager().findFragmentById(R.id.navigation_drawer);
		represents.callbacks = this;
	}

	@Override
	public void onNavigationDrawerItemSelected(final int position) {
		if (callback != null) {
			callback.itemSelected(options[position].getItem());
		}
	}

	public void setUp(final Activity a) {
		final String[] stringOptions = new String[options.length];
		int count = -1;
		for (final NamedSelection<T> selection : options) {
			count++;
			stringOptions[count] = selection.getName();
		}
		Log.i("NEST_EGG", "setting up...");
		represents.createOptions(stringOptions);
		represents.setUp(R.id.navigation_drawer, (DrawerLayout) a.findViewById(R.id.drawer_layout));
	}

	public boolean isDrawerOpen() {
		return represents.isDrawerOpen();
	}

	// Helper interfaces
	public interface DrawerCallback<T> {
		public void itemSelected(T t);
	}

	public interface NamedSelection<T> {
		public String getName();

		public T getItem();
	}
}
