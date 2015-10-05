/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.fileanalytics.convert;

/**
 * Provide methods to convert from one data type to another, in this case the
 * class of type F is converted to E.
 * 
 * @author abhishek
 * @since 1.0
 * @param <E>
 *            The output type after conversion.
 * @param <F>
 *            The input type before conversion.
 */
public interface Converter<E, F> {

    /**
     * The method which implements the conversion logic.
     * 
     * @param input
     *            The input data that needs to be converted.
     * @return The converted data object.
     * @author abhishek
     * @since 1.0
     */
    E convert(F input);

}
