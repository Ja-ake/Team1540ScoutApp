package org.team1540.incubator.handler;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.team1540.common.core.cache.CachedList;
import org.team1540.common.core.pattern.Callback;
import org.team1540.common.core.schema.impl.StandSchema;

public class StandHandler implements Callback<StandSchema> {
	public final List<StandSchema> recivedStandData;

	public StandHandler() {
		try {
			recivedStandData = new CachedList<StandSchema>("stand", new File(".", "resources"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void callback(StandSchema value) {
		recivedStandData.add(value);
		System.out.println(recivedStandData);
	}
}
