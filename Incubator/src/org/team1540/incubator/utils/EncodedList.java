package org.team1540.incubator.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("serial")//This class is not designed to be serialized
public final class EncodedList<T> extends ArrayList<T>{
	private final File backup;

	@SuppressWarnings("unchecked")
	public EncodedList(File f){
		backup=f;
		ObjectInputStream i=null;
		try{
			i=new ObjectInputStream(new FileInputStream(f));
			List<T> val;
			try {
				val = (List<T>)i.readObject();
			} catch (ClassNotFoundException e) {
				throw new IOException(e);
			}
			super.addAll(val);
		} catch (IOException e) {

		}finally{
			if(i!=null){
				try {
					i.close();
				} catch (IOException e){

				}
			}
		}
	}

	public boolean refreshBackup(){
		ObjectOutputStream s=null;
		try{
			s=new ObjectOutputStream(new FileOutputStream(backup));
			s.writeObject(this);
			return true;
		}catch (IOException e) {
			return false;
		}finally{
			if(s!=null){
				try{
					s.close();
				} catch (IOException e) {}
			}
		}
	}


	@Override
	public boolean add(T arg0) {
		boolean ret=super.add(arg0);
		refreshBackup();
		return ret;
	}

	@Override
	public void add(int arg0, T arg1) {
		super.add(arg0,arg1);
		refreshBackup();
	}

	@Override
	public boolean addAll(Collection<? extends T> arg0) {
		boolean ret=super.addAll(arg0);
		refreshBackup();
		return ret;
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends T> arg1) {
		boolean ret=super.addAll(arg0,arg1);
		refreshBackup();
		return ret;
	}

	@Override
	public void clear() {
		super.clear();
		refreshBackup();
	}

	@Override
	public boolean remove(Object arg0) {
		boolean ret=super.remove(arg0);
		refreshBackup();
		return ret;
	}

	@Override
	public T remove(int arg0) {
		T ret=super.remove(arg0);
		refreshBackup();
		return ret;
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		boolean ret=super.removeAll(arg0);
		refreshBackup();
		return ret;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		boolean ret=super.retainAll(arg0);
		refreshBackup();
		return ret;
	}

	@Override
	public T set(int arg0, T arg1) {
		T ret=super.set(arg0, arg1);
		refreshBackup();
		return ret;
	}
}
