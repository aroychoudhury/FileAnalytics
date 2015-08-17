package org.abhishek.fileanalytics.convert.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.abhishek.fileanalytics.convert.Converter;

public class BasicFilesArrayConverter implements Converter<List<String>, File[]> {

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
