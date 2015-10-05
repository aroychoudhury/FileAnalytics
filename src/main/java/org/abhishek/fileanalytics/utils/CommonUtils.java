/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.fileanalytics.utils;

import java.io.Closeable;
import java.io.IOException;

import org.abhishek.fileanalytics.exception.ConfigurationFailureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtils {
	private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);

	public static void closeQuietly(Closeable is) {
		try {
			is.close();
		} catch (IOException e) {
			//logger.catching(Level.ERROR, e);
			logger.error("Error in closing quieltly", e);
		}
	}

	public static int findStartIndex(char[] pattern, char[] source) {
		return findStartIndex(0, pattern, source);
	}

	public static int findStartIndex(int startPosn, char[] pattern, char[] source) {
		int patternLength = pattern.length;
		int sourceLength = source.length;
		for (int idx = startPosn; idx < sourceLength; idx++) {
			logger.info("idx : {}", idx);
			if (checkMatch(pattern, source, idx)) {
				return idx;
			} else {
				idx = idx + (patternLength - 1);
			}
		}
		return -1;
	}

	private static boolean checkMatch(char[] pattern, char[] source, int sourceStartIdx) {
		int idx = sourceStartIdx;
		for (char chr : pattern) {
			if (chr != source[idx]) {
				return false;
			}
			idx++;
		}
		return true;
	}

	public static void main(String[] args) {
		logger.info("This is my message");
		String pattern = "Abhishek";
		String source = "AbhixxxxAbhishek";
		logger.info("Value returned : {}", checkMatch(pattern.toCharArray(), source.toCharArray(), 8));
		logger.info("Value returned : {}", findStartIndex(pattern.toCharArray(), source.toCharArray()));
	}

	@SuppressWarnings("rawtypes")
	public static Class getClazz(String clazzName) {
		try {
			return Class.forName(clazzName);
		} catch (ClassNotFoundException e) {
			logger.error("Class could not be loaded : " + clazzName, e);
			throw new ConfigurationFailureException("Class could not be loaded : " + clazzName, e);
		}
	}
}
