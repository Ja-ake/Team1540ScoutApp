package edu.team1540.egg.impl.dispatching;

import java.util.concurrent.atomic.AtomicReference;

import org.team1540.common.core.bluetooth.Address;
import org.team1540.common.core.pattern.AtomicCallback;
import org.team1540.common.core.schema.Schema;
import org.team1540.common.system.ConnectedThread;
import org.team1540.common.system.ConnectionHandler;

import edu.team1540.egg.core.FragmentBasket;

public class DispatchingBasket<S extends Schema> extends FragmentBasket{

	public DispatchingBasket(final Address address,final ConnectionHandler h, S start, final BasketDrawCallbacks a, final String title, final DispatchingFragment<S>... fragments) {
		super(a, title, fragments);
		final AtomicReference<S> currentSchema = new AtomicReference<S>(start);
		AtomicCallback<ConnectedThread> callback=new AtomicCallback<ConnectedThread>();
		h.connectTo(address, callback);
		for (final DispatchingFragment<S> dispatchFragment : fragments) {
			dispatchFragment.setup(callback.callbackValue, currentSchema);
		}
	}

}
