package edu.team1540.recycle.basket.stand;

import org.team1540.common.core.schema.impl.StandSchema;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import edu.team1540.egg.impl.dispatching.DispatchingFragment;
import edu.team1540.recycle.R;
import edu.team1540.recycle.draw.StackSurfaceView;

public class TeleOpFragment extends DispatchingFragment<StandSchema> {

	private final Activity activity;

	public TeleOpFragment(final Activity a) {
		super(R.layout.stand_fragment_teleop);
		activity = a;
	}

	@Override
	public void readyLayout() {
		final StackSurfaceView stackSurfaceView = new StackSurfaceView(activity);

		final SurfaceView sv = this.<SurfaceView> getAsView(R.id.totes_surface);
		sv.getHolder().addCallback(stackSurfaceView);

		final Button[][] button_container = new Button[4][3];

		final Button button_container_minus = this.<Button> getAsView(R.id.button_container_minus);
		final Button button_container_plus = this.<Button> getAsView(R.id.button_container_plus);
		final Button button_landfill_minus = this.<Button> getAsView(R.id.button_landfill_minus);
		final Button button_landfill_plus = this.<Button> getAsView(R.id.button_landfill_plus);
		final Button button_submit = this.<Button> getAsView(R.id.button_submit);
		final Button button_undo = this.<Button> getAsView(R.id.button_undo);
		button_container[0][0] = this.<Button> getAsView(R.id.container00);
		button_container[0][1] = this.<Button> getAsView(R.id.container01);
		button_container[0][2] = this.<Button> getAsView(R.id.container02);
		button_container[1][0] = this.<Button> getAsView(R.id.container10);
		button_container[1][1] = this.<Button> getAsView(R.id.container11);
		button_container[1][2] = this.<Button> getAsView(R.id.container12);
		button_container[2][0] = this.<Button> getAsView(R.id.container20);
		button_container[2][1] = this.<Button> getAsView(R.id.container21);
		button_container[2][2] = this.<Button> getAsView(R.id.container22);
		button_container[3][0] = this.<Button> getAsView(R.id.container30);
		button_container[3][1] = this.<Button> getAsView(R.id.container31);
		button_container[3][2] = this.<Button> getAsView(R.id.container32);

		final SurfaceView ssv = this.<SurfaceView> getAsView(R.id.totes_surface);

		final StandButtonHandler standButtonHandler = new StandButtonHandler(getSchema(), this);
		class TeleOnClickListener implements OnClickListener {
			public int id;

			public TeleOnClickListener(final int i) {
				super();
				id = i;
			}

			@Override
			public void onClick(final View v) {
				if (!(v instanceof Button)) {
					return;
				}
				standButtonHandler.onClick(id);
			}
		}

		button_container_minus.setOnClickListener(new TeleOnClickListener(R.id.button_container_minus));
		button_container_plus.setOnClickListener(new TeleOnClickListener(R.id.button_container_plus));
		button_landfill_minus.setOnClickListener(new TeleOnClickListener(R.id.button_landfill_minus));
		button_landfill_plus.setOnClickListener(new TeleOnClickListener(R.id.button_landfill_plus));
		button_submit.setOnClickListener(new TeleOnClickListener(R.id.button_submit));
		button_undo.setOnClickListener(new TeleOnClickListener(R.id.button_undo));
		button_container[0][0].setOnClickListener(new TeleOnClickListener(R.id.container00));
		button_container[0][1].setOnClickListener(new TeleOnClickListener(R.id.container01));
		button_container[0][2].setOnClickListener(new TeleOnClickListener(R.id.container02));
		button_container[1][0].setOnClickListener(new TeleOnClickListener(R.id.container10));
		button_container[1][1].setOnClickListener(new TeleOnClickListener(R.id.container11));
		button_container[1][2].setOnClickListener(new TeleOnClickListener(R.id.container12));
		button_container[2][0].setOnClickListener(new TeleOnClickListener(R.id.container20));
		button_container[2][1].setOnClickListener(new TeleOnClickListener(R.id.container21));
		button_container[2][2].setOnClickListener(new TeleOnClickListener(R.id.container22));
		button_container[3][0].setOnClickListener(new TeleOnClickListener(R.id.container30));
		button_container[3][1].setOnClickListener(new TeleOnClickListener(R.id.container31));
		button_container[3][2].setOnClickListener(new TeleOnClickListener(R.id.container32));
		ssv.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(final View v, final MotionEvent event) {
				v.performClick();
				stackSurfaceView.mainStackDrawer.stackHeight = (int) ((850 - (event.getY() - stackSurfaceView.mainStackDrawer.y)) / ((1070 - stackSurfaceView.mainStackDrawer.y) / 7));
				return true;
			}
		});
	}
}
