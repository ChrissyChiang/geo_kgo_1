package gov.kcg.kgo.util;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.kcg.kgo.enums.backend.ParamSetEnum;
import gov.kcg.kgo.enums.backend.ParamSetTypeEnum;
import gov.kcg.kgo.model.KgoParamSet;
import gov.kcg.kgo.repository.KgoParamSetRepository;

/**
 * DateUtil.
 */
public class DateUtil implements Serializable {
	private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

	private static final long serialVersionUID = 1L;

	/** "yyyyMMdd". */
	public final static String PATTEN_YEAR_MONTH_DAY_NO_HYPHEN = "yyyyMMdd";

	/** "yyyyMMddHHmmss". */
	public final static String PATTEN_FULL_TIME_NO_HYPHEN = "yyyyMMddHHmmss";

	/** "yyyy-MM-dd". */
	public final static String PATTEN_YEAR_MONTH_DAY = "yyyy-MM-dd";

	/** yyyy-MM-dd HH:mm:ss. */
	public final static String PATTEN_FULL_TIME = "yyyy-MM-dd HH:mm:ss";

	/** yyyy/MM/dd HH:mm:ss. */
	public final static String PATTEN_FULL_TIME_SLASH = "yyyy/MM/dd HH:mm:ss";

	/** yyyy-MM-dd HH:mm:ss.SSS. */
	public final static String PATTEN_FULL_TIME_MS = "yyyy-MM-dd HH:mm:ss.SSS";

	/** yyyy-MM-dd HH:mm:ss.SSS. */
	public final static String PATTEN_FULL_TIME_TO_MINUTE = "yyyy-MM-dd HH:mm";

	/** yyyy/MM/-dd HH:mm:ss.SSS. */
	public final static String PATTEN_FULL_TIME_MS_SLASH = "yyyy/MM/dd HH:mm:ss.SSS";

	/** dd/MM/yyyy  */
	public final static String PATTEN_DAY_MONTH_YEAR = "dd/MM/yyyy";

	/** dd/MM/yyyy  */
	public final static String PATTEN_YEAR_MONTH_YEAR_SLASH = "yyyy/MM/dd";

	/** yyyy-MM-dd'T'hh:mm:ssZ */
	public final static String PATTEN_FULL_TIME_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	/** yyyy-MM-dd'T'hh:mm:ss.SSS. */
	public final static String PATTEN_FULL_TIME_MS_T = "yyyy-MM-dd'T'HH:mm:ss.SSS";

	/** yyyy/MM/dd */
	public final static String PATTEN_YEAR_MONTH_DAY_SLASH = "yyyy/MM/dd";

	/** yyyyMMddHHmmssz */
	public final static String PATTEN_TIMEZONE = "yyyyMMddHHmmssz";

	/** yyyy */
	public final static String FIELD_YEAR = "yyyy";

	/** yyy/MM/dd */
	public final static String PATTEN_YEAR_MONTH_DAY_SLASH_TW = "yyy/MM/dd";

	/** yyy-MM-dd */
	public final static String PATTEN_YEAR_MONTH_DAY_TW = "yyy-MM-dd";

	/** "yyyMMdd". */
	public final static String PATTEN_YEAR_MONTH_DAY_NO_HYPHEN_TW = "yyyMMdd";

	/** "yyyMMdd". */
	public final static String PATTEN_ONLY_TIME_NO_HYPHEN = "HHmmss";

	/** yyy/MM/dd HH:mm */
	public final static String PATTEN_YEAR_MONTH_DAY_SLASH_TIME = "yyy/MM/dd HH:mm";

	/** HH:mm */
	public final static String PATTEN_HOUR_MINUTE = "HH:mm ";

	/**
	 * 毫秒轉成秒.
	 *
	 * @param millsec the millsec
	 * @return the long
	 */
	public static long milliSecondToSec(long millsec) {
		return millsec / 1000;
	}

