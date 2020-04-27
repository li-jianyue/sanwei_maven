package com.koolearn.klibrary.text.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ZLVideoEntry {
	private final Map<String,String> mySources = new HashMap<String,String>();

	public void addSource(String mime, String path) {
		mySources.put(mime, path);
	}

	public Map<String,String> sources() {
		return Collections.unmodifiableMap(mySources);
	}
}
