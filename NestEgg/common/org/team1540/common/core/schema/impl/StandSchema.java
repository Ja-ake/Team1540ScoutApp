package org.team1540.common.core.schema.impl;

import java.util.ArrayList;
import java.util.List;

import org.team1540.common.core.schema.Schema;
import org.team1540.common.core.schema.impl.ToteStackSchema.ContainerState;

public class StandSchema extends Schema {
	private static final long serialVersionUID = 2L;

	public List<ToteStackSchema> stacks;
	public List<ToteStackSchema> cooperatitionStacks;
	public int litterContainer, litterLandfill;
	public ContainerState[] containerStates;
	public int errorsAlpha, errorsBeta, errorsDelta; //TODO:refuctor me!


	public StandSchema() {
		stacks = new ArrayList<ToteStackSchema>();
		cooperatitionStacks = new ArrayList<ToteStackSchema>();
		containerStates = new ContainerState[4];
	}
}
