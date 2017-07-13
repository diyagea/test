package com.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;

public class SimpleDate {
	public String getSimpleDateYM() {
		FastDateFormat s = FastDateFormat.getInstance("yyyy年MM月");
		Date date = new Date();
		return s.format(date);
	}

	public String getSimpleDateYM2() {
		FastDateFormat s = FastDateFormat.getInstance("yyyy-MM");
		Date date = new Date();
		return s.format(date);
	}

	public String getSimpleDateY() {
		FastDateFormat s = FastDateFormat.getInstance("yyyy");
		Date date = new Date();
		return s.format(date);
	}

	public String getSimpleDateM() {
		FastDateFormat s = FastDateFormat.getInstance("MM");
		Date date = new Date();
		return s.format(date);
	}

	public String getSimpleDateD() {
		FastDateFormat s = FastDateFormat.getInstance("dd");
		Date date = new Date();
		return s.format(date);
	}

	public String getSimpleDateYMNum() {
		FastDateFormat s = FastDateFormat.getInstance("yyyyMM");
		Date date = new Date();
		return s.format(date);
	}

	public String getSimpleDateMD() {
		FastDateFormat s = FastDateFormat.getInstance("-MM-dd");
		Date date = new Date();
		return s.format(date);
	}

	public String getSimpleDateYMDZh() {
		FastDateFormat s = FastDateFormat.getInstance("yyyy年MM月dd日");
		Date date = new Date();
		return s.format(date);
	}

	public String getSimpleDateYMD() {
		FastDateFormat s = FastDateFormat.getInstance("yyyy-MM-dd");
		Date date = new Date();
		return s.format(date);
	}

	public String getSimpleDateHMS() {
		FastDateFormat s = FastDateFormat.getInstance("HH:mm:ss");
		Date date = new Date();
		return s.format(date);
	}

	public String getSimpleDateHM() {
		FastDateFormat s = FastDateFormat.getInstance("HH:mm");
		Date date = new Date();
		return s.format(date);
	}

	public String getSimpleDateYMDHMS() {
		FastDateFormat s = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		return s.format(date);
	}

	public String getSimpleDateYMDHMS2() {
		FastDateFormat s = FastDateFormat.getInstance("yyyy年MM月dd日 HH:mm:ss");
		Date date = new Date();
		return s.format(date);
	}

	public String getSimpleDateYMDHMSS() {
		FastDateFormat s = FastDateFormat.getInstance("yyyyMMddHHmmssSSS");
		Date date = new Date();
		return s.format(date);
	}

	// public static void main(String[] args) {
	// @SuppressWarnings("unused")
	// SimpleDate sd = new SimpleDate();
	// }

	/**
	 * 日期格式字符串转换成时间戳
	 * 
	 * @param date
	 *            字符串日期
	 * @param format
	 *            如：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static long dateStrToTimeStamp(String dateStr) {
		try {
			return DateUtils.parseDate(dateStr, Env.DATE_PATTERN_YMDHMS).getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/* 根据传入的日期（2016-03-04）返回当月的天数 */
	public int getNumOfDaysInMounth(String date) {

		// 2016-03-04
		String[] str = date.split("-");

		int year = Common.objectToInt(str[0]);
		int mounth = Common.objectToInt(str[1]);

		int[] monDays = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		if (((year) % 4 == 0 && (year) % 100 != 0) || (year) % 400 == 0)
			monDays[1]++;

		return monDays[--mounth];
	}

	/**
	 * 判断时间相差秒
	 * 
	 * @return
	 * @throws IllegalArgumentException
	 */
	public long getTime(String sourceTime1, String sourceTime2) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		try {
			long start = sdf.parse(sourceTime1).getTime();
			long end = sdf.parse(sourceTime2).getTime();
			return end - start;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public long getTime2(String sourceTime1, String sourceTime2) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			long start = sdf.parse(sourceTime1).getTime();
			long end = sdf.parse(sourceTime2).getTime();
			return end - start;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 判断当前时间是否在一个区间内
	 * 
	 * @param sourceTime
	 *            时间区间,半闭合,如[10:00-20:00)
	 * @param curTime
	 *            需要判断的时间 如10:00
	 * @return
	 * @throws IllegalArgumentException
	 */
	public boolean isInTime(String sourceTime, String curTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		try {
			String[] args = sourceTime.split("-");
			long now = sdf.parse(curTime).getTime();
			long start = sdf.parse(args[0]).getTime();
			long end = sdf.parse(args[1]).getTime();
			if (now >= start && now < end) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
