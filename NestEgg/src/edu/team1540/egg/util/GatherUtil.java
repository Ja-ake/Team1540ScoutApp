package edu.team1540.egg.util;

import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.ToggleButton;
import edu.team1540.egg.util.coms.dispatch.Gatherer;

public class GatherUtil {
	public static Gatherer asGatherer(final Spinner s){
		return new Gatherer(){
			@Override
			public String gather() {
				Object item=s.getSelectedItem();
				if(item==null){
					item="";
				}
				return item.toString();
			}
		};
	}
	public static Gatherer asGatherer(final ToggleButton s){
		return new Gatherer(){
			@Override
			public String gather() {
				return Boolean.toString(s.isChecked());
			}
		};
	}
	public static Gatherer asGatherer(final ProgressBar s){
		return new Gatherer(){
			@Override
			public String gather() {
				return s.getProgress()+"";
			}
		};
	}
}
