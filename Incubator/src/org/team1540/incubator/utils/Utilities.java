package org.team1540.incubator.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utilities {

	@SuppressWarnings("unchecked")
	public static String[] merge(String conjunction, String[]... toMerge) {
		List<String>[] toMergeLists = new List[toMerge.length];
		for (int index = 0; index < toMerge.length; index++) {
			toMergeLists[index] = Arrays.asList(toMerge[index]);
		}
		return merge(conjunction, toMergeLists);
	}

	public static String[] merge(String conjunction, List<String>... toMerge) {
		final int size = toMerge[0].size();
		final String[] toReturn = new String[size];
		for (int index = 0; index < size; index++) {
			List<String> toMergeForList = new ArrayList<String>();
			for (List<String> l : toMerge) {
				toMergeForList.add(l.get(index));
			}
			toReturn[index] = join(conjunction, toMergeForList);
		}
		return toReturn;
	}

	public static String join(String conjunction, Iterable<String> list) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (String item : list) {
			if (first) first = false;
			else sb.append(conjunction);
			sb.append(item);
		}
		return sb.toString();
	}
}
