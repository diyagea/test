package com.tools;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Common {

	private static Logger logger = LogManager.getLogger(Common.class);

	private static BigDecimal big0 = new BigDecimal(0);

	public static boolean checkNull(Object obj) {

		boolean nullFlag = false;

		// String objStr = obj.toString();

		if (obj == null || "".equals(obj) || " ".equals(obj)) {
			nullFlag = true;
		}
		return nullFlag;
	}

	public static String changeNull(Object obj) {

		if (obj == null) {
			obj = "";
		}
		return obj.toString();
	}

	public static int maxCount(int count1, int count2) {

		if (count1 >= count2) {
			return count1;
		} else {
			return count2;
		}
	}

	public static int strToInt(String str) {

		if (checkNull(str)) {
			return 0;
		} else {
			try {
				return Integer.parseInt(str);
			} catch (Exception e) {
				logger.error("错误信息：", e);
				return 0;
			}

		}
	}

	public static int objectToIntWithDefault(Object obj, int def) {

		if (checkNull(obj)) {
			return def;
		} else {
			try {
				return Integer.parseInt(obj.toString());
			} catch (Exception e) {
				// logger.error("错误信息：", e);
				return def;
			}

		}
	}

	public static int objectToInt(Object obj) {

		if (checkNull(obj)) {
			return 0;
		} else {
			try {
				return Integer.parseInt(obj.toString());
			} catch (Exception e) {
				logger.error("错误信息：", e);
				return 0;
			}

		}
	}

	public static long objectToLongWithDefault(Object obj, long def) {

		if (checkNull(obj)) {
			return def;
		} else {
			try {
				return Long.parseLong(obj.toString());
			} catch (Exception e) {
				logger.error("错误信息：", e);
				return def;
			}

		}
	}

	public static long objectToLong(Object obj) {

		if (checkNull(obj)) {
			return 0;
		} else {
			try {
				return Long.parseLong(obj.toString());
			} catch (Exception e) {
				logger.error("错误信息：", e);
				return 0;
			}

		}
	}

	/**
	 * 把输入字符串的首字母改成大写
	 * 
	 * @param str
	 * @return
	 */
	public static String initcap(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		return new String(ch);
	}

	/**
	 * 生成上传图片的随机不重复名字
	 * 
	 * @param 原始图片名称
	 * @return name 随机名字
	 */
	public static String makeImgName(String dstName) {
		String name = "";
		SimpleDate sd = new SimpleDate();
		String fileName = String.valueOf(Calendar.getInstance().getTimeInMillis()) + sd.getSimpleDateYMDHMSS();
		String extName = dstName.substring(dstName.lastIndexOf("."));
		if (extName.equals(".JPG")) {
			extName = ".jpg";
		}
		if (extName.equals(".PNG")) {
			extName = ".png";
		}
		if (extName.equals(".BMP")) {
			extName = ".bmp";
		}
		if (extName.equals(".GIF")) {
			extName = ".gif";
		}
		if (extName.equals(".JPEG")) {
			extName = ".jpeg";
		}
		if (extName.equals(".Jpg")) {
			extName = ".jpg";
		}
		if (extName.equals(".Png")) {
			extName = ".png";
		}
		if (extName.equals(".Bmp")) {
			extName = ".bmp";
		}
		if (extName.equals(".Gif")) {
			extName = ".gif";
		}
		if (extName.equals(".Jpeg")) {
			extName = ".jpeg";
		}
		name = fileName + extName;
		return name;
	}

	/**
	 * 获取count个随机数
	 * 
	 * @param count随机数个数
	 * @return
	 */
	public static String random(int count) {
		StringBuffer sb = new StringBuffer();
		String str = "0123456789";
		Random r = new Random();
		for (int i = 0; i < count; i++) {
			int num = r.nextInt(str.length());
			sb.append(str.charAt(num));
			str = str.replace((str.charAt(num) + ""), "");
		}
		return sb.toString();
	}

	/**
	 * 格式化BigDecimal formatType 0:原始 1：单位元两位小数 2：单位万元两位小数 3:单位万元8位小数4:单位万元无小数
	 * 
	 * @param bigDecimal
	 * @param formatType
	 * @return
	 * @return BigDecimal
	 */
	public static BigDecimal formatBigDecimal(BigDecimal bigDecimal, int formatType) {

		if (bigDecimal == null) {
			return new BigDecimal(0);
		}

		switch (formatType) {
		case 0:
			return bigDecimal;
		case 1:
			return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		case 2:
			return bigDecimal.divide(new BigDecimal("10000"), 2, BigDecimal.ROUND_HALF_UP);
		case 3:
			return bigDecimal.divide(new BigDecimal("10000"), 8, BigDecimal.ROUND_HALF_UP);
		case 4:
			return bigDecimal.divide(new BigDecimal("10000"), 0, BigDecimal.ROUND_HALF_UP);
		default:
			return bigDecimal;
		}
	}

	/**
	 * bigdecimal计算百分比
	 * 
	 * @param b1
	 * @param b2
	 * @return
	 * @return BigDecimal
	 */
	public static BigDecimal decimalPercent(BigDecimal b1, BigDecimal b2) {

		int comp = b2.compareTo(big0);

		if (comp == 0) {
			// 分母为零的情况返回0
			return new BigDecimal(0);
		} else {
			// 百分数
			BigDecimal percent;
			// 分母为负数的情况下
			if (comp < 0) {
				// 完成率＝（2－实际完成数/目标完成数）*100%=（200-实际完成数×100/目标完成数）%
				percent = new BigDecimal(200).subtract(b1.multiply(new BigDecimal(100)).divide(b2, 2, BigDecimal.ROUND_HALF_UP));
			} else {
				percent = b1.multiply(new BigDecimal(100)).divide(b2, 2, BigDecimal.ROUND_HALF_UP);
			}

			// 达成为负数则为0
			if (percent.compareTo(big0) <= 0) {
				return new BigDecimal(0);
			} else {
				return percent;
			}
		}
	}

	/**
	 * bigdecimal除法
	 * 
	 * @param b1
	 * @param b2
	 * @return
	 * @return BigDecimal
	 */
	public static BigDecimal decimalDivide(BigDecimal b1, BigDecimal b2, int scale) {

		if (b2.compareTo(new BigDecimal(0)) == 0) {
			return new BigDecimal(0);
		} else {
			return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
		}
	}

	/**
	 * 去重复字符串，返回最新字符串
	 * 
	 * @param oldRange
	 * @return
	 */
	public static String filterRange(String oldRange, String newRange) {

		// 如果范围为空情况下，直接返回新串
		if (Common.checkNull(oldRange)) {
			return newRange;
		}

		// 判断新id是否存在老id中如果存在则不添加
		StringTokenizer st = new StringTokenizer(newRange, ",", false);

		while (st.hasMoreElements()) {
			String userID = st.nextToken();
			// 不存在的情况下加上该用户
			if (oldRange.indexOf("," + userID + ",") < 0) {
				oldRange += userID + ",";
			}
		}
		return oldRange;
	}

	/** String去重 ,返回值不带前后逗号 */
	public static String delRepeat(String range) {

		if (Common.checkNull(range) || ",".equals(range)) {
			return "";
		}
		// set去重
		Set<String> idSet = new HashSet<String>();

		StringTokenizer st = new StringTokenizer(range, ",", false);
		while (st.hasMoreElements()) {
			String sID = st.nextToken();
			if (!"|".equals(sID)) {
				idSet.add(sID);
			}
		}

		if (idSet.size() > 0) {
			return setToStr(idSet);
		} else {
			return "";
		}
	}

	/**
	 * 字符串数组去重
	 * 
	 * @param arr
	 * @return
	 * @return String[]
	 */
	public static String[] delStringArrRepeat(String[] arr) {

		// arr 不是数组
		if (arr == null || arr.length == 0) {
			return arr;
		}

		List<String> list = Arrays.asList(arr);
		Set<String> set = new HashSet<String>(list);

		return set.toArray(new String[set.size()]);
	}

	/**
	 * 根据数组返回id串
	 * 
	 * @param arr
	 * @return
	 * @return String
	 */
	public static String arrToRange(String[] arr) {

		StringBuffer cBuff = new StringBuffer();

		if (arr != null && arr.length > 0) {
			Arrays.sort(arr);
			// 管理员设置
			for (int i = 0; i < arr.length; i++) {
				cBuff.append(arr[i] + ",");
			}
		}

		if (cBuff.length() > 0) {
			return "," + cBuff.toString();
		} else {
			return "";
		}
	}

	private static Date date = new Date();
	private static StringBuilder buf = new StringBuilder();
	private static int seq = 1;
	private static final int ROTATION = 99999;

	/**
	 * 唯一ID生成
	 * 
	 * @return
	 * @return long
	 */
	public static synchronized long next() {
		if (seq > ROTATION)
			seq = 1;
		buf.delete(0, buf.length());
		date.setTime(System.currentTimeMillis());
		String str = String.format("%1$tY%1$tm%1$td%1$tk%1$tM%1$tS%2$05d", date, seq++);
		return Long.parseLong(str);
	}

	/**
	 * set转成逗号分隔的字符串
	 * 
	 * @param set
	 * @return
	 * @return String
	 */
	public static String setToStr(Set<String> set) {

		if (set == null || set.size() == 0) {
			return "";
		}

		StringBuffer buff = new StringBuffer();

		// 遍历set生成id
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			buff.append(it.next() + ",");
		}

		// 去到最后一个逗号
		return buff.substring(0, buff.length() - 1);
	}

}
