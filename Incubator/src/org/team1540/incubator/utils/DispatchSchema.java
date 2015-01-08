package org.team1540.incubator.utils;

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

	public DispatchSchema(String schemaType) {
		this.schemaType = schemaType;
	}

	public DispatchSchema r(String requirment) {
		require(requirment);
		return this;
	}

	public DispatchSchema require(String... requirment) {
		if (finalized) { throw new IllegalStateException("Already finalized!"); }
		requirments.addAll(Arrays.asList(requirment));
		return this;
	}

	public boolean fulfills(Collection<String> fufilledRequirments) {
		return fufilledRequirments.containsAll(requirments);
	}

	public List<String> getRequirments() {
		return Collections.unmodifiableList(requirments);
	}

	public List<String> getValues(Map<String, String> reqValMap) {
		List<String> vals = new ArrayList<String>();
		for (String require : requirments) {
			vals.add(reqValMap.get(require));
		}
		return vals;
	}

	public void finalize() {
		if (finalized) { throw new IllegalStateException("Already finalized!"); }
		finalized = true;
	}

	public boolean isFinalized() {
		return finalized;
	}

	public String getType() {
		return schemaType;
	}

}
