package org.abhishek.fileanalytics.factory;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import org.abhishek.fileanalytics.exception.ConfigurationFailureException;
import org.abhishek.fileanalytics.exception.NoSuchConfigurationException;
import org.abhishek.fileanalytics.parse.AbstractParser;
import org.abhishek.fileanalytics.parse.Parser;
import org.abhishek.fileanalytics.utils.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("rawtypes")
public class PreProcesserFactory {
	private static final Logger logger = LoggerFactory.getLogger(PreProcesserFactory.class);

	/* Variables start here */
	private static final PreProcesserFactory INSTANCE = new PreProcesserFactory();
	private Map<String, SoftReference<AbstractParser>> parsers = null;

	static {
		INSTANCE.parsers = new HashMap<String, SoftReference<AbstractParser>>();

		Class[] clazzes = getPackageClasses();
		if (null != clazzes && 0 < clazzes.length) {
			for (Class clazz : clazzes) {
				addParser(clazz);
			}
		}
	}

	private PreProcesserFactory() {
		super();
	}

	public static boolean addParser(Class clazz) {
		String clazzName = clazz.getName();
		if (contains(clazzName) && null != get(clazzName)) {
			return false;
		}

		INSTANCE.add(clazz, clazzName);
		logger.info("Parser added for : ", clazzName);

		return true;
	}

	public static boolean addParser(String clazzName) {
		try {
			return addParser(Class.forName(clazzName));
		} catch (ClassNotFoundException e) {
			logger.error("Failed to add Parser");
		}
		return false;
	}

	public static Parser getParser(Class<? extends Parser> clazz) {
		if (null == clazz) {
			throw new NoSuchConfigurationException("Configuration does not exist for : " + clazz);
		}
		return getParser(clazz.getName());
	}

	public static AbstractParser getParser(String clazzName) {
		if (contains(clazzName)) {
			SoftReference<AbstractParser> parser = get(clazzName);
			if (null == parser || null == parser.get()) {
				logger.warn("Parser instance for the key : {} was garbage collected. Creating fresh.", clazzName);

				// if add fails, throw Exception
				if (!addParser(clazzName)) {
					throw new ConfigurationFailureException("Parser could not be added : " + clazzName);
				}
				parser = get(clazzName);
			}
			return parser.get();
		} else {
			throw new NoSuchConfigurationException("Configuration does not exist for : " + clazzName);
		}
	}

	private void add(Class clazz, String clazzName) {
		synchronized (parsers) {
			try {
			    AbstractParser parserInst = (AbstractParser) clazz.newInstance();
				parserInst.initialize();

				parsers.put(
					clazzName, // This is the class name with package
					new SoftReference<AbstractParser>(parserInst));
			} catch (InstantiationException e) {
				throw new ConfigurationFailureException("Class instantiation failed for : " + clazz.getName(), e);
			} catch (IllegalAccessException e) {
				throw new ConfigurationFailureException("Illegal Access for : " + clazz.getName(), e);
			}
		}
	}

	private static boolean contains(String clazzName) {
		if (null == clazzName || "".equals(clazzName.trim())) {
			return false;
		}
		return INSTANCE.parsers.containsKey(clazzName);
	}

	private static SoftReference<AbstractParser> get(String clazzName) {
		return INSTANCE.parsers.get(clazzName);
	}

	private static Class[] getPackageClasses() {
		try {
			return ClassUtils.getClasses(Parser.class);
		} catch (ClassNotFoundException e) {
			throw new ConfigurationFailureException("Class loading failed for Persister", e);
		} catch (IOException e) {
			throw new ConfigurationFailureException("IO Exception caught while Class loading for Persister", e);
		}
	}
}
