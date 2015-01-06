package edu.team1540.rabbit.basket.pit;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import edu.team1540.egg.impl.dispatching.DispatchingFragment;
import edu.team1540.inspector.R;

public class PitSendFragment extends DispatchingFragment{

	public PitSendFragment() {
		super(R.layout.pit_fragment_send);
	}

	public void readyLayout(){
		Log.w("RABBIT","SETUP!");
		this.setDecrement(this.<Button>getAsView(R.id.pit_send_previous_button));
		Button transmit=this.<Button>getAsView(R.id.pit_send_button);
		transmit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(submitDispatch()){
					launchFlushThread();
					attemptDecrementCurrentBasket();
					Log.i("RABBIT","success!");
				}
			}
		});
	}
}
