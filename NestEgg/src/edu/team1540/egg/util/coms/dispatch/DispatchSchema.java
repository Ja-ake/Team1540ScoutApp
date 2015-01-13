package edu.team1540.egg.util.coms.dispatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DispatchSchema {
	private final List<String> requirments = new ArrayList<String>();
	private final String schemaType;
	private boolean finalized = false;

	public DispatchSchema(final String schemaType) {
		this.schemaType = schemaType;
	}

	public DispatchSchema r(final String requirment) {
		require(requirment);
		return this;
	}

	public DispatchSchema require(final String... requirment) {
		if (finalized) {
			throw new IllegalStateException("Already finalized!");
		}
		requirments.addAll(Arrays.asList(requirment));
		return this;
	}

	public boolean fulfills(final Collection<String> fufilledRequirments) {
		return fufilledRequirments.containsAll(requirments);
	}

	public List<String> getRequirments() {
		return Collections.unmodifiableList(requirments);
	}

	public List<String> getValues(final Map<String, String> reqValMap) {
		final List<String> vals = new ArrayList<String>();
		for (final String require : requirments) {
			vals.add(reqValMap.get(require));
		}
		return vals;
	}

	@Override
	public void finalize() {
		if (finalized) {
			throw new IllegalStateException("Already finalized!");
		}
		finalized = true;
	}

	public boolean isFinalized() {
		return finalized;
	}

	public String getType() {
		return schemaType;
	}

}
