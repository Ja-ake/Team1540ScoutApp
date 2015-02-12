package edu.team1540.recycle.user;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

public class Properties {
	private Map<String, String> entries = new HashMap<String, String>();
	
	public void load(String file, Context context) throws IOException {
		final byte[] bnames = new byte[10000];
		Arrays.fill(bnames, (byte) 0);
		final AssetManager am = context.getAssets();
		final InputStream input = am.open(file);
		for (int i=0; i<bnames.length; i++) {
			final int b = input.read();
			if (b == -1) break;
			bnames[i] = (byte) b;
		}
		input.close();
		
		int i;
		for (i=0; i<bnames.length; i++) if (bnames[i] == -1) break;
		final String[] lines = new String(bnames, 0, i).split("\\r?\\n");
		for (String s : lines) {
			String[] keyval = s.split(",");
			if (keyval[0].equalsIgnoreCase("id")) entries.put(keyval[2], keyval[1]);
		}
		
		for (Entry<String, String> entry : entries.entrySet()) {
			Log.i("PROP", entry.getKey() + "\t" + entry.getValue());
		}
	}
	
	public String getProperty(String identifier) {
		return entries.get(identifier);
	}
}
