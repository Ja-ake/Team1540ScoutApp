package org.team1540.common.core.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CachedList<T extends Serializable> implements List<T> {
	private final File target;

	private final List<T> cache;

	@SuppressWarnings("unchecked")
	public CachedList(String s, File cacheDirectory) throws FileNotFoundException, IOException, ClassNotFoundException {
		target = new File(cacheDirectory, s);
		if (target.exists()) {
			cache = (List<T>) new ObjectInputStream(new FileInputStream(target)).readObject();
		} else {
			cache = new ArrayList<T>();
		}
	}

	public void rewriteCache() {
		FileOutputStream fio = null;
		ObjectOutputStream oos = null;
		try {
			File backup = new File(target.getParentFile(), target.getName() + "_backup");
			backup.delete();
			target.renameTo(backup);
			target.delete();
			fio = new FileOutputStream(target);
			oos = new ObjectOutputStream(fio);
			oos.writeObject(cache);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (fio != null) {
				try {
					fio.close();
				} catch (IOException e) {}
			}
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {}
			}
		}

	}

	@Override
	public boolean add(T arg0) {
		boolean result = cache.add(arg0);
		rewriteCache();
		return result;
	}

	@Override
	public void add(int arg0, T arg1) {
		cache.add(arg0, arg1);
		rewriteCache();
	}

	@Override
	public boolean addAll(Collection<? extends T> arg0) {
		boolean result = cache.addAll(arg0);
		rewriteCache();
		return result;
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends T> arg1) {
		boolean result = cache.addAll(arg0, arg1);
		rewriteCache();
		return result;
	}

	@Override
	public void clear() {
		cache.clear();
		rewriteCache();
	}

	@Override
	public boolean contains(Object arg0) {
		return cache.contains(arg0);
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		return cache.containsAll(arg0);
	}

	@Override
	public T get(int arg0) {
		return cache.get(arg0);
	}

	@Override
	public int indexOf(Object arg0) {
		return cache.indexOf(arg0);
	}

	@Override
	public boolean isEmpty() {
		return cache.isEmpty();
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			Iterator<T> backing = cache.iterator();

			@Override
			public boolean hasNext() {
				return backing.hasNext();
			}

			@Override
			public T next() {
				return backing.next();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}

		};
	}

	@Override
	public int lastIndexOf(Object arg0) {
		return cache.lastIndexOf(arg0);
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object arg0) {
		boolean result = cache.remove(arg0);
		rewriteCache();
		return result;
	}

	@Override
	public T remove(int arg0) {
		T result = cache.remove(arg0);
		rewriteCache();
		return result;
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		boolean result = cache.removeAll(arg0);
		rewriteCache();
		return result;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		boolean result = cache.retainAll(arg0);
		rewriteCache();
		return result;
	}

	@Override
	public T set(int arg0, T arg1) {
		T result = cache.set(arg0, arg1);
		rewriteCache();
		return result;
	}

	@Override
	public int size() {
		return cache.size();
	}

	@Override
	public List<T> subList(int arg0, int arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object[] toArray() {
		return cache.toArray();
	}

	@Override
	public <T1> T1[] toArray(T1[] arg0) {
		return cache.toArray(arg0);
	}
}
