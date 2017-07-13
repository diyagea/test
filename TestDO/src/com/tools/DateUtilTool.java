package com.tools;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DateUtilTool {

	private final static FastDateFormat SFYMD = FastDateFormat.getInstance("yyyy-MM-dd");
	private final static FastDateFormat SFYMDHMS = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
	private static Logger logger = LogManager.getLogger(DateUtilTool.class);

	/**
	 * 根据字符串返回当月有多少天
	 * 
	 * @param ym
	 * @return
	 * @return int
	 */
	public static int getDayNumOfMounthYM(String ym) {

		Calendar cal = Calendar.getInstance();
		try {
			// 字符串转日期函数
			cal.setTime(DateUtils.parseDate(ym, Env.DATE_PATTERN_YM));
			// 得到的dayc1就是你需要的calendar了
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cal.getActualMaximum(Calendar.DATE);
	}

	/**
	 * 根据小时总数返回几天几小时
	 * 
	 * @param hourTotal
	 * @return
	 * @return String
	 */
	public static String getTimeStrWithHours(int hourTotal) {

		StringBuffer timeBuff = new StringBuffer();

		int day = hourTotal / 24;
		int hour = hourTotal % 24;

		if (day != 0) {
			timeBuff.append(day + "天");
		}
		timeBuff.append(hour + "时");

		return timeBuff.toString();
	}

	/**
	 * 根据long返回 时间显示字符串
	 * 
	 * @param time
	 * @return
	 * @return String
	 */
	public static String getTimeStr(long time) {
		// 毫秒
		long mSec = time % 1000;
		time /= 1000;
		// 年
		long year = time / (365 * 24 * 3600);
		time = time % (365 * 24 * 3600);
		// 月份
		long month = time / (30 * 24 * 3600);
		time = time % (30 * 24 * 3600);
		// 天数
		long day = time / (24 * 3600);
		time = time % (24 * 3600);
		// 小时
		long hour = time / 3600;
		time = time % 3600;
		// 分钟
		long min = time / 60;
		time = time % 60;
		// 秒
		long sec = time;

		StringBuffer timeBuff = new StringBuffer();

		if (year != 0) {
			timeBuff.append(year + "年");
		}
		if (month != 0) {
			timeBuff.append(month + "月");
		}
		if (day != 0) {
			timeBuff.append(day + "天");
		}
		if (hour != 0) {
			timeBuff.append(hour + "时");
		}
		if (min != 0) {
			timeBuff.append(min + "分");
		}
		if (sec != 0) {
			timeBuff.append(sec + "秒");
		}
		if (mSec != 0) {
			// timeBuff.append(mSec + "豪秒");
		}
		if (timeBuff.length() == 0) {
			timeBuff.append("1秒内");
		}
		return timeBuff.toString();
	}

	public static List<String> findStrDates(String sDate, String eDate) {

		List<String> list = new ArrayList<String>();

		Date dBegin = null;
		Date dEnd = null;
		try {
			dBegin = DateUtils.parseDate(sDate, Env.DATE_PATTERN_YMD);
			dEnd = DateUtils.parseDate(eDate, Env.DATE_PATTERN_YMD);

			// 添加第一天
			list.add(sDate);

			// 使用给定的 Date 设置此 Calendar 的时间
			Calendar calBegin = Calendar.getInstance();
			calBegin.setTime(dBegin);

			// 使用给定的 Date 设置此 Calendar 的时间
			Calendar calEnd = Calendar.getInstance();
			calEnd.setTime(dEnd);

			// 测试此日期是否在指定日期之后
			while (calEnd.compareTo(calBegin) > 0) {
				// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
				calBegin.add(Calendar.DATE, 1);
				list.add(SFYMD.format(calBegin.getTime()));
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}

	/*
	 * 根据开始和结束时间 ，时间段的所有日期
	 */
	public static List<Date> findDates(String sDate, String eDate) {

		Date dBegin = null;
		Date dEnd = null;
		try {
			dBegin = DateUtils.parseDate(sDate, Env.DATE_PATTERN_YMD);
			dEnd = DateUtils.parseDate(eDate, Env.DATE_PATTERN_YMD);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return findDates(dBegin, dEnd);
	}

	/*
	 * 根据开始和结束时间 ，时间段的所有日期
	 */
	public static List<Date> findDates(Date dBegin, Date dEnd) {

		List<Date> list = new ArrayList<Date>();

		// 添加第一天
		list.add(dBegin);

		// 使用给定的 Date 设置此 Calendar 的时间
		Calendar calBegin = Calendar.getInstance();
		calBegin.setTime(dBegin);

		// 使用给定的 Date 设置此 Calendar 的时间
		Calendar calEnd = Calendar.getInstance();
		calEnd.setTime(dEnd);

		// 测试此日期是否在指定日期之后
		while (calEnd.compareTo(calBegin) > 0) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			calBegin.add(Calendar.DATE, 1);
			list.add(calBegin.getTime());
		}

		return list;
	}

	/**
	 * 增加/减少天数
	 * 
	 * @param dateStr
	 * @param days
	 * @return
	 * @return String
	 */
	public static String addDate(String dateStr, int days) {

		try {
			Date sDate = DateUtils.parseDate(dateStr, Env.DATE_PATTERN_YMD);
			// 使用给定的 Date 设置此 Calendar 的时间
			Calendar c = Calendar.getInstance();
			c.setTime(sDate);
			c.add(Calendar.DATE, days);
			return SFYMD.format(c.getTime());
		} catch (ParseException e) {
			logger.error("DateUtilTool=>日期计算失败：dateStr【" + dateStr + "】days【" + days + "】", e);
			return "";
		}
	}

	/**
	 * 增加/减少天数
	 * 
	 * @param dateStr
	 * @param days
	 * @return
	 * @return String
	 */
	public static String addDays(String dateStr, int days, String[] DATE_PATTERN) {

		try {
			Date sDate = DateUtils.parseDate(dateStr, DATE_PATTERN);
			// 使用给定的 Date 设置此 Calendar 的时间
			Calendar c = Calendar.getInstance();
			c.setTime(sDate);
			c.add(Calendar.DATE, days);
			return SFYMD.format(c.getTime());
		} catch (ParseException e) {
			logger.error("DateUtilTool=>日期计算失败：dateStr【" + dateStr + "】days【" + days + "】", e);
			return "";
		}
	}

	/**
	 * 增加/减少小时
	 * 
	 * @param dateStr
	 * @param days
	 * @return
	 * @return String
	 */
	public static String addHours(String dateStr, int hours) {

		try {
			Date sDate = DateUtils.parseDate(dateStr, Env.DATE_PATTERN_YMDHMS);
			// 使用给定的 Date 设置此 Calendar 的时间
			Calendar c = Calendar.getInstance();
			c.setTime(sDate);
			c.add(Calendar.HOUR, hours);
			return SFYMDHMS.format(c.getTime());
		} catch (ParseException e) {
			logger.error("DateUtilTool=>日期计算失败：dateStr【" + dateStr + "】hours【" + hours + "】", e);
			return "";
		}
	}

	/**
	 * 增加/减少秒
	 * 
	 * @param dateStr
	 * @param days
	 * @return
	 * @return String
	 */
	public static String addSecond(String dateStr, int second) {

		try {
			Date sDate = DateUtils.parseDate(dateStr, Env.DATE_PATTERN_YMDHMS);
			// 使用给定的 Date 设置此 Calendar 的时间
			Calendar c = Calendar.getInstance();
			c.setTime(sDate);
			c.add(Calendar.SECOND, second);
			return SFYMDHMS.format(c.getTime());
		} catch (ParseException e) {
			logger.error("DateUtilTool=>日期计算失败：dateStr【" + dateStr + "】second【" + second + "】", e);
			return "";
		}
	}

	public static String[] getFirstDayAndEndDayOfYear(Calendar inputCal) {

		Calendar c = (Calendar) inputCal.clone();
		String[] date = new String[2];

		date[1] = SFYMD.format(c.getTime());
		// 设置年，月、日初始
		int year = c.get(Calendar.YEAR);
		date[0] = year + "-01-01";

		return date;

	}

	public static String[] getFirstDayAndLastDayOfYear() {

		String[] date = new String[2];

		// 获取当前时间
		Calendar c = Calendar.getInstance();

		// 设置年，月、日初始
		int year = c.get(Calendar.YEAR);
		date[0] = year + "-01-01";

		// 清空
		c.clear();
		// 设置年，月、日初始
		c.set(Calendar.YEAR, year);
		// 减去一天为当年最后一天
		c.roll(Calendar.DAY_OF_YEAR, -1);
		// 获取当前年最后一天
		date[1] = SFYMD.format(c.getTime());

		return date;

	}

	/**
	 * 获取年的第一天和最后一天数组
	 * 
	 * @param c
	 * @return
	 * @return String[]
	 */
	public static String[] getFirstDayAndLastDayOfYear(int year) {

		String[] date = new String[2];
		// 设置年的第一天
		date[0] = year + "-01-01";

		// 获取当前时间
		Calendar c = Calendar.getInstance();
		// 清空
		c.clear();
		// 设置年，月、日初始
		c.set(Calendar.YEAR, year);
		// 减去一天为当年最后一天
		c.roll(Calendar.DAY_OF_YEAR, -1);

		// 获取当前年最后一天
		date[1] = SFYMD.format(c.getTime());

		return date;

	}

	/**
	 * 获取本季度第一天和今天数组
	 * 
	 * @param c
	 * @return
	 * @return String[]
	 */
	public static String[] getFirstDayAndEndDayOfQuarter(Calendar inputCal) {

		Calendar c = (Calendar) inputCal.clone();

		String[] date = new String[2];

		int year = c.get(Calendar.YEAR);// 季度的年份
		int mouth = c.get(Calendar.MONTH) + 1;// 季度的月份

		// 根据月份,判断是哪个季度
		switch (mouth) {
		case 1:
		case 2:
		case 3:
			date[0] = year + "-01-01";
			break;
		case 4:
		case 5:
		case 6:
			date[0] = year + "-04-01";
			break;
		case 7:
		case 8:
		case 9:
			date[0] = year + "-07-01";
			break;
		case 10:
		case 11:
		case 12:
			date[0] = year + "-10-01";
			break;
		}
		// 当天为季度最后一天
		date[1] = SFYMD.format(c.getTime());

		return date;
	}

	/**
	 * 根据当前时间获取季度的第一天和做后一天
	 * 
	 * @param num前推负数n后推正数n季度
	 * @return
	 * @return String[]
	 */
	public static String[] getFirstDayAndLastDayOfQuarter(int n) {

		String[] date = new String[2];

		// 取得日历
		Calendar calendar = Calendar.getInstance();

		// 日历减6个月,即上上季度
		calendar.add(Calendar.MONTH, 3 * n);

		int year = calendar.get(Calendar.YEAR);// 季度的年份
		int mouth = calendar.get(Calendar.MONTH) + 1;// 季度的月份

		// 根据月份,判断是哪个季度
		switch (mouth) {
		case 1:
		case 2:
		case 3:
			date[0] = year + "-01-01";
			date[1] = year + "-03-31"; // 第一季度最后一天是3/31
			break;
		case 4:
		case 5:
		case 6:
			date[0] = year + "-04-01";
			date[1] = year + "-06-30";// 第二季度最后一天是6/30
			break;
		case 7:
		case 8:
		case 9:
			date[0] = year + "-07-01";
			date[1] = year + "-09-30";// 第三季度最后一天是9/30
			break;
		case 10:
		case 11:
		case 12:
			date[0] = year + "-10-01";
			date[1] = year + "-12-31";// 第四季度最后一天是12/31
			break;
		}

		return date;
	}

	/**
	 * 获取当月第一天到当天
	 * 
	 * @return
	 * @return String[]
	 */
	public static String[] getFirstDayAndNowDayOfMounth() {
		Calendar c = Calendar.getInstance();
		return getFirstDayAndEndDayOfMounth(c);
	}

	public static String[] getFirstDayAndEndDayOfMounth(Calendar inputCal) {

		Calendar c = (Calendar) inputCal.clone();

		String[] date = new String[2];

		// 设置成当前天
		date[1] = SFYMD.format(c.getTime());

		// 获取月第一天：设置为1号,既当月第一天
		c.set(Calendar.DAY_OF_MONTH, 1);
		date[0] = SFYMD.format(c.getTime());

		return date;
	}

	/**
	 * 获取当月的第一天和最后一天
	 * 
	 * @return
	 */
	public static String[] getFirstDayAndLastDayOfMounth() {

		// 获取当前时间
		Calendar c = Calendar.getInstance();
		return getFirstDayAndLastDayOfMounth(c);
	}

	/**
	 * 推n月获取月开始结束时间
	 * 
	 * @param index
	 * @return
	 * @return String[]
	 */
	public static String[] getFirstDayAndLastDayOfMounth(int index) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, index);
		return getFirstDayAndLastDayOfMounth(c);
	}

	/**
	 * 获取月的第一天和最后一天
	 * 
	 * @return
	 */
	public static String[] getFirstDayAndLastDayOfMounth(Calendar inputCal) {

		String[] date = new String[2];

		Calendar c = (Calendar) inputCal.clone();

		c.clone();

		// 获取月第一天：设置为1号,既当月第一天
		c.set(Calendar.DAY_OF_MONTH, 1);
		date[0] = SFYMD.format(c.getTime());

		// 获取当前月最后一天
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		date[1] = SFYMD.format(c.getTime());

		return date;
	}

	/**
	 * 获取当前周的第一天和最后一天
	 * 
	 * @param c
	 * @return
	 * @return String[]
	 */
	public static String[] getFirstDayAndLastDayOfWeek() {
		// 获取当前时间
		Calendar c = Calendar.getInstance();
		return getFirstDayAndLastDayOfWeek(c);
	}

	/**
	 * 获取周的第一天和最后一天
	 * 
	 * @param c
	 * @return
	 * @return String[]
	 */
	public static String[] getFirstDayAndLastDayOfWeek(Calendar inputCal) {

		Calendar c = (Calendar) inputCal.clone();

		String[] date = new String[2];

		c = Calendar.getInstance();
		// 周一
		c.add(Calendar.DATE, -c.get(Calendar.DAY_OF_WEEK) + 2);
		date[0] = SFYMD.format(c.getTime());
		// 周日
		c.add(Calendar.DATE, +6);
		date[1] = SFYMD.format(c.getTime());

		return date;
	}

	/**
	 * 获取当月第一天
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 * @return String
	 */
	public static String getFirstDayOfMounth(String date) throws ParseException {

		Calendar c = Calendar.getInstance();

		c.setTime(DateUtils.parseDate(date, Env.DATE_PATTERN_YMD));

		return getFirstDayOfMounth(c);
	}

	/**
	 * 获取当月第一天
	 * 
	 * @param c
	 * @return
	 * @return String
	 */
	public static String getFirstDayOfMounth(Calendar inputCal) {

		Calendar c = (Calendar) inputCal.clone();

		// 获取月第一天：设置为1号,既当月第一天
		c.set(Calendar.DAY_OF_MONTH, 1);
		String date = SFYMD.format(c.getTime());

		return date;
	}

	/**
	 * 获取当月最后一天
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 * @return String
	 */
	public static String getLastDayOfMounth(String date) throws ParseException {

		Calendar c = Calendar.getInstance();

		c.setTime(DateUtils.parseDate(date, Env.DATE_PATTERN_YMD));

		return getLastDayOfMounth(c);
	}

	/**
	 * 获取当月最后一天
	 * 
	 * @param c
	 * @return
	 * @return String
	 */
	public static String getLastDayOfMounth(Calendar inputCal) {

		Calendar c = (Calendar) inputCal.clone();

		// 获取当前月最后一天
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		String date = SFYMD.format(c.getTime());

		return date;
	}

	/**
	 * 前推index年份返回开始结束天List
	 * 
	 * @param dateStr
	 * @param index
	 * @return
	 * @return List<String[]>
	 */
	public static List<String[]> forwardIndexYearList(String dateStr, int index) {

		List<String[]> list = new ArrayList<String[]>();
		try {
			// 获取当前时间
			Calendar c = Calendar.getInstance();
			// 设置日期
			c.setTime(DateUtils.parseDate(dateStr, Env.DATE_PATTERN_YMD));

			int year = c.get(Calendar.YEAR);
			for (int i = 0; i < index; i++) {
				// 获取当年的第一天和最后 一天
				list.add(DateUtilTool.getFirstDayAndLastDayOfYear(year));
				// 年份自减
				year--;
			}
		} catch (ParseException e) {
			logger.error("forwardIndexYearList日期转换失败：dateStr【" + dateStr + "】dateStr【" + dateStr + "】");
		}
		return list;

	}

	/**
	 * 前推index月份获取开始结束日期
	 * 
	 * @param dateStr
	 * @param index
	 * @return
	 * @return List<String[]>
	 */
	public static List<String[]> forwardIndexMonthList(String dateStr, int index) {

		List<String[]> list = new ArrayList<String[]>();
		try {
			// 获取当前时间
			Calendar c = Calendar.getInstance();
			// 设置日期
			c.setTime(DateUtils.parseDate(dateStr, Env.DATE_PATTERN_YMD));

			for (int i = 0; i < index; i++) {
				list.add(DateUtilTool.getFirstDayAndLastDayOfMounth(c));
				// 月份减一
				c.add(Calendar.MONTH, -1);
			}
		} catch (ParseException e) {
			logger.error("forwardIndexMonthList日期转换失败：dateStr【" + dateStr + "】dateStr【" + dateStr + "】");
		}
		return list;
	}

	/**
	 * 前推index周获取每周起止日期
	 * 
	 * @param dateStr
	 * @param index
	 * @return
	 * @return List<String[]>
	 */
	public static List<String[]> forwardIndexWeekList(String dateStr, int index) {

		List<String[]> list = new ArrayList<String[]>();
		try {
			// 获取当前时间
			Calendar c = Calendar.getInstance();
			// 设置日期
			c.setTime(DateUtils.parseDate(dateStr, Env.DATE_PATTERN_YMD));

			// 设置成本周五
			c.add(Calendar.DATE, -c.get(Calendar.DAY_OF_WEEK) + 6);

			for (int i = 0; i < index; i++) {

				String[] weekDay = new String[2];

				// 周四
				c.add(Calendar.DATE, -1);
				weekDay[1] = SFYMD.format(c.getTime());
				// 上周五
				c.add(Calendar.DATE, -6);
				weekDay[0] = SFYMD.format(c.getTime());

				list.add(weekDay);
			}
		} catch (ParseException e) {
			logger.error("forwardIndexWeekList日期转换失败：dateStr【" + dateStr + "】dateStr【" + dateStr + "】");
		}
		return list;
	}

	/**
	 * 得到某年某周的第一天
	 *
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getFirstDayOfWeek(int year, int week) {
		week = week - 1;
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		calendar.set(Calendar.DATE, 1);

		Calendar cal = (Calendar) calendar.clone();
		cal.add(Calendar.DATE, week * 7);

		return getFirstDayOfWeek(cal.getTime());
	}

	/**
	 * 得到某年某周的最后一天
	 *
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getLastDayOfWeek(int year, int week) {
		week = week - 1;
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		calendar.set(Calendar.DATE, 1);
		Calendar cal = (Calendar) calendar.clone();
		cal.add(Calendar.DATE, week * 7);

		return getLastDayOfWeek(cal.getTime());
	}

	/**
	 * 取得当前日期所在周的第一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek()); // Sunday
		return calendar.getTime();
	}

	/**
	 * 取得当前日期所在周的最后一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6); // Saturday
		return calendar.getTime();
	}

	/**
	 * 取得当前日期所在周的前一周最后一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfLastWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return getLastDayOfWeek(calendar.get(Calendar.YEAR), calendar.get(Calendar.WEEK_OF_YEAR) - 1);
	}

	/**
	 * 返回指定日期的月的第一天
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
		return calendar.getTime();
	}

	/**
	 * 返回指定年月的月的第一天
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getFirstDayOfMonth(Integer year, Integer month) {
		Calendar calendar = Calendar.getInstance();
		if (year == null) {
			year = calendar.get(Calendar.YEAR);
		}
		if (month == null) {
			month = calendar.get(Calendar.MONTH);
		}
		calendar.set(year, month, 1);
		return calendar.getTime();
	}

	/**
	 * 返回指定日期的月的最后一天
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
		calendar.roll(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * 返回指定年月的月的最后一天
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getLastDayOfMonth(Integer year, Integer month) {
		Calendar calendar = Calendar.getInstance();
		if (year == null) {
			year = calendar.get(Calendar.YEAR);
		}
		if (month == null) {
			month = calendar.get(Calendar.MONTH);
		}
		calendar.set(year, month, 1);
		calendar.roll(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * 返回指定日期的上个月的最后一天
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getLastDayOfLastMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) - 1, 1);
		calendar.roll(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * 返回指定日期的季的第一天
	 *
	 * @param year
	 * @param quarter
	 * @return
	 */
	public static Date getFirstDayOfQuarter(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return getFirstDayOfQuarter(calendar.get(Calendar.YEAR), getQuarterOfYear(date));
	}

	/**
	 * 返回指定年季的季的第一天
	 *
	 * @param year
	 * @param quarter
	 * @return
	 */
	public static Date getFirstDayOfQuarter(Integer year, Integer quarter) {
		Calendar calendar = Calendar.getInstance();
		Integer month = new Integer(0);
		if (quarter == 1) {
			month = 1 - 1;
		} else if (quarter == 2) {
			month = 4 - 1;
		} else if (quarter == 3) {
			month = 7 - 1;
		} else if (quarter == 4) {
			month = 10 - 1;
		} else {
			month = calendar.get(Calendar.MONTH);
		}
		return getFirstDayOfMonth(year, month);
	}

	/**
	 * 返回指定日期的季的最后一天
	 *
	 * @param year
	 * @param quarter
	 * @return
	 */
	public static Date getLastDayOfQuarter(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return getLastDayOfQuarter(calendar.get(Calendar.YEAR), getQuarterOfYear(date));
	}

	/**
	 * 返回指定年季的季的最后一天
	 *
	 * @param year
	 * @param quarter
	 * @return
	 */
	public static Date getLastDayOfQuarter(Integer year, Integer quarter) {
		Calendar calendar = Calendar.getInstance();
		Integer month = new Integer(0);
		if (quarter == 1) {
			month = 3 - 1;
		} else if (quarter == 2) {
			month = 6 - 1;
		} else if (quarter == 3) {
			month = 9 - 1;
		} else if (quarter == 4) {
			month = 12 - 1;
		} else {
			month = calendar.get(Calendar.MONTH);
		}
		return getLastDayOfMonth(year, month);
	}

	/**
	 * 返回指定日期的上一季的最后一天
	 *
	 * @param year
	 * @param quarter
	 * @return
	 */
	public static Date getLastDayOfLastQuarter(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return getLastDayOfLastQuarter(calendar.get(Calendar.YEAR), getQuarterOfYear(date));
	}

	/**
	 * 返回指定年季的上一季的最后一天
	 *
	 * @param year
	 * @param quarter
	 * @return
	 */
	public static Date getLastDayOfLastQuarter(Integer year, Integer quarter) {
		Calendar calendar = Calendar.getInstance();
		Integer month = new Integer(0);
		if (quarter == 1) {
			month = 12 - 1;
		} else if (quarter == 2) {
			month = 3 - 1;
		} else if (quarter == 3) {
			month = 6 - 1;
		} else if (quarter == 4) {
			month = 9 - 1;
		} else {
			month = calendar.get(Calendar.MONTH);
		}
		return getLastDayOfMonth(year, month);
	}

	/**
	 * 返回指定日期的季度
	 *
	 * @param date
	 * @return
	 */
	public static int getQuarterOfYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) / 3 + 1;
	}
}
