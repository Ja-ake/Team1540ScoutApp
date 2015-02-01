package org.team1540.common.core.schema;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.team1540.common.core.bluetooth.Address;

public abstract class Schema implements Serializable {
	private static final long serialVersionUID = 2L;

	public Address from = null;

	public static String serilizeSchema(final Schema s) {
		try {
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			final ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(s);
			oos.close();
			return new String(Base64Coder.encode(baos.toByteArray()));
		} catch (final IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static Schema deserilizeSchema(final String s) {
		try {
			final byte[] data = Base64Coder.decode(s);
			final ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
			final Object o = ois.readObject();
			ois.close();
			return (Schema) o;
		} catch (final IOException e) {
			throw new IllegalArgumentException(e);
		} catch (final ClassNotFoundException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
