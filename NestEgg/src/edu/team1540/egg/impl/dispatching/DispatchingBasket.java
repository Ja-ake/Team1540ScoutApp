package edu.team1540.egg.impl.dispatching;

import java.util.concurrent.atomic.AtomicReference;

import edu.team1540.egg.core.FragmentBasket;
import edu.team1540.egg.util.coms.bluetooth.ComBridge;
import edu.team1540.egg.util.coms.dispatch.Dispatch;
import edu.team1540.egg.util.coms.dispatch.DispatchSchema;

public class DispatchingBasket extends FragmentBasket {

	public DispatchingBasket(final ComBridge bridge, final DispatchSchema schema, final BasketDrawCallbacks a, final String title, final DispatchingFragment... fragments) {
		super(a, title, fragments);
		final AtomicReference<Dispatch> currentDispatch = new AtomicReference<Dispatch>(new Dispatch(schema));
		for (final DispatchingFragment dispatchFragment : fragments) {
			dispatchFragment.init(currentDispatch, bridge);
		}
	}

}
