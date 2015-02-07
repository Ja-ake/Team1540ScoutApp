package edu.team1540.recycle.basket.stand;

import org.team1540.common.core.schema.impl.StandSchema;
import org.team1540.common.core.schema.impl.ToteStackSchema.ContainerState;

import android.util.Log;
import android.widget.TextView;
import edu.team1540.recycle.R;

public class StandButtonHandler {
	private final StandSchema scheme;
	private final TeleOpFragment fragment;
	
	public StandButtonHandler(StandSchema schem, TeleOpFragment frag) {
		scheme = schem;
		fragment = frag;
	}
	
	public void onClick(int id) {
		switch (id) {
		case R.id.button_container_minus	:
			scheme.litterContainer--;
			break;
		case R.id.button_container_plus	    :
			scheme.litterContainer++;
			break;
		case R.id.button_cooperatition		:
			
			break;
		case R.id.button_landfill_minus	    :
			scheme.litterLandfill--;
			final TextView tv = fragment.<TextView> getAsView(R.id.text_landfill_count);
			tv.setText((Integer.parseInt(tv.getText().toString()) - 1) + "");
			break;
		case R.id.button_landfill_plus		:
			scheme.litterLandfill++;
			break;
		case R.id.button_submit			    :

			break;
		case R.id.button_undo				:
			break;
		case R.id.container00				:
			scheme.containerStates[0] = ContainerState.OTHER_SIDE;
			break;
		case R.id.container01				:
			scheme.containerStates[0] = ContainerState.TIPPED;
			break;
		case R.id.container02				:
			scheme.containerStates[0] = ContainerState.COLLECTED;
			break;
		case R.id.container10				:
			scheme.containerStates[1] = ContainerState.OTHER_SIDE;
			break;
		case R.id.container11				:
			scheme.containerStates[1] = ContainerState.TIPPED;
			break;
		case R.id.container12				:
			scheme.containerStates[1] = ContainerState.COLLECTED;
			break;
		case R.id.container20				:
			scheme.containerStates[2] = ContainerState.OTHER_SIDE;
			break;
		case R.id.container21				:
			scheme.containerStates[2] = ContainerState.TIPPED;
			break;
		case R.id.container22				:
			scheme.containerStates[2] = ContainerState.COLLECTED;
			break;
		case R.id.container30				:
			scheme.containerStates[3] = ContainerState.OTHER_SIDE;
			break;
		case R.id.container31				:
			scheme.containerStates[3] = ContainerState.TIPPED;
			break;
		case R.id.container32				:
			scheme.containerStates[3] = ContainerState.COLLECTED;
			break;
		case R.id.totes_surface:
			break;
		default:
			Log.w("DIE", "A button was pressed that is not handled.");
			break;
		}
		
		Log.i("DIE", "Pressed: " + id);
	}
}
