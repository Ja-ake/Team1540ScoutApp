package edu.team1540.egg.impl.dispatching;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReference;

import android.util.Log;
import edu.team1540.egg.core.ScoutingFragment;
import edu.team1540.egg.util.coms.bluetooth.ComBridge;
import edu.team1540.egg.util.coms.dispatch.Dispatch;
import edu.team1540.egg.util.coms.dispatch.DispatchSchema;
import edu.team1540.egg.util.coms.dispatch.Gatherer;

public abstract class DispatchingFragment extends ScoutingFragment {
	private AtomicReference<Dispatch> dispatch;
	private ComBridge bridge;

	public DispatchingFragment(final int layoutID) {
		super(layoutID);
	}

	public final void init(final AtomicReference<Dispatch> dispatch, final ComBridge bridge) {
		this.dispatch = dispatch;
		this.bridge = bridge;
	}

	public boolean submitDispatch() {
		return submitDispatch(getDispatcher().schema);
	}

	public boolean submitDispatch(final DispatchSchema schema) {
		try {
			bridge.queueMessage(getDispatcher().toMessage());
			setDispatcher(new Dispatch(schema));
			return true;
		} catch (final IllegalStateException e) {
			Log.i("NEST_EGG", e.toString());
			return false;
		}
	}

	public void patchDispatch(final String requirment, final String value) {
		getDispatcher().patch(requirment, value);
	}

	public void patchDispatch(final String requirment, final Gatherer g) {
		getDispatcher().patch(requirment, g);
	}

	public void patchDispatch(final Map<String, Gatherer> toPatch) {
		for (final Entry<String, Gatherer> patching : toPatch.entrySet()) {
			patchDispatch(patching.getKey(), patching.getValue());
		}
	}

	public boolean tryFlushComBridge() {
		return bridge.attemptClearBacklog();
	}

	public void launchFlushThread() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					final boolean result = tryFlushComBridge();
					Log.i("NEST_EGG", "flushed:" + result);
				} catch (final Exception e) {
					Log.i("NEST_EGG", e.toString());
					throw new RuntimeException(e);
				}
			}
		}).start();
	}

	public Dispatch getDispatcher() {
		return dispatch.get();
	}

	public Dispatch setDispatcher(final Dispatch d) {
		return dispatch.getAndSet(d);
	}
}
