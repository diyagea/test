package com.tools;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetDateDistance {
	private static Logger logger = LogManager.getLogger(GetDateDistance.class);

	private static SimpleDate simpleDate = new SimpleDate();

	public final static String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss"; // 默认时间格式
	public final static FastDateFormat SFYMD = FastDateFormat.getInstance("yyyy-MM-dd");
	public final static FastDateFormat SFYMDHMS = FastDateFormat.getInstance(DEFAULT_PATTERN);

	/**
	 * 获取超时倍数
	 * 
	 * date格式: "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @return 0：未超时，1：超时1倍范围内，2：超时2倍范围内...以此类推
	 */
	public static int getOvertimeCount(String startDate, String endDate, String nowDate) {
		try {
			long start = SFYMDHMS.parse(startDate).getTime();
			long end = SFYMDHMS.parse(endDate).getTime();
			long now = SFYMDHMS.parse(nowDate).getTime();
			long totalTime = now - start;
			long planTime = end - start;

			return new Long(totalTime / planTime).intValue();

		} catch (Exception e) {
			logger.error("获取超时倍数 异常：startDate=" + startDate + "，endDate=" + endDate + "，nowDate=" + nowDate + "，");
		}
		return -1;
	}

	/**
	 * 根据当前时间
	 * 
	 * @return 例如：2006-06-06 12:12:50
	 */
	public static String[] getTowDateTime(int state) {
		SimpleDate sd = new SimpleDate();
		String nowDateY = sd.getSimpleDateY();
		String nowDateM = sd.getSimpleDateM();
		int year = Integer.parseInt(nowDateY);
		String[] returnDateTime = null;
		FastDateFormat s = FastDateFormat.getInstance("yyyy-MM-dd 00:00:00");
		FastDateFormat s2 = FastDateFormat.getInstance("yyyy-MM-dd 23:59:59");
		FastDateFormat s3 = FastDateFormat.getInstance("yyyy-MM-dd");
		Calendar c;
		switch (state) {
		case -1: // 前七天
			returnDateTime = new String[7];
			c = Calendar.getInstance();
			// 7
			c.add(Calendar.DATE, -7);
			returnDateTime[0] = s3.format(c.getTime());
			// 6
			c.add(Calendar.DATE, +1);
			returnDateTime[1] = s3.format(c.getTime());
			// 5
			c.add(Calendar.DATE, +1);
			returnDateTime[2] = s3.format(c.getTime());
			// 4
			c.add(Calendar.DATE, +1);
			returnDateTime[3] = s3.format(c.getTime());
			// 3
			c.add(Calendar.DATE, +1);
			returnDateTime[4] = s3.format(c.getTime());
			// 2
			c.add(Calendar.DATE, +1);
			returnDateTime[5] = s3.format(c.getTime());
			// 1
			c.add(Calendar.DATE, +1);
			returnDateTime[6] = s3.format(c.getTime());
			break;
		case 0: // 周
			returnDateTime = new String[2];
			c = Calendar.getInstance();
			// 周一
			c.add(Calendar.DATE, -c.get(Calendar.DAY_OF_WEEK) + 2);
			returnDateTime[0] = s.format(c.getTime());
			// 周日
			c.add(Calendar.DATE, +6);
			returnDateTime[1] = s2.format(c.getTime());
			break;
		case 1:// 月
			returnDateTime = new String[2];
			returnDateTime[0] = nowDateY + "-" + nowDateM + "-01" + " 00:00:00";
			if (nowDateM.equals("01") || nowDateM.equals("03") || nowDateM.equals("05") || nowDateM.equals("07") || nowDateM.equals("08") || nowDateM.equals("10") || nowDateM.equals("12")) {
				returnDateTime[1] = nowDateY + "-" + nowDateM + "-31" + " 23:59:59";
			} else if (nowDateM.equals("04") || nowDateM.equals("06") || nowDateM.equals("09") || nowDateM.equals("11")) {
				returnDateTime[1] = nowDateY + "-" + nowDateM + "-30" + " 23:59:59";
			} else {
				if (year % 100 == 0) {
					if (year % 400 == 0) {
						returnDateTime[1] = nowDateY + "-" + nowDateM + "-29" + " 23:59:59";
					} else {
						if (year % 4 == 0) {
							returnDateTime[1] = nowDateY + "-" + nowDateM + "-29" + " 23:59:59";
						} else {
							returnDateTime[1] = nowDateY + "-" + nowDateM + "-28" + " 23:59:59";
						}
					}
				} else {
					returnDateTime[1] = nowDateY + "-" + nowDateM + "-28" + " 23:59:59";
				}
			}
			break;
		case 2:// 季
			returnDateTime = new String[2];
			if (nowDateM.equals("01") || nowDateM.equals("02") || nowDateM.equals("03")) {
				returnDateTime[0] = nowDateY + "-01-01 00:00:00";
				returnDateTime[1] = nowDateY + "-03-31 23:59:59";
			} else if (nowDateM.equals("04") || nowDateM.equals("05") || nowDateM.equals("06")) {
				returnDateTime[0] = nowDateY + "-04-01 00:00:00";
				returnDateTime[1] = nowDateY + "-06-30 23:59:59";
			} else if (nowDateM.equals("07") || nowDateM.equals("08") || nowDateM.equals("09")) {
				returnDateTime[0] = nowDateY + "-07-01 00:00:00";
				returnDateTime[1] = nowDateY + "-09-30 23:59:59";
			} else if (nowDateM.equals("10") || nowDateM.equals("11") || nowDateM.equals("12")) {
				returnDateTime[0] = nowDateY + "-10-01 00:00:00";
				returnDateTime[1] = nowDateY + "-12-31 23:59:59";
			}
			break;
		case 3:// 年
			returnDateTime = new String[2];
			returnDateTime[0] = nowDateY + "-01-01 00:00:00";
			returnDateTime[1] = nowDateY + "-12-31 23:59:59";
			break;
		default:
			break;
		}
		return returnDateTime;
	}

	// **
	// ** 判断2个时间相差多少天、多少小时、多少分<br>
	// ** <br>
	// * *@param pBeginTime 开始时间 YYYY-MM-DD HH:MM:SS<br>
	// ** @param pEndTime 结束时间 YYYY-MM-DD HH:MM:SS<br>
	// ** @return String 计算结果<br>
	// ** @Exception 发生异常<br>
	// * */

	/**
	 * 返回当前日期时间
	 * 
	 * @return 例如：2006-06-06 12:12:50
	 */
	public static String getCurDateTime() {
		return getCurDateTime(DEFAULT_PATTERN);
	}

	/**
	 * 根据给定的格式返回当前日期或时间 相当于调用getDateTime(formatStr,Calendar.getInstance()
	 * 
	 * @param formatStr
	 *            日期时间格式 例如：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getCurDateTime(String formatStr) {
		return getDateTime(formatStr, Calendar.getInstance());
	}

	/**
	 * 根据给定的格式、Calendar返回相应字符串
	 * 
	 * @param formatStr
	 *            日期时间格式 例如：yyyy-MM-dd HH:mm:ss
	 * @param c
	 *            Calendar实例
	 * @return
	 */
	public static String getDateTime(String formatStr, Calendar c) {
		FastDateFormat nowDate = FastDateFormat.getInstance(formatStr);
		String curTimeStr = nowDate.format(c.getTime());

		return curTimeStr;
	}

	/**
	 * 获取当前时间的 时间戳
	 * 
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static String getTimeLine() {
		Calendar c = Calendar.getInstance();
		return String.valueOf(c.get(c.YEAR)) + c.get(c.MONTH);
	}

	/**
	 * 根据开始时间和结束时间获取时间差 天 小时 秒
	 * 
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static String getDateDistance(String beginTime, String endTime) {
		String distance = "";
		try {
			long beginL = DateUtils.parseDate(beginTime, Env.DATE_PATTERN_YMDHMS).getTime();
			long endL = DateUtils.parseDate(endTime, Env.DATE_PATTERN_YMDHMS).getTime();
			long day = (endL - beginL) / 86400000;
			long hour = ((endL - beginL) % 86400000) / 3600000;
			long min = ((endL - beginL) % 86400000 % 3600000) / 60000;
			distance = day + "-" + hour + "-" + min;
		} catch (Exception e) {
			distance = "error";
		}
		return distance;
	}

	/**
	 * 获取时间相差月数
	 * 
	 * @param date1
	 * @param date2
	 * @return int
	 * @throws ParseException
	 */
	public static int getMonthSpace(String start, String end) throws ParseException {

		int result = 0;

		Calendar startCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();

		startCal.setTime(DateUtils.parseDate(start, Env.DATE_PATTERN_YMD));
		endCal.setTime(DateUtils.parseDate(end, Env.DATE_PATTERN_YMD));

		int years = endCal.get(Calendar.YEAR) - startCal.get(Calendar.YEAR);
		int mounts = endCal.get(Calendar.MONTH) - startCal.get(Calendar.MONTH);
		int days = endCal.get(Calendar.DAY_OF_MONTH) - startCal.get(Calendar.DAY_OF_MONTH);
		// 如果天数没过减去1
		if (days < 0) {
			result = years * 12 + mounts - 1;
		} else {
			result = years * 12 + mounts;
		}

		return result == 0 ? 0 : Math.abs(result);

	}

	/**
	 * 根据开始时间和结束时间获取相差天数（广义上的天，并且不能跨年）都是以这天的0点开始计算 比如 1号（任何时间）都和二号错一天 2016-01-01
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 * @return int
	 */
	public static int getDaysSpace(String startDate, String endDate) {

		try {
			Date sDate = DateUtils.parseDate(startDate, Env.DATE_PATTERN_YMD);
			Date eDate = DateUtils.parseDate(endDate, Env.DATE_PATTERN_YMD);
			return getDaysSpace(sDate, eDate);
		} catch (ParseException e) {
			logger.error("GetDateDistance获取时间间隔异常：startDate【" + startDate + "】endDate【" + endDate + "】", e);
			return 0;
		}
	}

	/**
	 * 根据开始时间和结束时间获取相差天数（广义上的天，并且不能跨年） 比如 1号（任何时间）都和二号错一天
	 * 
	 * @param sDate
	 * @param eDate
	 * @return
	 * @return int
	 */
	public static int getDaysSpace(Date sDate, Date eDate) {

		Calendar aCalendar = Calendar.getInstance();

		aCalendar.setTime(sDate);

		long time1 = aCalendar.getTimeInMillis();

		aCalendar.setTime(eDate);

		long time2 = aCalendar.getTimeInMillis();

		return new Long((time2 - time1) / (1000 * 3600 * 24)).intValue();
	}

	/**
	 * 获取数据相隔小时数
	 * 
	 * @param startDate
	 * @param endDate
	 * @param DATE_PATTERN
	 * @return
	 * @return int
	 */
	public static int getHoursSpace(String startDate, String endDate, String[] DATE_PATTERN) {
		int distance = 0;
		try {
			long beginL = DateUtils.parseDate(startDate, DATE_PATTERN).getTime();
			long endL = DateUtils.parseDate(endDate, DATE_PATTERN).getTime();
			long hour = (endL - beginL) / (60 * 60 * 1000);
			distance = (int) hour;

		} catch (ParseException e) {
			logger.error("GetDateDistance获取时间间隔异常：startDate【" + startDate + "】endDate【" + endDate + "】", e);
			return 0;
		}
		return distance;
	}

	/**
	 * 根据开始时间和结束时间获取时间差 天
	 * 
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static int getDateDistanceDay(String beginTime, String endTime) {
		int distance = 0;
		try {
			long beginL = DateUtils.parseDate(beginTime, Env.DATE_PATTERN_YMDHMS).getTime();
			long endL = DateUtils.parseDate(endTime, Env.DATE_PATTERN_YMDHMS).getTime();
			long day = (endL - beginL) / 86400000;
			distance = (int) day;
		} catch (Exception e) {
			distance = 0;
		}
		return distance;

	}

	/**
	 * 根据开始时间和结束时间获取时间差 小时（除去天，只计算相差小时）
	 * 
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static int getDateDistanceHours(String beginTime, String endTime) {
		int distance = 0;
		try {
			long beginL = DateUtils.parseDate(beginTime, Env.DATE_PATTERN_YMDHMS).getTime();
			long endL = DateUtils.parseDate(endTime, Env.DATE_PATTERN_YMDHMS).getTime();
			long hour = ((endL - beginL) % 86400000) / 3600000;
			distance = (int) hour;
		} catch (Exception e) {
			distance = 0;
		}
		return distance;
	}

	/**
	 * 根据开始时间和结束时间获取时间差 分（除去天、小时，只计算相差分钟）
	 * 
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static int getDateDistanceMinute(String beginTime, String endTime) {
		int distance = 0;
		try {
			long beginL = DateUtils.parseDate(beginTime, Env.DATE_PATTERN_YMDHMS).getTime();
			long endL = DateUtils.parseDate(endTime, Env.DATE_PATTERN_YMDHMS).getTime();
			long min = ((endL - beginL) % 86400000 % 3600000) / 60000;
			distance = (int) min;
		} catch (Exception e) {
			distance = 0;
		}
		return distance;
	}

	/**
	 * 精确计算两个时间相差分钟数
	 * 
	 * @param beginTime
	 * @return
	 * @return int
	 */
	public static int getDateDistanceMinute2(String beginTime) {
		int distance = 0;
		try {
			long beginL = DateUtils.parseDate(beginTime, Env.DATE_PATTERN_YMDHMS).getTime();
			long endL = DateUtils.parseDate(getCurDateTime(), Env.DATE_PATTERN_YMDHMS).getTime();
			long min = (endL - beginL) / 60000;
			distance = (int) min;
		} catch (Exception e) {
			distance = 0;
		}
		return distance;
	}

	/**
	 * 根据开始时间和结束时间获取时间差 秒
	 * 
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static Long getDateDistanceSeconds(String beginTime, String endTime) {
		long disLong = 0;
		try {
			long beginL = DateUtils.parseDate(beginTime, Env.DATE_PATTERN_YMDHMS).getTime();
			long endL = DateUtils.parseDate(endTime, Env.DATE_PATTERN_YMDHMS).getTime();
			disLong = (endL - beginL) / 1000;
		} catch (Exception e) {
			disLong = 0;
		}
		return disLong;
	}

	/**
	 * 距离下个时间相差（会员到期之类会用）
	 * 
	 * @param endTime
	 * @return
	 */
	public static String getDateDistanceToEnd(String endTime) {
		String distance = "";
		String dateNow = simpleDate.getSimpleDateYMDHMS();
		String eTime = endTime + " 00:00:00";
		try {

			long beginL = DateUtils.parseDate(dateNow, Env.DATE_PATTERN_YMDHMS).getTime();
			long endL = DateUtils.parseDate(eTime, Env.DATE_PATTERN_YMDHMS).getTime();
			long day = (endL - beginL) / 86400000;
			long hour = ((endL - beginL) % 86400000) / 3600000;
			long min = ((endL - beginL) % 86400000 % 3600000) / 60000;
			distance = day + "y" + hour + "h" + min;
		} catch (Exception e) {
			distance = "error";
		}
		return distance;
	}

	/**
	 * 距离上个时间相差
	 * 
	 * @param endTime
	 * @return
	 */
	public static String getDateDistanceBeforCN(String endTime) {
		String distance = "您已在线";
		String dateNow = simpleDate.getSimpleDateYMDHMS();
		try {

			long beginL = DateUtils.parseDate(endTime, Env.DATE_PATTERN_YMDHMS).getTime();
			long endL = DateUtils.parseDate(dateNow, Env.DATE_PATTERN_YMDHMS).getTime();
			long day = (endL - beginL) / 86400000;
			long hour = ((endL - beginL) % 86400000) / 3600000;
			long min = ((endL - beginL) % 86400000 % 3600000) / 60000;
			if (day > 0) {
				distance += day + "天";
			}
			if (hour > 0) {
				distance += hour + "小时";
			}
			if (min > 0) {
				distance += min + "分钟";
			}
			if (distance.equals("您已在线")) {
				distance = "您刚刚登录了本系统";
			}

		} catch (Exception e) {
			distance = "error";
		}
		return distance;
	}

	/**
	 * 新消息距离上个时间相差
	 * 
	 * @param endTime
	 * @return
	 */
	public static String getMessageDateDistanceBeforeCN(String endTime) {

		String distance = "";
		String dateNow = simpleDate.getSimpleDateYMDHMS();
		try {
			long beginL = DateUtils.parseDate(endTime, Env.DATE_PATTERN_YMDHMS).getTime();
			long endL = DateUtils.parseDate(dateNow, Env.DATE_PATTERN_YMDHMS).getTime();
			long day = (endL - beginL) / 86400000;
			long hour = ((endL - beginL) % 86400000) / 3600000;
			long min = ((endL - beginL) % 86400000 % 3600000) / 60000;
			if (day > 0) {
				distance += day + "天";
			}
			if (hour > 0) {
				distance += hour + "小时";
			}
			if (min > 0) {
				distance += min + "分钟";
			}
			if (Common.checkNull(distance)) {
				distance = "刚刚";
			} else {
				distance += "前";
			}

		} catch (Exception e) {
			distance = "error";
		}
		return distance;
	}

	public static String getDateDistanceAjax(String beginTime, String endTime) {

		StringBuffer buff = new StringBuffer();

		try {
			long beginL = DateUtils.parseDate(beginTime, Env.DATE_PATTERN_YMDHMS).getTime();
			long endL = DateUtils.parseDate(endTime, Env.DATE_PATTERN_YMDHMS).getTime();
			long day = (endL - beginL) / 86400000;
			long hour = ((endL - beginL) % 86400000) / 3600000;
			long min = ((endL - beginL) % 86400000 % 3600000) / 60000;

			if (day > 0) {
				buff.append(day + "天");
			}
			if (hour > 0) {
				buff.append(hour + "小时");
			}
			if (min > 0) {
				buff.append(min + "分钟");
			}
			if (buff.length() == 0) {
				buff.append("小于1分钟");
			}
		} catch (Exception e) {
			buff.append("时间输入错误！");
		}
		return buff.toString();
	}

	public static String getDateDistanceLast(String beginTime, String endTime) {

		StringBuffer buff = new StringBuffer();

		try {
			long beginL = DateUtils.parseDate(beginTime, Env.DATE_PATTERN_YMDHMS).getTime();
			long endL = DateUtils.parseDate(endTime, Env.DATE_PATTERN_YMDHMS).getTime();
			if (beginL > endL) {
				buff.append("0天");
			} else {
				long day = (endL - beginL) / 86400000;
				long hour = ((endL - beginL) % 86400000) / 3600000;
				long min = ((endL - beginL) % 86400000 % 3600000) / 60000;

				if (day > 0) {
					buff.append(day + "天");
				}
				if (hour > 0) {
					buff.append(hour + "小时");
				}
				if (min > 0) {
					buff.append(min + "分钟");
				}
			}
		} catch (Exception e) {
			buff.append("时间输入错误！");
		}
		return buff.toString();
	}

	/**
	 * 通用时间show2
	 * 
	 * @param time
	 * @return
	 */
	public static String getDateshow2(String time) {
		String array[] = time.split(" ");
		String ymd = array[0];
		String hms = array[1];
		String ymdarray[] = ymd.split("-");
		int y = Integer.parseInt(ymdarray[0]);
		int m = Integer.parseInt(ymdarray[1]);
		int d = Integer.parseInt(ymdarray[2]);
		long date = GetDateDistance.getDateDistanceSeconds(time, simpleDate.getSimpleDateYMDHMS());
		long yyyy = date / 31536000;
		// 超过一年
		if (yyyy > 0) {
			return y + "年" + m + "月" + d + "日 " + hms;
		} else {
			if (date < 60) {
				return "刚刚";
			} else if (date < 120) {
				return "1分钟前";
			} else if (date < 180) {
				return "2分钟前";
			} else if (date < 240) {
				return "3分钟前";
			} else if (date < 300) {
				return "4分钟前";
			} else if (date < 360) {
				return "5分钟前";
			} else if (date < 660) {
				return "10分钟前";
			} else if (date < 960) {
				return "15分钟前";
			} else if (date < 86400) {
				return hms;
			} else {
				return m + "月" + d + "日 " + hms;
			}
		}
	}

	/**
	 * 通用时间show
	 * 
	 * @param time
	 * @return
	 */
	public static String getDateshow(String time) {
		String array[] = time.split(" ");
		String ymd = array[0];
		String hms = array[1];
		String ymdarray[] = ymd.split("-");
		String y = ymdarray[0];
		String m = ymdarray[1];
		String d = ymdarray[2];
		long date = GetDateDistance.getDateDistanceSeconds(time, simpleDate.getSimpleDateYMDHMS());
		long yyyy = date / 31536000;
		if (yyyy > 0) {
			return yyyy + "年前";
		}
		long MM = date / 2592000;
		if (MM > 0) {
			if (!simpleDate.getSimpleDateY().equals(y)) {
				return time;
			} else {
				String returnDate = "";
				if (Integer.parseInt(m) > 9) {
					returnDate += m + "月";
				} else {
					returnDate += Integer.parseInt(m) + "月";
				}
				if (Integer.parseInt(d) > 9) {
					returnDate += d + "号";
				} else {
					returnDate += Integer.parseInt(d) + "号";
				}
				return returnDate + " " + hms;
			}
		}
		long dd = date / 86400;
		if (dd > 2) {
			if (!simpleDate.getSimpleDateY().equals(y)) {
				return time;
			} else {
				String returnDate = "";
				if (Integer.parseInt(m) > 9) {
					returnDate += m + "月";
				} else {
					returnDate += Integer.parseInt(m) + "月";
				}
				if (Integer.parseInt(d) > 9) {
					returnDate += d + "号";
				} else {
					returnDate += Integer.parseInt(d) + "号";
				}
				return returnDate + " " + hms;
			}
		} else if (dd == 2) {
			return "前天 " + hms;
		} else if (dd == 1) {
			return "昨天 " + hms;
		}
		if (date < 60) {
			return "1秒钟前";
		} else if (date < 120) {
			return "1分钟前";
		} else if (date < 180) {
			return "2分钟前";
		} else if (date < 240) {
			return "3分钟前";
		} else if (date < 300) {
			return "4分钟前";
		} else if (date < 360) {
			return "5分钟前";
		} else if (date < 660) {
			return "10分钟前";
		} else if (date < 960) {
			return "15分钟前";
		} else if (date < 1260) {
			return "20分钟前";
		} else if (date < 1860) {
			return "半个小时前";
		} else if (date < 3660) {
			return "1个小时前";
		} else {
			return hms;
		}
	}

	public static String getDateShowStr(String inputDateStr) {

		// 获得当前日期秒
		// int inputSecond = inputCal.get(Calendar.SECOND);
		// 0 代表前面补充0
		// 2 代表长度为2
		// d 代表参数为正数型
		// String.format("%02d", inputMinute);

		String dateShowStr = "";
		try {
			// 1、获取当前日期的Calendar对象
			Calendar nowCal = Calendar.getInstance();
			// 获得当前日期年份
			int nowYear = nowCal.get(Calendar.YEAR);
			// 获得当前日期月份
			int nowMonth = nowCal.get(Calendar.MONTH) + 1;
			// 获得当前日期日份
			int nowDay = nowCal.get(Calendar.DAY_OF_MONTH);
			// 获得当前日期时
			int nowHour = nowCal.get(Calendar.HOUR_OF_DAY);
			// 获得当前日期分
			int nowMinute = nowCal.get(Calendar.MINUTE);
			// 获得当前日期秒
			// int nowSecond = nowCal.get(Calendar.SECOND);

			// 转换时间字符串为Calendar对象
			Date daystart = DateUtils.parseDate(inputDateStr, Env.DATE_PATTERN_YMDHMS);

			// start_date是类似"2013-02-02"的字符串
			Calendar inputCal = Calendar.getInstance();
			inputCal.setTime(daystart); // 得到的dayc1就是你需要的calendar了
			// 获得当前日期年份
			int inputYear = inputCal.get(Calendar.YEAR);
			// 获得当前日期月份
			int inputMonth = inputCal.get(Calendar.MONTH) + 1;
			// 获得当前日期日份
			int inputDay = inputCal.get(Calendar.DAY_OF_MONTH);
			// 获得当前日期时
			int inputHour = inputCal.get(Calendar.HOUR_OF_DAY);
			String inputHourStr = String.format("%02d", inputHour);
			// 获得当前日期分
			int inputMinute = inputCal.get(Calendar.MINUTE);
			String inputMinuteStr = String.format("%02d", inputMinute);

			// 如果当前时间小于输入时间，直接返回时间
			if (nowCal.compareTo(inputCal) < 0) {
				dateShowStr = inputYear + "年" + inputMonth + "月" + inputDay + "日  " + inputHourStr + ":" + inputMinuteStr;
			}
			// 如果时间差大于一年，那么直接返回年月日 时分
			else if ((nowYear - inputYear) > 0) {
				dateShowStr = inputYear + "年" + inputMonth + "月" + inputDay + "日  " + inputHourStr + ":" + inputMinuteStr;
			}
			// 如果月份超过一个月，输出月日时分
			else if ((nowMonth - inputMonth) > 0) {
				dateShowStr = inputMonth + "月" + inputDay + "日  " + inputHourStr + ":" + inputMinuteStr;
			}
			// 如果是当月的，超过7天（一周），输出日时分
			else if ((nowDay - inputDay) > 2) {
				dateShowStr = inputMonth + "月" + inputDay + "日  " + inputHourStr + ":" + inputMinuteStr;
			} else if ((nowDay - inputDay) > 1) {
				dateShowStr = "前天  " + inputHourStr + ":" + inputMinuteStr;
			} else if ((nowDay - inputDay) > 0) {
				dateShowStr = "昨天  " + inputHourStr + ":" + inputMinuteStr;
			}
			// 今天天，一小时前
			else if ((nowHour - inputHour) > 0) {
				dateShowStr = inputHourStr + ":" + inputMinuteStr;
			}
			// 在当天，超过15分钟，输出 时分
			else if ((nowMinute - inputMinute) > 15) {
				dateShowStr = inputHourStr + ":" + inputMinuteStr;
			}
			// 在当天，15分钟内,一分钟前，输出几分钟前
			else if ((nowMinute - inputMinute) > 1) {
				dateShowStr = (nowMinute - inputMinute - 1) + "分钟前";
			}
			// 一分钟内，输出刚刚
			else {
				dateShowStr = "刚刚";
			}
		} catch (ParseException e) {
			dateShowStr = "";
		}
		return dateShowStr;

	}

	// 一、获取星期几：
	public static String getWeekDay(Calendar c) {
		if (c == null) {
			return "周一";
		}

		if (Calendar.MONDAY == c.get(Calendar.DAY_OF_WEEK)) {
			return "周一";
		}
		if (Calendar.TUESDAY == c.get(Calendar.DAY_OF_WEEK)) {
			return "周二";
		}
		if (Calendar.WEDNESDAY == c.get(Calendar.DAY_OF_WEEK)) {
			return "周三";
		}
		if (Calendar.THURSDAY == c.get(Calendar.DAY_OF_WEEK)) {
			return "周四";
		}
		if (Calendar.FRIDAY == c.get(Calendar.DAY_OF_WEEK)) {
			return "周五";
		}
		if (Calendar.SATURDAY == c.get(Calendar.DAY_OF_WEEK)) {
			return "周六";
		}
		if (Calendar.SUNDAY == c.get(Calendar.DAY_OF_WEEK)) {
			return "周日";
		}

		return "星期一";
	}

	/*
	 * 获取前七天的字符串日期（不包含时分秒）
	 */
	public String[][] getWeekDateStrs(Calendar cal) {

		String[][] dateStr = new String[2][8];

		FastDateFormat df0 = FastDateFormat.getInstance("yyyy-MM-dd");
		FastDateFormat df1 = FastDateFormat.getInstance("MM月dd日");

		// 获取明天日期
		cal.add(Calendar.DATE, +1);
		dateStr[0][0] = df0.format(cal.getTime());
		dateStr[1][0] = df1.format(cal.getTime());
		// 获取当前日期
		cal.add(Calendar.DATE, -1);
		dateStr[0][1] = df0.format(cal.getTime());
		dateStr[1][1] = df1.format(cal.getTime());
		// 获取当前1天
		cal.add(Calendar.DATE, -1); // 日期减1
		dateStr[0][2] = df0.format(cal.getTime());
		dateStr[1][2] = df1.format(cal.getTime());
		// 获取当前2天
		cal.add(Calendar.DATE, -1); // 日期减2
		dateStr[0][3] = df0.format(cal.getTime());
		dateStr[1][3] = df1.format(cal.getTime());
		// 获取当前3天
		cal.add(Calendar.DATE, -1); // 日期减3
		dateStr[0][4] = df0.format(cal.getTime());
		dateStr[1][4] = df1.format(cal.getTime());
		// 获取当前4天
		cal.add(Calendar.DATE, -1); // 日期减4
		dateStr[0][5] = df0.format(cal.getTime());
		dateStr[1][5] = df1.format(cal.getTime());
		// 获取当前5天
		cal.add(Calendar.DATE, -1); // 日期减5
		dateStr[0][6] = df0.format(cal.getTime());
		dateStr[1][6] = df1.format(cal.getTime());
		// 获取当前6天
		cal.add(Calendar.DATE, -1); // 日期减6
		dateStr[0][7] = df0.format(cal.getTime());
		dateStr[1][7] = df1.format(cal.getTime());

		return dateStr;
	}

	/**
	 * 
	 * @param 要转换的毫秒数
	 * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
	 * @author fy.zhang
	 */
	public static String formatDuring(long mss) {
		long days = mss / (1000 * 60 * 60 * 24);
		long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (mss % (1000 * 60)) / 1000;
		return days + " days " + hours + " hours " + minutes + " minutes " + seconds + " seconds ";
	}

	/**
	 * 
	 * @param begin
	 *            时间段的开始
	 * @param end
	 *            时间段的结束
	 * @return 输入的两个Date类型数据之间的时间间格用* days * hours * minutes * seconds的格式展示
	 * @author fy.zhang
	 */
	public static String formatDuring(Date begin, Date end) {
		return formatDuring(end.getTime() - begin.getTime());
	}
}
