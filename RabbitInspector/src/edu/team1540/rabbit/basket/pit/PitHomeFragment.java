package edu.team1540.rabbit.basket.pit;

import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import edu.team1540.egg.impl.counter.CounterFragment;
import edu.team1540.egg.impl.dispatching.DispatchingFragment;
import edu.team1540.egg.util.GatherUtil;
import edu.team1540.inspector.R;

public class PitHomeFragment extends DispatchingFragment{

	public PitHomeFragment() {
		super(R.layout.pit_fragment_home);
	}

	public void readyLayout(){
		patchDispatch("scout","Gregor");
		patchDispatch("robot",GatherUtil.asGatherer(
				this.<Spinner>getAsView(R.id.pit_team_measure)));

		patchDispatch("mainWheelType",GatherUtil.asGatherer(
				this.<Spinner>getAsView(R.id.pit_wheeltype_measure)));

		patchDispatch("coolnessOfWidgets",GatherUtil.asGatherer(
				this.<ProgressBar>getAsView(R.id.pit_widget_mesure)));

		patchDispatch("chillness",GatherUtil.asGatherer(
				this.<ProgressBar>getAsView(R.id.pit_chillness_mesure)));
		patchDispatch("numWheels",
				CounterFragment.getCounterGatherer(R.id.pit_wheelnum_measure_fragment, this.getChildFragmentManager()));
		//Setup the buttons
		setIncrement(this.<Button>getAsView(R.id.pit_home_next_button));
	}
}
