package org.team1540.common.core.schema.impl;

import java.io.Serializable;

import org.team1540.common.core.schema.Schema;

public class ToteStackSchema extends Schema {
	private static final long serialVersionUID = 2L;

	public int oldHeight, newHeight;
	public boolean oldContainer, newContainer;

	public ToteStackSchema(final int oh, final int nh, final boolean oc, final boolean nc) {
		oldHeight = oh;
		newHeight = nh;
		oldContainer = oc;
		newContainer = nc;
	}

	public enum ContainerState implements Serializable {
		OTHER_SIDE, TIPPED, COLLECTED;

		public static final long serialVersionUID = 2L;
	}
}
