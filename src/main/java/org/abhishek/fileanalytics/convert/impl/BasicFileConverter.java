package org.abhishek.fileanalytics.convert.impl;

import java.io.File;

import org.abhishek.fileanalytics.convert.Converter;

public class BasicFileConverter implements Converter<String, File> {

    @Override
    public String convert(File file) {
        if (file.isDirectory()) {
            return "_" + file.getName();
        } else {
            return file.getName();
        }
    }

}
