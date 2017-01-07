package com.zegome.utils.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * (C) 2013 ZeroTeam
 * @author QuanLT
 */

public class XMLNode {
	private final String mParent;
	private final ArrayList<String> mKeys;
	private final Map<String, String> mValues;
	
	public XMLNode(final String parent, final ArrayList<String> keys) {
		mParent = parent;
		mKeys = keys;
		mValues = new HashMap<String, String>();
	}
	
	public String getParent() {
		return mParent;
	}
	
	public ArrayList<String> getAll() {
		return mKeys;
	}
	
	public String getValue(final String key) {
		return mValues.get(key);
	}
	
	public void setValue(final String key, final String value) {
		mValues.put(key, value);
	}
	
	public boolean check(final String key) {
		for (int i = mKeys.size() - 1; i >= 0; i--) {
			if (mKeys.get(i).equals(key)) return true;
		}
		return false;
	}
}
