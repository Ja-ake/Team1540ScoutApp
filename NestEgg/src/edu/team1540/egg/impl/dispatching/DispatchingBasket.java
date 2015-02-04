package edu.team1540.egg.impl.dispatching;

import java.util.concurrent.atomic.AtomicReference;

import org.team1540.common.core.schema.Schema;
import org.team1540.common.system.ConnectedThread;

import edu.team1540.egg.core.FragmentBasket;

public class DispatchingBasket<S extends Schema> extends FragmentBasket {

	public DispatchingBasket(final ConnectedThread c, final BasketDrawCallbacks a, final String title, final DispatchingFragment<S>... fragments) {
		super(a, title, fragments);
		final AtomicReference<S> currentSchema = new AtomicReference<S>(null);
		for (final DispatchingFragment<S> dispatchFragment : fragments) {
			dispatchFragment.setup(c, currentSchema);
		}
	}

}
