package org.abhishek.fileanalytics.convert.impl;

import java.text.ParseException;
import java.util.Date;

import org.abhishek.fileanalytics.convert.Converter;
import org.abhishek.fileanalytics.utils.DateUtils;

public class DefaultDateConverter implements Converter<Date, Character[]> {
	public Date convert(Character[] chars) {
		try {
			return DateUtils.parse(new String(new char[1]));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}
}
