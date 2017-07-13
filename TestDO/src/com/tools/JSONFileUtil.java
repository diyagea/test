package com.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @Title: HISP
 * @Description: 文件操作工具类
 * @Company: 哈他网络科技
 * @Author: xumeng
 * @Date: 2016-5-16
 */
public class JSONFileUtil {

	/**
	 * 把json数据写入文件
	 * 
	 * @param json
	 * @param fileName
	 */
	public synchronized static void write2File(Object json, String fileName) {
		BufferedWriter writer = null;
		File filePath = new File(Env.DATA_FOLDER_PATH);
		JSONObject eJSON = null;

		if (!filePath.exists() && !filePath.isDirectory()) {
			filePath.mkdirs();
		}

		File file = new File(Env.DATA_FOLDER_PATH + File.separator + fileName);
		// System.out.println("path:" + file.getPath() + " abs path:" +
		// file.getAbsolutePath());
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				// System.out.println("createNewFile，出现异常:");
				e.printStackTrace();
			}
		} else {
			eJSON = (JSONObject) read2JSON(fileName);
		}

		try {
			writer = new BufferedWriter(new FileWriter(file));

			if (eJSON == null) {
				writer.write(json.toString());
			} else {
				Object[] array = ((JSONObject) json).keySet().toArray();
				for (int i = 0; i < array.length; i++) {
					eJSON.put(array[i].toString(), ((JSONObject) json).get(array[i].toString()));
				}

				writer.write(eJSON.toString());
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 读文件到json
	 * 
	 * @param fileName
	 * @return
	 */
	public static JSONObject read2JSON(String fileName) {
		File file = new File(Env.DATA_FOLDER_PATH + File.separator + fileName);
		if (!file.exists()) {
			return null;
		}

		BufferedReader reader = null;
		String laststr = "";
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				laststr += tempString;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return (JSONObject) JSON.parse(laststr);
	}

	/**
	 * 通过key值获取文件中的value
	 * 
	 * @param fileName
	 * @param key
	 * @return
	 */
	public static Object getValue(String fileName, String key) {
		JSONObject eJSON = null;
		eJSON = (JSONObject) read2JSON(fileName);
		if (null != eJSON && eJSON.containsKey(key)) {
			@SuppressWarnings("unchecked")
			Map<String, Object> values = JSON.parseObject(eJSON.toString(), Map.class);
			return values.get(key);
		} else {
			return null;
		}
	}

	/**
	 * json转map
	 * 
	 * @param js
	 * @return
	 */
	public static HashMap<Long, Long> toHashMap(JSONObject js) {
		if (js == null) {
			return null;
		}
		HashMap<Long, Long> data = new HashMap<Long, Long>();
		// 将json字符串转换成jsonObject
		Set<String> set = js.keySet();
		// 遍历jsonObject数据，添加到Map对象
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String key = String.valueOf(it.next());
			Long keyLong = Long.valueOf(key);

			String value = js.getString(key);
			Long valueLong;
			if ("".equals(value) || value == null) {
				valueLong = js.getLong(key);
			} else {
				valueLong = Long.valueOf(value);
			}
			data.put(keyLong, valueLong);
		}
		return data;
	}

	// /**
	// * 把json数据写入文件
	// *
	// * @param json
	// * @param fileName
	// */
	// public synchronized static void writeArrayToFile(JSONArray json, String
	// fileName) {
	// BufferedWriter writer = null;
	// File filePath = new File(FILEPATH);
	// JSONArray eJSON = null;
	//
	// if (!filePath.exists() && !filePath.isDirectory()) {
	// filePath.mkdirs();
	// }
	// File file = new File(FILEPATH + File.separator + fileName);
	//
	// if (!file.exists()) {
	// try {
	// file.createNewFile();
	// } catch (Exception e) {
	// // System.out.println("createNewFile，出现异常:");
	// e.printStackTrace();
	// }
	// } else {
	// eJSON = (JSONArray) readToArray(fileName);
	// }
	//
	// try {
	// writer = new BufferedWriter(new FileWriter(file));
	//
	// if (eJSON == null) {
	// writer.write(json.toString());
	// } else {
	// Object[] array = ((JSONObject) json).keySet().toArray();
	// for (int i = 0; i < array.length; i++) {
	// eJSON.put(array[i].toString(), ((JSONObject)
	// json).get(array[i].toString()));
	// }
	//
	// writer.write(eJSON.toString());
	// }
	//
	// } catch (IOException e) {
	// e.printStackTrace();
	// } finally {
	// try {
	// if (writer != null) {
	// writer.close();
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	//
	// /**
	// * 读文件到json
	// *
	// * @param fileName
	// * @return
	// */
	// public static JSONArray readToArray(String fileName) {
	// File file = new File(FILEPATH + File.separator + fileName);
	// if (!file.exists()) {
	// return null;
	// }
	//
	// BufferedReader reader = null;
	// String laststr = "";
	// try {
	// reader = new BufferedReader(new FileReader(file));
	// String tempString = null;
	// while ((tempString = reader.readLine()) != null) {
	// laststr += tempString;
	// }
	// reader.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// return (JSONArray) JSONArray.parse(laststr);
	// }
}