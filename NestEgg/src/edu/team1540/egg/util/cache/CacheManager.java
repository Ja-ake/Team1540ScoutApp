package edu.team1540.egg.util.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.content.Context;

public class CacheManager<T extends Serializable> {

	private final File location;
	private Set<T> cacheItems;
	private Set<T> frozenItems;

	@SuppressWarnings("unchecked")
	public CacheManager(final Activity a, final String cacheName) {
		location = new File(a.getDir("cacheManager", Context.MODE_PRIVATE), cacheName);
		try {
			final File setFile = new File(location, "set");

			try {
				cacheItems = (HashSet<T>) new ObjectInputStream(new FileInputStream(setFile)).readObject();
			} catch (final OptionalDataException e) {
				throw new IOException(e);
			} catch (final StreamCorruptedException e) {
				throw new IOException(e);
			} catch (final ClassNotFoundException e) {
				throw new IOException(e);
			}
		} catch (final IOException e) {
			cacheItems = new HashSet<T>();
		}
		try {
			final File frozenFile = new File(location, "frozen");
			try {
				frozenItems = (HashSet<T>) new ObjectInputStream(new FileInputStream(frozenFile)).readObject();
			} catch (final OptionalDataException e) {
				throw new IOException(e);
			} catch (final StreamCorruptedException e) {
				throw new IOException(e);
			} catch (final ClassNotFoundException e) {
				throw new IOException(e);
			}
		} catch (final IOException e) {
			frozenItems = new HashSet<T>();
		}
	}

	public synchronized boolean cacheEmpty() {
		return cacheItems.isEmpty();
	}

	public synchronized boolean cache(final T t) {
		return doCacheOp(new SetOp<T>() {
			@Override
			public void doOp(final Set<T> s) {
				s.add(t);
			}
		});
	}

	public synchronized boolean freeze(final T t) {
		return doFrozenOp(new SetOp<T>() {
			@Override
			public void doOp(final Set<T> s) {
				s.add(t);
			}
		});
	}

	public synchronized boolean unCache(final T t) {
		return doCacheOp(new SetOp<T>() {
			@Override
			public void doOp(final Set<T> s) {
				s.remove(t);
			}
		});
	}

	public synchronized boolean unFreeze(final T t) {
		return doFrozenOp(new SetOp<T>() {
			@Override
			public void doOp(final Set<T> s) {
				s.remove(t);
			}
		});
	}

	public synchronized T getCached() {
		if (!cacheItems.isEmpty()) {
			return cacheItems.iterator().next();
		}
		return null;
	}

	public synchronized T getFrozen() {
		if (!frozenItems.isEmpty()) {
			return frozenItems.iterator().next();
		}
		return null;
	}

	public synchronized int numCached() {
		return cacheItems.size();
	}

	public synchronized int numFrozen() {
		return frozenItems.size();
	}

	private boolean doFrozenOp(final SetOp<T> op) {
		return CacheManager.doOnCachedSet(frozenItems, op, "frozen", location);
	}

	private boolean doCacheOp(final SetOp<T> op) {
		return CacheManager.doOnCachedSet(cacheItems, op, "set", location);
	}

	private static <T> boolean doOnCachedSet(final Set<T> old, final SetOp<T> o, final String set, final File location) {
		final Set<T> newSet = new HashSet<T>(old);
		o.doOp(newSet);
		final File frozenFile = new File(location, set);
		try {
			new ObjectOutputStream(new FileOutputStream(frozenFile)).writeObject(newSet);
		} catch (final FileNotFoundException e) {
			return false;
		} catch (final IOException e) {
			return false;
		}
		old.clear();
		old.addAll(newSet);
		return true;

	}

	private interface SetOp<T> {
		public void doOp(Set<T> s);
	}
}
