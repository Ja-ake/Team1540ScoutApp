package org.team1540.incubator.handler;

import org.team1540.common.core.pattern.Callback;
import org.team1540.common.core.schema.impl.TestSchema;

public class TestHandler implements Callback<TestSchema> {
	@Override
	public void callback(final TestSchema value) {
		System.out.println(value);
	}
}
