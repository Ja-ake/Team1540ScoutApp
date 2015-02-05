package edu.team1540.recycle.basket.stand;

import android.app.Activity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
		
		Button[][] button_container		= new Button[4][3];

		Button button_container_minus	= this.<Button> getAsView(R.id.button_container_minus	);
		Button button_container_plus	= this.<Button> getAsView(R.id.button_container_plus	);
		Button button_cooperatition	 	= this.<Button> getAsView(R.id.button_cooperatition		);
		Button button_landfill_minus	= this.<Button> getAsView(R.id.button_landfill_minus	);
		Button button_landfill_plus	 	= this.<Button> getAsView(R.id.button_landfill_plus		);
		Button button_submit			= this.<Button> getAsView(R.id.button_submit			);
		Button button_undo				= this.<Button> getAsView(R.id.button_undo				);
			   button_container[0][0]	= this.<Button> getAsView(R.id.container00				);
			   button_container[0][1]	= this.<Button> getAsView(R.id.container01				);
			   button_container[0][2]	= this.<Button> getAsView(R.id.container02				);
			   button_container[1][0]	= this.<Button> getAsView(R.id.container10				);
			   button_container[1][1]	= this.<Button> getAsView(R.id.container11				);
			   button_container[1][2]	= this.<Button> getAsView(R.id.container12				);
			   button_container[2][0]	= this.<Button> getAsView(R.id.container20				);
			   button_container[2][1]	= this.<Button> getAsView(R.id.container21				);
			   button_container[2][2]	= this.<Button> getAsView(R.id.container22				);
			   button_container[3][0]	= this.<Button> getAsView(R.id.container30				);
			   button_container[3][1]	= this.<Button> getAsView(R.id.container31				);
			   button_container[3][2]	= this.<Button> getAsView(R.id.container32				);
			   
		class TeleOnClickListener implements OnClickListener {
			public int id;
			
			public TeleOnClickListener(int i) {
				super();
				id = i;
			}
			
			@Override
			public void onClick(View v) {
				if (!(v instanceof Button)) return;
				Button button = (Button) v;
				
				switch (id) {
				case R.id.button_container_minus	:
					break;
				case R.id.button_container_plus	    :
					break;
				case R.id.button_cooperatition		:
					break;
				case R.id.button_landfill_minus	    :
					break;
				case R.id.button_landfill_plus		:
					break;
				case R.id.button_submit			    :
					break;
				case R.id.button_undo				:
					break;
				case R.id.container00				:
					break;
				case R.id.container01				:
					break;
				case R.id.container02				:
					break;
				case R.id.container10				:
					break;
				case R.id.container11				:
					break;
				case R.id.container12				:
					break;
				case R.id.container20				:
					break;
				case R.id.container21				:
					break;
				case R.id.container22				:
					break;
				case R.id.container30				:
					break;
				case R.id.container31				:
					break;
				case R.id.container32				:
					break;
				default:
					Log.w("DIE", "A button was pressed that is not handled.");
					break;
				}
			}	
		}
		
		button_container_minus.setOnClickListener(new TeleOnClickListener(R.id.button_container_minus	));
		button_container_plus .setOnClickListener(new TeleOnClickListener(R.id.button_container_plus	));
		button_cooperatition  .setOnClickListener(new TeleOnClickListener(R.id.button_cooperatition		));
		button_landfill_minus .setOnClickListener(new TeleOnClickListener(R.id.button_landfill_minus	));
		button_landfill_plus  .setOnClickListener(new TeleOnClickListener(R.id.button_landfill_plus		));
		button_submit		  .setOnClickListener(new TeleOnClickListener(R.id.button_submit			));
		button_undo			  .setOnClickListener(new TeleOnClickListener(R.id.button_undo				));
		button_container[0][0].setOnClickListener(new TeleOnClickListener(R.id.container00				));
		button_container[0][1].setOnClickListener(new TeleOnClickListener(R.id.container01				));
		button_container[0][2].setOnClickListener(new TeleOnClickListener(R.id.container02				));
		button_container[1][0].setOnClickListener(new TeleOnClickListener(R.id.container10				));
		button_container[1][1].setOnClickListener(new TeleOnClickListener(R.id.container11				));
		button_container[1][2].setOnClickListener(new TeleOnClickListener(R.id.container12				));
		button_container[2][0].setOnClickListener(new TeleOnClickListener(R.id.container20				));
		button_container[2][1].setOnClickListener(new TeleOnClickListener(R.id.container21				));
		button_container[2][2].setOnClickListener(new TeleOnClickListener(R.id.container22				));
		button_container[3][0].setOnClickListener(new TeleOnClickListener(R.id.container30				));
		button_container[3][1].setOnClickListener(new TeleOnClickListener(R.id.container31				));
		button_container[3][2].setOnClickListener(new TeleOnClickListener(R.id.container32				));
	}
}
