package edu.team1540.egg.core;

import java.lang.reflect.Field;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class ScoutingFragment extends Fragment {

	private final int layoutID;

	public ScoutingFragment(final int layoutID) {
		this.layoutID = layoutID;
	}

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		final View rootView = inflater.inflate(layoutID, container, false);
		return rootView;
	}

	@Override
	public void onDetach() {
		// Hack around known android issue
		super.onDetach();
		try {
			final Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);
		} catch (final NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (final IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends View> T getAsView(final int id) {
		return (T) getView().findViewById(id);
	}

	@SuppressWarnings("unchecked")
	public <T extends Fragment> T getAsFragment(final int id) {
		return (T) getChildFragmentManager().findFragmentById(id);
	}

	public void attemptIncrementCurrentBasket() {
		((ScoutingActivity) getActivity()).incrementCurrentBasket();
	}

	public void attemptDecrementCurrentBasket() {
		((ScoutingActivity) getActivity()).decrementCurrentBasket();
	}

	public void setIncrement(final View v) {
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				attemptIncrementCurrentBasket();
			}
		});
	}

	public void setDecrement(final View v) {
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				attemptDecrementCurrentBasket();
			}
		});
	}

	@Override
	public void onStart() {
		super.onStart();
		readyLayout();
	}

	public void readyLayout() {
	}
}
