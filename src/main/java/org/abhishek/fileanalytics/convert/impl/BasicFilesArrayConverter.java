/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.fileanalytics.convert.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.abhishek.fileanalytics.convert.Converter;

/**
 * Coverts a list of Files of type {@link java.io.File} into a simple list of
 * file names.
 * 
 * @author abhishek
 * @since 1.0
 */
public class BasicFilesArrayConverter implements Converter<List<String>, File[]> {

    /**
     * @param files
     *            The file array which needs to be coverted.
     * @return List of file names after conversion.
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.convert.Converter#convert(java.lang.Object)
     */
    @Override
    public List<String> convert(File[] files) {
        BasicFileConverter fileConverter = new BasicFileConverter();
        List<String> convFiles = new ArrayList<String>(files.length);

        for (File file : files) {
            if (file.isHidden()) {
                continue;
            }
            convFiles.add(fileConverter.convert(file));
        }
        return convFiles;
    }

}
