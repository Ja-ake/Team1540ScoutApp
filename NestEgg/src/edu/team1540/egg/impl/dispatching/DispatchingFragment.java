package edu.team1540.egg.impl.dispatching;

import java.util.concurrent.atomic.AtomicReference;

import org.team1540.common.core.schema.Schema;
import org.team1540.common.system.ConnectedThread;

import edu.team1540.egg.core.ScoutingFragment;

public abstract class DispatchingFragment<S extends Schema> extends ScoutingFragment {

	private ConnectedThread c=null;
	private AtomicReference<S> schema;

	public DispatchingFragment(final int layoutID) {
		super(layoutID);
	}

	protected void setup(ConnectedThread c, AtomicReference<S> schema){
		this.c=c;
		this.schema=schema;
	}

	public S getSchema(){
		return schema.get();
	}

	public void setSchema(S s){
		schema.set(s);
	}

	public void submitSchema(){
		c.sendMessage(Schema.serilizeSchema(schema.get()), null);
	}
}
