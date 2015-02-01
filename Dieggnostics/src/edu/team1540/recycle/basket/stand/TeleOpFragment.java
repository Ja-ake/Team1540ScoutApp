package edu.team1540.recycle.basket.stand;

import android.app.Activity;
import android.view.SurfaceView;
import edu.team1540.egg.core.ScoutingFragment;
import edu.team1540.recycle.R;
import edu.team1540.recycle.draw.StackSurfaceView;

public class TeleOpFragment extends ScoutingFragment {

	private final Activity activity;
	
	public TeleOpFragment(Activity a) {
		super(R.layout.stand_fragment_teleop);
		activity = a;
	}

	@Override
	public void readyLayout() {
		SurfaceView sv = this.<SurfaceView> getAsView(R.id.totes_surface);
		sv.getHolder().addCallback(new StackSurfaceView(activity));
	}
}
