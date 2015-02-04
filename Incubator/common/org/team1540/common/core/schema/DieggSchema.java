package org.team1540.common.core.schema;

import java.util.ArrayList;
import java.util.List;

public class DieggSchema extends Schema {
	private static final long serialVersionUID = 2L;

	public class ToteStack {
		public int oldHeight, newHeight;
		public boolean oldContainer, newContainer;
		
		public ToteStack(int oh, int nh, boolean oc, boolean nc) {
			oldHeight = oh;
			newHeight = nh;
			oldContainer = oc;
			newContainer = nc;
		}
	}
	
	public enum ContainerState {
		OTHER_SIDE, TIPPED, COLLECTED
	}
	
	public List<ToteStack> stacks;
	public List<ToteStack> cooperatitionStacks;
	public int litterContainer, litterLandfill;
	public ContainerState[] containerStates;
	public int errorsAlpha, errorsBeta, errorsDelta; // refactor me!
	
	
	public DieggSchema() {
		stacks = new ArrayList<ToteStack>();
		cooperatitionStacks = new ArrayList<ToteStack>();
		containerStates = new ContainerState[4];
	}
}
