package com.mysql.common.dao;

import java.io.InputStream;
import java.util.Properties;

@SuppressWarnings("serial")
public class Env extends Properties {
	private static Env instance;

	public static Env getInstance() {
		if (instance != null) {
			return instance;
		} else {
			makeInstance();
			return instance;
		}
	}

	public static synchronized void makeInstance() {
		if (instance == null) {
			instance = new Env();
		}
	}

	private Env() {
		InputStream is = getClass().getResourceAsStream("/db.properties");
		try {
			load(is);
		} catch (Exception e) {
			System.err.println("无法加载db.properties文件!");
		}
	}
}
