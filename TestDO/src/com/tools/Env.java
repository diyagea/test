package com.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tools.JSONFileUtil;

/*
 * 全局变量类
 */
public class Env extends Properties {

	private static final long serialVersionUID = -9146416339188184812L;

	protected static Logger logger = LogManager.getLogger(Env.class);

	private static Env instance;

	/*
	 * 从配置文件中获取参数
	 */
	public static synchronized String get(String key) {
		if (instance == null) {
			instance = new Env();
		}
		return instance.getProperty(key);
	}

	private Env() {
		InputStream is = getClass().getResourceAsStream("/env.properties");
		try {
			BufferedReader bf = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			load(bf);
		} catch (Exception e) {
			logger.error("无法加载env.properties文件!", e);
		}

		File tempFile = new File(TEMP_FOLDER_PATH);
		if (tempFile.exists() == false) {
			tempFile.mkdirs();// 多级目录
		}
	}

	/********* HISP日期处理 *********/
	public static final String[] DATE_PATTERN_YMDHMS = new String[] { "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss" };
	public static final String[] DATE_PATTERN_YMD = new String[] { "yyyyMMdd", "yyyy-MM-dd", "yyyy/MM/dd", "yyyy年MM月dd日" };
	public static final String[] DATE_PATTERN_YM = new String[] { "yyyy-MM", "yyyyMM", "yyyy/MM" };
	/********* HISP日期处理 *********/

	/********* HISP哈他信息系统平台 *********/
	/* 数据存放文件夹 */
	public static final String DATA_FOLDER_PATH = JSONFileUtil.class.getResource("/").getPath() + "data";
	/* 数据存放文件夹 */
	public static final String TEMP_FOLDER_PATH = JSONFileUtil.class.getResource("/").getPath() + "temp";


}
