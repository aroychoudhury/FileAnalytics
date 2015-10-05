/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.fileanalytics.convert.impl;

import java.text.ParseException;
import java.util.Date;

import org.abhishek.fileanalytics.constants.DateFormats;
import org.abhishek.fileanalytics.constants.TimeZones;
import org.abhishek.fileanalytics.convert.Converter;
import org.abhishek.fileanalytics.utils.DateUtils;

/**
 * Coverts a {@link java.lang.Character} array into a date.
 * 
 * Leverages the DateUtils for the conversion. Configurations for time-zone and
 * display format for the date needs to be configured in the
 * {@link org.abhishek.fileanalytics.constants.DateFormats} class and updated
 * below.
 * 
 * @author abhishek
 * @since 1.0
 */
public class DefaultDateConverter implements Converter<Date, Character[]> {

    /**
     * @param chars
     *            The character array which needs to be coverted.
     * @return The converted date.
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.convert.Converter#convert(java.lang.Object)
     */
    @Override
    public Date convert(Character[] chars) {
        try {
            return DateUtils.parse(
                chars.toString(),
                DateFormats.DEFAULT_PATTERN,
                TimeZones.DEFAULT);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

}
