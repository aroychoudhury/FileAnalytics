/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.fileanalytics.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.abhishek.fileanalytics.constants.DateFormats;
import org.abhishek.fileanalytics.constants.TimeZones;

public class DateUtils {
	/**
	 * Adds a number of years to a date returning a new object. The original
	 * date object is unchanged.
	 *
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date addYears(Date date, int amount) {
		return addYears(date, amount, TimeZones.DEFAULT);
	}

	public static Date addYears(Date date, int amount, TimeZones timeZone) {
		return add(date, Calendar.YEAR, amount, timeZone);
	}

	/**
	 * Adds a number of months to a date returning a new object. The original
	 * date object is unchanged.
	 *
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date addMonths(Date date, int amount) {
		return addMonths(date, amount, TimeZones.DEFAULT);
	}

	public static Date addMonths(Date date, int amount, TimeZones timeZone) {
		return add(date, Calendar.MONTH, amount, timeZone);
	}

	/**
	 * Adds a number of weeks to a date returning a new object. The original
	 * date object is unchanged.
	 *
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date addWeeks(Date date, int amount) {
		return addWeeks(date, amount, TimeZones.DEFAULT);
	}

	public static Date addWeeks(Date date, int amount, TimeZones timeZone) {
		return add(date, Calendar.WEEK_OF_YEAR, amount, timeZone);
	}

	/**
	 * Adds a number of days to a date returning a new object. The original date
	 * object is unchanged.
	 *
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date addDays(Date date, int amount) {
		return addDays(date, amount, TimeZones.DEFAULT);
	}

	public static Date addDays(Date date, int amount, TimeZones timeZone) {
		return add(date, Calendar.DAY_OF_MONTH, amount, timeZone);
	}

	/**
	 * Adds a number of hours to a date returning a new object. The original
	 * date object is unchanged.
	 *
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date addHours(Date date, int amount) {
		return addHours(date, amount, TimeZones.DEFAULT);
	}

	public static Date addHours(Date date, int amount, TimeZones timeZone) {
		return add(date, Calendar.HOUR_OF_DAY, amount, timeZone);
	}

	/**
	 * Adds a number of minutes to a date returning a new object. The original
	 * date object is unchanged.
	 *
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date addMinutes(Date date, int amount) {
		return addMinutes(date, amount, TimeZones.DEFAULT);
	}

	public static Date addMinutes(Date date, int amount, TimeZones timeZone) {
		return add(date, Calendar.MINUTE, amount, timeZone);
	}

	/**
	 * Adds a number of seconds to a date returning a new object. The original
	 * date object is unchanged.
	 *
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date addSeconds(Date date, int amount) {
		return addSeconds(date, amount, TimeZones.DEFAULT);
	}

	public static Date addSeconds(Date date, int amount, TimeZones timeZone) {
		return add(date, Calendar.SECOND, amount, timeZone);
	}

	/**
	 * Adds a number of milliseconds to a date returning a new object. The
	 * original date object is unchanged.
	 *
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date addMilliseconds(Date date, int amount) {
		return addMilliseconds(date, amount, TimeZones.DEFAULT);
	}

	public static Date addMilliseconds(Date date, int amount, TimeZones timeZone) {
		return add(date, Calendar.MILLISECOND, amount, timeZone);
	}

	/**
	 * Adds to a date returning a new object. The original date object is
	 * unchanged.
	 *
	 * @param date
	 *            the date, not null
	 * @param calendarField
	 *            the calendar field to add to
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date add(Date date, int calendarField, int amount, TimeZones timeZone) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar c = Calendar.getInstance(resolveTimeZone(timeZone));
		c.setTime(date);
		c.add(calendarField, amount);
		return c.getTime();
	}

	public static String format(long date) {
		return format(parse(date), DateFormats.DEFAULT_PATTERN, TimeZones.DEFAULT);
	}

	public static String format(Date date) {
		return format(date, DateFormats.DEFAULT_PATTERN, TimeZones.DEFAULT);
	}

	public static String format(Date date, DateFormats dateFormat, TimeZones timeZone) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		SimpleDateFormat formatter = new SimpleDateFormat();
		formatter.applyPattern(resolveDateFormat(dateFormat));
		formatter.setTimeZone(resolveTimeZone(timeZone));
		formatter.setLenient(false);
		return formatter.format(date);
	}

	public static Date parse(String dateStr) throws ParseException {
		return parse(dateStr, DateFormats.DEFAULT_PATTERN, TimeZones.DEFAULT);
	}

	public static Date parse(long longDate) {
		if (longDate == 0L) {
			throw new IllegalArgumentException("Date is not correct");
		}
		return new Date(longDate);
	}

	public static Date parse(String dateStr, DateFormats dateFormat, TimeZones timeZone) throws ParseException {
		if (dateStr == null) {
			throw new IllegalArgumentException("The date must not be null");
		}

		SimpleDateFormat parser = new SimpleDateFormat();
		parser.applyPattern(resolveDateFormat(dateFormat));
		parser.setTimeZone(resolveTimeZone(timeZone));
		parser.setLenient(false);
		return parser.parse(dateStr);
	}

	public static TimeZone resolveTimeZone(TimeZones timeZone) {
		return TimeZone.getTimeZone(timeZone.zone());
	}

	public static String resolveDateFormat(DateFormats dateFormat) {
		return dateFormat.format();
	}
}
