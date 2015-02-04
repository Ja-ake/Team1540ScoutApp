package org.team1540.common.core.pattern;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicCallback<T> implements Callback<T> {
	public final AtomicReference<T> callbackValue = new AtomicReference<T>();

	@Override
	public void callback(final T value) {
		synchronized (callbackValue) {
			callbackValue.set(value);
			callbackValue.notifyAll();
		}
	}
}
