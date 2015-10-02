package org.abhishek.fileanalytics.utils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.abhishek.fileanalytics.constants.DateFormats;
import org.abhishek.fileanalytics.constants.TimeZones;
import org.abhishek.fileanalytics.convert.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * Cleans a directory without deleting it.
     *
     * @param directory
     *            directory to clean
     * @throws IOException
     *             in case cleaning is unsuccessful
     */
    public static File cleanDirectory(String directoryPath) {
        if (null == directoryPath || "".equals(directoryPath.trim())) {
            throw new IllegalArgumentException(directoryPath + " cannot be Null/Empty");
        }
        return cleanDirectory(new File(directoryPath));
    }

    public static File cleanDirectory(File directory) {
        if (null == directory || !directory.exists()) {
            throw new IllegalArgumentException(directory + " does not exist");
        }

        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory + " is not a directory");
        }

        File[] files = directory.listFiles();
        if (files == null) { // null if security restricted
            throw new SecurityException("Failed to list contents of " + directory);
        }

        logger.debug("Directory details  : {} [ {} ]", directory.getAbsolutePath(), files.length);

        // all files in the folder needs to be deleted
        // before all files can be deleted
        for (File file : files) {
            boolean success = file.delete();
            // try again
            if (!success) {
                file.delete();
            }
        }
        return directory;
    }

    public static void deleteDirectory(String directoryPath) {
        cleanDirectory(directoryPath).delete();
    }

    public static void deleteDirectory(File directory) {
        cleanDirectory(directory).delete();
    }

    public static <E> List<E> listContents(String directoryPath, Converter<List<E>, File[]> converter) {
        if (null == directoryPath || "".equals(directoryPath.trim())) {
            throw new IllegalArgumentException("Directory Path cannot be Null/Empty");
        }
        return listContents(new File(directoryPath), converter);
    }

    public static <E> List<E> listContents(File directory, Converter<List<E>, File[]> converter) {
        if (null == directory || !directory.exists()) {
            throw new IllegalArgumentException(directory + " does not exist");
        }

        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory + " is not a directory");
        }

        File[] foundFiles = directory.listFiles();
        if (null == foundFiles) { // null if security restricted
            throw new SecurityException("Failed to list contents of " + directory);
        }

        logger.debug("Directory details  : {} [ {} ]", directory.getAbsolutePath(), foundFiles.length);

        return converter.convert(foundFiles);
    }

    public static String serilizedFileName(String fileName) {
        if (null == fileName || "".equals(fileName.trim())) {
            throw new IllegalArgumentException("File Name cannot be Null/Empty");
        }
        return serilizedFileName(fileName, "");
    }

    public static String serilizedFileName(String fileName, Date lastModified) {
        if (null == lastModified) {
            throw new IllegalArgumentException("Last Modified cannot be Null");
        }
        return serilizedFileName(fileName, DateUtils.format(lastModified, DateFormats.SIMPLE_CHAR_PATTERN, TimeZones.DEFAULT));
    }

    public static String serilizedFileName(String fileName, String lastModifiedStr) {
        if (null == fileName || "".equals(fileName.trim())) {
            throw new IllegalArgumentException("File Name cannot be Null/Empty");
        }
        StringBuilder tmpBuilder = new StringBuilder(fileName.substring(0, fileName.indexOf(".")).toLowerCase());

        if (null == lastModifiedStr) {
            throw new IllegalArgumentException("Last Modified cannot be Null/Empty");
        } else {
            tmpBuilder
                .append('.')
                .append(lastModifiedStr);
        }
        tmpBuilder.append(".ser");

        logger.debug("File Name : {}", tmpBuilder.toString());
        return tmpBuilder.toString();
    }

    public static boolean fileExists(String filePath) {
        if (null == filePath || "".equals(filePath.trim())) {
            throw new IllegalArgumentException("File Path cannot be Null/Empty");
        }
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }
        return true;
    }

    public static <E> E fileMeta(String filePath, Converter<E, File> converter) {
        if (null == filePath || "".equals(filePath.trim())) {
            throw new IllegalArgumentException("File Path cannot be Null/Empty");
        }
        return fileMeta(new File(filePath), converter);
    }

    public static <E> E fileMeta(File file, Converter<E, File> converter) {
        if (null == file || !file.exists()) {
            throw new IllegalArgumentException(file + " does not exist");
        }
        return converter.convert(file);
    }

    public static void deleteFile(String filePath) {
        if (null == filePath || "".equals(filePath.trim())) {
            throw new IllegalArgumentException("File Path cannot be Null/Empty");
        }
        deleteFile(new File(filePath));
    }

    public static void deleteFile(File file) {
        if (null == file || !file.exists()) {
            throw new IllegalArgumentException(file + " does not exist");
        }
        file.delete();
    }

    public static String extractFileName(String fileName) {
        if (null == fileName || "".equals(fileName.trim())) {
            throw new IllegalArgumentException("File Name cannot be Null/Empty");
        }
        int extensionStartPt = fileName.lastIndexOf(".");
        return fileName.substring(0, extensionStartPt);
    }

    public static String extractFileExtension(String fileName) {
        if (null == fileName || "".equals(fileName.trim())) {
            throw new IllegalArgumentException("File Name cannot be Null/Empty");
        }
        int extensionStartPt = fileName.lastIndexOf(".");
        return fileName.substring(extensionStartPt + 1);
    }
}
