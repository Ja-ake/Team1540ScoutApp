package edu.team1540.egg.impl.dispatching;

import java.util.concurrent.atomic.AtomicReference;

import edu.team1540.egg.core.FragmentBasket;
import edu.team1540.egg.util.coms.bluetooth.ComBridge;
import edu.team1540.egg.util.coms.dispatch.Dispatch;
import edu.team1540.egg.util.coms.dispatch.DispatchSchema;

public class DispatchingBasket extends FragmentBasket {

	public DispatchingBasket(ComBridge bridge, DispatchSchema schema, BasketDrawCallbacks a, String title, DispatchingFragment... fragments) {
		super(a, title, fragments);
		AtomicReference<Dispatch> currentDispatch = new AtomicReference<Dispatch>(new Dispatch(schema));
		for (DispatchingFragment dispatchFragment : fragments) {
			dispatchFragment.init(currentDispatch, bridge);
		}
	}

}
