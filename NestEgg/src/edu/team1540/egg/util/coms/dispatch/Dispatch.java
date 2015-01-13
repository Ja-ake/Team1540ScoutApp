package edu.team1540.egg.util.coms.dispatch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.text.TextUtils;
import edu.team1540.egg.util.coms.bluetooth.Message;

public class Dispatch {
	public final DispatchSchema schema;
	private final Map<String, String> requirmentValueMap;
	private final Map<String, Gatherer> gatherers = new HashMap<String, Gatherer>();

	public Dispatch(final DispatchSchema schema) {
		this(schema, new HashMap<String, String>());
	}

	public Dispatch(final DispatchSchema schema, final Map<String, String> startingValues) {
		if (!schema.isFinalized()) {
			throw new IllegalStateException("Unfinalized schema!");
		}
		this.schema = schema;
		requirmentValueMap = startingValues;
	}

	public void patch(final String requirment, final String value) {
		requirmentValueMap.put(requirment, value);
	}

	public void patch(final String requirment, final Gatherer g) {
		gatherers.put(requirment, g);
		requirmentValueMap.put(requirment, g.gather());
	}

	public boolean ready() {
		return schema.fulfills(requirmentValueMap.keySet());
	}

	public Message toMessage() {
		// Update gatherers
		for (final String req : gatherers.keySet()) {
			requirmentValueMap.put(req, gatherers.get(req).gather());
		}
		// Check if it now fulfills the schema
		if (!ready()) {
			throw new IllegalStateException("Requirments not fulfilled!");
		}
		final String encodedMessage = TextUtils.join("\n", Dispatch.encode(schema.getRequirments(), schema.getValues(requirmentValueMap)));
		return new Message(schema.getType(), encodedMessage);
	}

	public static String encode(final String requirment, final String value) {
		return requirment + ":" + value;
	}

	public static String[] encode(final Collection<String> requirment, final Collection<String> value) {
		final Iterator<String> requirments = requirment.iterator();
		final Iterator<String> values = value.iterator();
		final List<String> encodedPairs = new ArrayList<String>();
		while (requirments.hasNext()) {
			encodedPairs.add(Dispatch.encode(requirments.next(), values.next()));
		}
		String[] pairArray = new String[encodedPairs.size()];
		pairArray = encodedPairs.toArray(pairArray);
		return pairArray;
	}
}
