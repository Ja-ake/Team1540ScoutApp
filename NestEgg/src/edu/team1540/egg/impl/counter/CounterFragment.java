package edu.team1540.egg.impl.counter;

import java.util.concurrent.atomic.AtomicInteger;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import edu.team1540.egg.R;
import edu.team1540.egg.core.ScoutingFragment;

public class CounterFragment extends ScoutingFragment{
	private final AtomicInteger val = new AtomicInteger(0);

	public CounterFragment() {
		super(R.layout.counter_fragment);
	}

	@Override
	public void readyLayout() {
		final Button increment = (Button) getView().findViewById(R.id.increment_button);
		final Button decrement = (Button) getView().findViewById(R.id.decrement_button);
		final TextView diplay = (TextView) getView().findViewById(R.id.num_view);
		increment.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				val.incrementAndGet();
				diplay.setText(val.get() + "");
			}
		});
		decrement.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				synchronized (val) {
					if (val.get() > 0) {
						val.decrementAndGet();
						diplay.setText(val.get() + "");
					}
				}
			}
		});
	}

	public int getCounterValue() {
		return val.get();
	}
}
