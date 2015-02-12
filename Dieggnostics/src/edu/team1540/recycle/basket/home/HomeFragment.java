package edu.team1540.recycle.basket.home;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import edu.team1540.egg.core.ScoutingFragment;
import edu.team1540.recycle.R;
import edu.team1540.recycle.RecyclingActivity;

public class HomeFragment extends ScoutingFragment {

	public HomeFragment() {
		super(R.layout.stand_fragment_login);
	}
	
	@Override
	public void readyLayout() {
		final Button buttonLogin = this.<Button> getAsView(R.id.button_login);
		final EditText textLogin = this.<EditText> getAsView(R.id.login_number);
		final TextView loginName = this.<TextView> getAsView(R.id.login_name);

		buttonLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final RecyclingActivity activity = (RecyclingActivity) HomeFragment.this.getActivity();
				String id = textLogin.getText().toString();
				activity.properties.getProperty(id);
				
				// TODO: login!
			}
		});
		
		textLogin.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				final RecyclingActivity activity = (RecyclingActivity) HomeFragment.this.getActivity();
				String id = s.toString();
				loginName.setText(activity.properties.getProperty(id));
			}
		});
	}
}
