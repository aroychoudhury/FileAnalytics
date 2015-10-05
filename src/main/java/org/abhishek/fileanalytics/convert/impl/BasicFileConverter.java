/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.fileanalytics.convert.impl;

import java.io.File;

import org.abhishek.fileanalytics.convert.Converter;

/**
 * Coverts a File Object of type {@link java.io.File} into a simple file name.
 * 
 * @author abhishek
 * @since 1.0
 */
public class BasicFileConverter implements Converter<String, File> {

    /**
     * @param file
     *            The file object which needs to be coverted.
     * @return The converted file name.
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.convert.Converter#convert(java.lang.Object)
     */
    @Override
    public String convert(File file) {
        if (file.isDirectory()) {
            return "_" + file.getName();
        } else {
            return file.getName();
        }
    }

}