	/**
	 * 毫秒差值.
	 *
	 * @param millSec1 the mill sec 1
	 * @param millSec2 the mill sec 2
	 * @return the long
	 */
	public static long msDiff(long millSec1, long millSec2) {
		return millSec1 - millSec2;
	}

	/**
	 * 取得目前日期(Timestamp格式)
	 *
	 * @return
	 */
	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(new Date().getTime());
	}

	public static Date getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * Date轉日期字串
	 *
	 * @param date
	 * @param patten
	 * @return
	 */
	public static String dateToString(Date date, String patten) {
		String str = "";
		try {
			if (date != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat(patten);
				str = dateFormat.format(date);
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		return str;
	}

	/**
	 * Timestamp轉日期字串
	 *
	 * @param time
	 * @param patten
	 * @return
	 */
	public static String timestampToString(Timestamp time, String patten) {
		String str = "";

		if (time != null) {
			str = DateFormatUtils.format(time, patten);
		}
		return str;
	}

	/**
	 * 日期字串轉Timestamp
	 *
	 * @param str
	 * @param patten
	 * @return
	 * @throws ParseException
	 */
	public static Timestamp strToTimestamp(String str, String patten) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(patten);
		Date date = dateFormat.parse(str);
		Timestamp timestamp = new Timestamp(date.getTime());
		return timestamp;
	}

	/**
	 * 日期字串轉Date
	 *
	 * @param str
	 * @param patten
	 * @return
	 * @throws ParseException
	 */
	public static Date strToDate(String str, String patten) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(patten);
		Date date = dateFormat.parse(str);
		return date;
	}

	/**
	 * 字串日期格式轉換
	 *
	 * @param strDate
	 * @param inPatten(輸入格式)
	 * @param outPatten(輸出格式)
	 * @return
	 * @throws ParseException
	 */
	public static String strDateFormat(String strDate, String inPatten, String outPatten) throws ParseException {
		Timestamp timestamp = null;
		timestamp = strToTimestamp(strDate, inPatten);
		return dateToString(timestamp, outPatten);

	}

	/**
	 *
	 * @param strDate         2020-11-13T00:11:08+08:00
	 * @param outPatten(輸出格式)
	 * @return
	 * @throws ParseException
	 */
	public static String strDateFormat(String strDate, String outPatten) throws ParseException {
		OffsetDateTime parsedDateTime = OffsetDateTime.parse(strDate);
		Timestamp timestamp = Timestamp.from(parsedDateTime.toInstant());
		return dateToString(timestamp, outPatten);
	}

	/**
	 * 民國日期格式轉換(yyy/MM/dd)
	 *
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String convertTWDate(String strDate, String inPatten, String outPatten) throws ParseException {
		SimpleDateFormat dateIn = new SimpleDateFormat(inPatten);
		SimpleDateFormat dateOut = new SimpleDateFormat(outPatten);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateIn.parse(strDate));
		if (cal.get(Calendar.YEAR) > 1492) {
			cal.add(Calendar.YEAR, -1911);
		} else {
			cal.add(Calendar.YEAR, +1911);
		}
		return dateOut.format(cal.getTime());
	}

	/**
	 * 民國日期格式轉換(yyy)
	 * @param time
	 * @param outPatten
	 * @return
	 * @throws ParseException
	 */
	public static String convertTimeStampToTWDate(Timestamp time, String outPatten) throws ParseException {
		SimpleDateFormat dateOut = new SimpleDateFormat(outPatten);
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		if (cal.get(Calendar.YEAR) > 1492) {
			cal.add(Calendar.YEAR, -1911);
		} else {
			cal.add(Calendar.YEAR, +1911);
		}
		return dateOut.format(cal.getTime());
	}

	/**
	 * 取得當年度的最初時刻(ex:2018/01/01 00:00:00)
	 *
	 * @param dt (年)
	 * @return
	 */
	public static Date getYearFirstTime(Date dt) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);
		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(calendar.DAY_OF_MONTH));

		return setDate(dt, 0, 0, 0);
	}

	/**
	 * 設定時間(Timestamp)
	 *
	 * @param dt     : Date
	 * @param hour   : hour 12小時制
	 * @param minute : minute
	 * @param second :second
	 * @return Date
	 */
	public static Date setDate(Date dt, int hour, int minute, int second) {
		if (null == dt) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	/**
	 * 取得當前年度 yyyy, Ex: 2020
	 *
	 * @return the now year
	 */
	public static String getNowYear() {
		// 取得當前年度
		String nowYear = dateToString(new Date(), DateUtil.FIELD_YEAR);
		return nowYear;
	}

	/**
	 * 修改日期(加減年日月)
	 *
	 * @param timestamp
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @throws ParseException
	 */
	public static Timestamp modifyDate(Timestamp timestamp, Integer year, Integer month, Integer day) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timestamp.getTime());
		cal.add(Calendar.YEAR, year);
		cal.add(Calendar.MONTH, month);
		cal.add(Calendar.DAY_OF_MONTH, day);

		return new Timestamp(cal.getTime().getTime());
	}

	public static Timestamp modifyDate(Date date, Integer year, Integer month, Integer day) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		cal.add(Calendar.YEAR, year);
		cal.add(Calendar.MONTH, month);
		cal.add(Calendar.DAY_OF_MONTH, day);

		return new Timestamp(cal.getTime().getTime());
	}

	public static Timestamp modifyDate(Date date, Integer year, Integer month, Integer day , Integer hour , Integer min ,Integer sec) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		cal.add(Calendar.YEAR, year);
		cal.add(Calendar.MONTH, month);
		cal.add(Calendar.DAY_OF_MONTH, day);
		cal.add(Calendar.HOUR,hour);
		cal.add(Calendar.MINUTE,min);
		cal.add(Calendar.SECOND,sec);
		return new Timestamp(cal.getTime().getTime());
	}
	/**
	 * 獲取tomorrow的日期
	 *
	 * @param date
	 * @return
	 */
	public static Date getTomorrow(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, +1);
		date = calendar.getTime();
		return date;
	}

	/**
	 * 判斷是否是weekend
	 *
	 * @param sdate
	 * @return
	 * @throws ParseException
	 */
	public static boolean isWeekend(Date date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 判斷是否是holiday
	 *
	 * @param sdate
	 * @param list
	 * @return
	 * @throws ParseException
	 */
	public static boolean isHoliday(Date date, List<Date> list) throws ParseException {
		if (CollectionUtils.isNotEmpty(list)) {
			for (Date compareDate : list) {
				if (date.equals(compareDate)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 取得每個月的最後一天日期.
	 *
	 * @param calendar the calendar
	 * @return the last month day
	 */
	public static Date getLastMonthDay(Calendar calendar) {
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		return calendar.getTime();
	}

	/**
	 * 取得某天最大時間 2020/10/15 23:59:59.
	 *
	 * @param dateStr      the date str
	 * @param inputPatten  字串日期格式
	 * @param outputPatten 輸出式其時間格式
	 * @return the end of day
	 * @throws ParseException the parse exception
	 */
	public static Timestamp getEndOfDay(String dateStr, String inputPatten, String outputPatten) throws ParseException {
		Date inputDate = strToDate(dateStr, inputPatten);
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(inputDate.getTime()), ZoneId.systemDefault());
		;
		LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
		Date outputDate = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
		return strToTimestamp(dateToString(outputDate, outputPatten), outputPatten);
	}

	/**
	 * 取得某天最小時間 2020/10/15 00:00:00.
	 *
	 * @param dateStr      the date str
	 * @param inputPatten  字串日期格式
	 * @param outputPatten 輸出式其時間格式
	 * @return the start of day
	 * @throws ParseException the parse exception
	 */
	public static Timestamp getStartOfDay(String dateStr, String inputPatten, String outputPatten) throws ParseException {
		Date inputDate = strToDate(dateStr, inputPatten);
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(inputDate.getTime()), ZoneId.systemDefault());
		LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
		Date outputDate = Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
		return strToTimestamp(dateToString(outputDate, outputPatten), outputPatten);
	}

	/**
	 * 西元年份(起)~西元年份(迄) 合併 yyyy/MM/dd~yyyy/MM/dd
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	public static String mergeDate(String startDate, String endDate) throws ParseException {
		String mergeDate = "";
		if (StringUtils.isNoneBlank(startDate, endDate)) {
			mergeDate = String.format("%s~%s", startDate, endDate);
		}
		return mergeDate;
	}

	/**
	 * 確認日期格式有效與否
	 *
	 * @param format
	 * @param value
	 * @param locale
	 * @return
	 */
	public static boolean isValidFormat(String format, String value, Locale locale) {
		LocalDateTime ldt = null;
		DateTimeFormatter fomatter = DateTimeFormatter.ofPattern(format, locale);

		try {
			ldt = LocalDateTime.parse(value, fomatter);
			String result = ldt.format(fomatter);
			return result.equals(value);
		} catch (DateTimeParseException e) {
			try {
				LocalDate ld = LocalDate.parse(value, fomatter);
				String result = ld.format(fomatter);
				return result.equals(value);
			} catch (DateTimeParseException exp) {
				try {
					LocalTime lt = LocalTime.parse(value, fomatter);
					String result = lt.format(fomatter);
					return result.equals(value);
				} catch (DateTimeParseException e2) {
					LOGGER.error(e.getMessage(), e2);
				}
			}
		}

		return false;
	}

	/**
	 * 日期減 系統參數設定 案件暫存區保存期限 時間單位. Ex 12/9 -5 (天) = 12/4 只能看到以後的案件
	 *
	 * @param date the date
	 * @return the local date time
	 */
	public static LocalDateTime subTsDateBySysParam(Timestamp date) {
		KgoParamSetRepository kgoParamSetRepository = SpringUtil.getDao(KgoParamSetRepository.class);

		// 案件暫存區保存期限
		Optional<KgoParamSet> kgoParamSetOptional = kgoParamSetRepository.findById(ParamSetEnum.FQ.getType());
		LocalDateTime localDateTime = null;

		if (kgoParamSetOptional.isPresent()) {
			KgoParamSet kgoParamSet = kgoParamSetOptional.get();
			String value = kgoParamSet.getValue();
			String detailType = kgoParamSet.getDetailType();
			LocalDateTime fDateLocalDateTime = date.toLocalDateTime();
			if (detailType.equals(ParamSetTypeEnum.Y.getCode())) {
				localDateTime = fDateLocalDateTime.plusYears(Long.parseLong(value) * -1);
			} else if (detailType.equals(ParamSetTypeEnum.M.getCode())) {
				localDateTime = fDateLocalDateTime.plusMonths(Long.parseLong(value) * -1);
			} else if (detailType.equals(ParamSetTypeEnum.D.getCode())) {

				localDateTime = fDateLocalDateTime.plusDays(Long.parseLong(value) * -1);
			} else if (detailType.equals(ParamSetTypeEnum.H.getCode())) {
				localDateTime = fDateLocalDateTime.plusHours(Long.parseLong(value) * -1);
			} else if (detailType.equals(ParamSetTypeEnum.N.getCode())) {
				localDateTime = fDateLocalDateTime.plusMinutes(Long.parseLong(value) * -1);
			}
		}
		return localDateTime;
	}

	/**
	 * 取得前n天
	 * @param date
	 * @param days
	 * @param outputPatten
	 * @return
	 */
	public static String getBeforeDate(Date date, int days, String outputPatten) {
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
		localDateTime = localDateTime.minusDays(days);
		Date outputDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		return dateToString(outputDate, outputPatten);
	}

	/**
	 * 取得 現在+n分鐘 時間
	 * @param dateNow
	 * @param minutes
	 * @param outputPatten
	 * @return
	 */
	public static String getAfterTime(Date dateNow, int minutes, String outputPatten) {
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(dateNow.getTime()), ZoneId.systemDefault());
		localDateTime = localDateTime.plusMinutes(minutes);
		Date outputDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		return dateToString(outputDate, outputPatten);
	}

	/**
	 * 取得週幾
	 *
	 * @return
	 * @throws ParseException
	 */
	public static int getWeekDay(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 取得當月第幾週
	 *
	 * @return
	 * @throws ParseException
	 */
	public static int getWeekInMonth(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		return cal.get(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * 取得月
	 *
	 * @return
	 * @throws ParseException
	 */
	public static int getMonth(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH ) + 1;
	}

	/**
	 * 取得當週第一天日期
	 *
	 * @return
	 * @throws ParseException
	 */
	public static String getFirstDateInWeek(Integer year, Integer month,Integer week){
		return getDateInWeek(year, month, week, "first");
	}

	/**
	 * 取得當週最後一天日期
	 *
	 * @return
	 * @throws ParseException
	 */
	public static String getLastDateInWeek(Integer year, Integer month,Integer week){
		return getDateInWeek(year, month, week, "last");
	}

	private static String getDateInWeek(Integer year, Integer month,Integer week, String type){
		LOGGER.info("getDateInWeek year ="+year+"month="+month+","+",week="+week+",type="+type);
		Calendar c = Calendar.getInstance();
		LOGGER.info(" getDateInWeek start c.getTime()="+c.getTime());
		if (year != 0 && month != 0 && week !=0){
			c.set(Calendar.YEAR, year);
			c.set(Calendar.MONTH, month -1 );
			c.set(Calendar.WEEK_OF_MONTH, week);
			c.setFirstDayOfWeek(Calendar.MONDAY);
		}
		if (type.equals("first")) {
			c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
		} else {
			c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
		}
		String dateStr = DateUtil.dateToString(c.getTime(), DateUtil.PATTEN_YEAR_MONTH_DAY);
		LOGGER.info(" getDateInWeek end c.getTime()="+c.getTime());
		LOGGER.info("getDateInWeek="+dateStr);
		return dateStr;
	}

	/**
	 * 取得當週的每天日期
	 *
	 * @return
	 * @throws ParseException
	 */
	public static String getEveryDayInWeek(Integer year, Integer month,Integer week,Integer add){
		LOGGER.info("getEveryDayInWeek year="+year+",month="+month+",week="+week);
		Calendar c = Calendar.getInstance();
		if (year!=0&& month!=0 && week!=0){
			c.set(Calendar.YEAR, year);
			c.set(Calendar.MONTH, month -1 );
			c.set(Calendar.WEEK_OF_MONTH, week);
			c.setFirstDayOfWeek(Calendar.MONDAY);
			c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()+add);
		}
		String dateStr = DateUtil.dateToString(c.getTime(), DateUtil.PATTEN_YEAR_MONTH_DAY);
		LOGGER.info("getEveryDayInWeek dateStr="+dateStr);
		return dateStr;
	}

	/**
	 * 傳入字串判斷目前時間是否在區間內
	 */
	public static Boolean belongCalender(String nowTime, String beginTime, String endTime) throws ParseException {
		Date now = strToDate(nowTime,PATTEN_YEAR_MONTH_DAY);
		Date begin = strToDate(beginTime,PATTEN_YEAR_MONTH_DAY);
		Date end = strToDate(endTime,PATTEN_YEAR_MONTH_DAY);

		Calendar nowDate = Calendar.getInstance();
		nowDate.setTime(now);
		Calendar beginDate = Calendar.getInstance();
		beginDate.setTime(begin);
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(end);
		if (nowDate.after(beginDate) && nowDate.before(endDate)){
			return true;
		}else if (now.compareTo(begin) == 0 || now.compareTo(end) == 0){
			return true;
		}else {
			return false;
		} //if (date.after(begin)
	} //public static Boolean belongCalender

}
