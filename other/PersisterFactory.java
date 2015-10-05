package org.abhishek.fileanalytics.factory;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import org.abhishek.fileanalytics.exception.ConfigurationFailureException;
import org.abhishek.fileanalytics.exception.NoSuchConfigurationException;
import org.abhishek.fileanalytics.persist.Persister;
import org.abhishek.fileanalytics.utils.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("rawtypes")
public class PersisterFactory {
	private static final Logger logger = LoggerFactory.getLogger(PersisterFactory.class);

	/* Variables start here */
	private static final PersisterFactory INSTANCE = new PersisterFactory();
	private Map<String, SoftReference<Persister>> persisters = null;

	static {
		INSTANCE.persisters = new HashMap<String, SoftReference<Persister>>();

		Class[] clazzes = getPackageClasses();
		if (null != clazzes && 0 < clazzes.length) {
			for (Class clazz : clazzes) {
				addPersister(clazz);
			}
		} else {
			logger.warn("No Persisters configured");
		}
	}

	private PersisterFactory() {
		super();
	}

	public static boolean addPersister(Class clazz) {
		String clazzName = clazz.getName();
		if (contains(clazzName) && null != get(clazzName)) {
			return false;
		}

		INSTANCE.add(clazz, clazzName);
		logger.info("Persister added for : ", clazzName);

		return true;
	}

	public static boolean addPersister(String clazzName) {
		try {
			return addPersister(Class.forName(clazzName));
		} catch (ClassNotFoundException e) {
			logger.error("Failed to add Persister");
		}
		return false;
	}

	public static Persister getPersister(Class<? extends Persister> clazz) {
		return getPersister(clazz.getName());
	}

	public static Persister getPersister(String clazzName) {
		if (contains(clazzName)) {
			SoftReference<Persister> persister = get(clazzName);
			if (null == persister || null == persister.get()) {
				logger.warn("Persister instance for the key : {} was garbage collected. Creating fresh.", clazzName);

				// if add fails, throw Exception
				if (!addPersister(clazzName)) {
					throw new ConfigurationFailureException("Persister could not be added : " + clazzName);
				}
				persister = get(clazzName);
			}
			return persister.get();
		} else {
			throw new NoSuchConfigurationException("Configuration does not exist for : " + clazzName);
		}
	}

	private void add(Class clazz, String clazzName) {
		synchronized (persisters) {
			try {
				Persister persisterInst = (Persister) clazz.newInstance();
				persisters.put(
					clazzName, // This is the class name with package
					new SoftReference<Persister>(persisterInst));
			} catch (InstantiationException e) {
				logger.error("Class instantiation failed for : " + clazz.getName(), e);
			} catch (IllegalAccessException e) {
				logger.error("Illegal Access for : " + clazz.getName(), e);
			}
		}
	}

	private static boolean contains(String clazzName) {
		if (null == clazzName || "".equals(clazzName.trim())) {
			return false;
		}
		return INSTANCE.persisters.containsKey(clazzName);
	}

	private static SoftReference<Persister> get(String clazzName) {
		return INSTANCE.persisters.get(clazzName);
	}

	private static Class[] getPackageClasses() {
		try {
			return ClassUtils.getClasses(Persister.class);
		} catch (ClassNotFoundException e) {
			throw new ConfigurationFailureException("Class loading failed for Persister", e);
		} catch (IOException e) {
			throw new ConfigurationFailureException("IO Exception caught while Class loading for Persister", e);
		}
	}
}
