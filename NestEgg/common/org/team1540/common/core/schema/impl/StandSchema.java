package org.team1540.common.core.schema.impl;

import java.util.ArrayList;
import java.util.List;

import org.team1540.common.core.schema.Schema;
import org.team1540.common.core.schema.impl.ToteStackSchema.ContainerState;

public class StandSchema extends Schema {
	private static final long serialVersionUID = 2L;

	public List<ToteStackSchema> stacks;
	public ContainerState[] containerStates;
	public int litterContainer, litterLandfill;
	public int errorsAlpha, errorsBeta, errorsDelta; // TODO: refactor me! <gregor you can do this if you know the names>

	public boolean leftContainerAuto, middleContainerAuto, rightContainerAuto, leftToteAuto, middleToteAuto, rightToteAuto;
	public boolean stackedTotes, endedInAuto;
	
	public String notes;
	
	public StandSchema() {
		stacks = new ArrayList<ToteStackSchema>();
		containerStates = new ContainerState[4];
		leftContainerAuto = middleContainerAuto = rightContainerAuto = 
				leftToteAuto = middleToteAuto = rightToteAuto = false;
		stackedTotes = endedInAuto = false;
		
		notes = "";
	}
}
