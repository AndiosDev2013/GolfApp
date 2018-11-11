package com.app.golfapp.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.app.golfapp.GolfApp;
import com.app.golfapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DateTimeUtils {

	private static final int SECOND = 1;
	private static final int MINUTE = 60 * SECOND;
	private static final int HOUR = 60 * MINUTE;
	private static final int DAY = 24 * HOUR;
	private static final int WEEK = 7 * DAY;
	private static final int MONTH = 30 * DAY;

	/*
	 * Time Format for saving
	 */
	public static String DEFAULT_FORMAT_TIME = "yyyy-MM-dd HH:mm:ss";

	/*
	 * Date -> yyyyMMdd
	 * Date -> MMdd
	 * Date -> yyyy/MM/dd
	 * Date -> dd/MM/yyyy
	 */
	public static String dateToString(Date date, String strformat) {
		if (date == null) return "";
		SimpleDateFormat format = new SimpleDateFormat(strformat);
		return format.format(date);
	}

	/*
	 * yyyyMMdd -> Date 
	 * MMdd -> Date 
	 * yyyy/MM/dd -> Date 
	 * dd/MM/yyyy -> Date 
	 */
	public static Date stringToDate(String strDate, String strformat) {
		if (TextUtils.isEmpty(strDate))
			return null;

		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat(strformat);
		try {
			date = format.parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return date;
	}

	public static int getAge(Date birthday) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(birthday);
		Calendar today = Calendar.getInstance();
		int age = today.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
		if ((today.get(Calendar.YEAR) > calendar.get(Calendar.YEAR)) &&
				(today.get(Calendar.MONTH) < calendar.get(Calendar.MONTH)))
			age--;
		if ((today.get(Calendar.YEAR) > calendar.get(Calendar.YEAR)) &&
				(today.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)) &&
				(today.get(Calendar.DAY_OF_MONTH) < calendar.get(Calendar.DAY_OF_MONTH)))
			age--;
		return age;
	}

	/*
	 *
	 * Date  ->  "3 hours ago"
	 *
	 */
	public static String convertDateTimeToString(Date date) {
		String formattedTime = null;
		try {
			long nowTime = new Date().getTime();
			long dateTime = date.getTime();
			if (nowTime < dateTime)
				return GolfApp.getContext().getResources().getString(R.string.checkin_time_minus);

			long timeInterval = ((new Date()).getTime() - date.getTime() )/ 1000;

			if (timeInterval < 1*MINUTE) {
				formattedTime = String.format(GolfApp.getContext().getResources().getString(
						timeInterval <= 1 ? R.string.checkin_time_second_ago : R.string.checkin_time_seconds_ago), timeInterval);
			} else if (timeInterval < 1*HOUR) {
				int minutes = (int) Math.floor((double)timeInterval/MINUTE);
				formattedTime = String.format(GolfApp.getContext().getResources().getString(
						minutes <= 1 ? R.string.checkin_time_minute_ago : R.string.checkin_time_minutes_ago), minutes);
			} else if (timeInterval < 1*DAY) {
				int hours = (int) Math.floor((double)timeInterval/HOUR);
				formattedTime = String.format(GolfApp.getContext().getResources().getString(
						hours <= 1 ? R.string.checkin_time_hour_ago : R.string.checkin_time_hours_ago), hours);
			} else if (timeInterval < 1*WEEK) {
				int days = (int) Math.floor((double)timeInterval/DAY);
				formattedTime = String.format(GolfApp.getContext().getResources().getString(
						days <= 1 ? R.string.checkin_time_day_ago : R.string.checkin_time_days_ago), days);
			} else if (timeInterval < 1*MONTH) {
				int weeks = (int) Math.floor((double)timeInterval/WEEK);
				formattedTime = String.format(GolfApp.getContext().getResources().getString(
						weeks <= 1 ? R.string.checkin_time_week_ago : R.string.checkin_time_weeks_ago), weeks);
			} else if (timeInterval < 3*MONTH) {
				int months = (int) Math.floor((double)timeInterval/MONTH);
				formattedTime = String.format(GolfApp.getContext().getResources().getString(
						months <= 1 ? R.string.checkin_time_month_ago : R.string.checkin_time_months_ago), months);
			} else {
				formattedTime = dateToString(date, DEFAULT_FORMAT_TIME);
			}

		} catch ( Exception e) {
			e.printStackTrace();
			formattedTime = GolfApp.getContext().getResources().getString(R.string.checkin_time_minus);
		}
		return formattedTime;
	}
	
	public static Date dateTrim(Date date) {
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);

        return calendar.getTime();
	}
	
	public static Date dateEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.HOUR_OF_DAY, 23);

        return calendar.getTime();
	}
}
