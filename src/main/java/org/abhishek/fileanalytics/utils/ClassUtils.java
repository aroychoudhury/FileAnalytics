package org.abhishek.fileanalytics.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ClassUtils {
	@SuppressWarnings({
		"rawtypes"
	})
	public static Class[] getClasses(Class clazz) throws ClassNotFoundException, IOException {
		if (clazz == null) {
			return new Class[0];
		}
		return getClasses(getPackageName(clazz));
	}

	/**
	 * Source : http://www.dzone.com/snippets/get-all-classes-within-package
	 * 
	 * Scans all classes accessible from the context class loader which belong
	 * to the given package and sub-packages.
	 *
	 * @param packageName
	 *            The base package
	 * @return The classes
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings({
		"rawtypes"
	})
	public static Class[] getClasses(String packageName) throws ClassNotFoundException, IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assert classLoader != null;
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			System.out.println(resource.getFile());
			dirs.add(new File(resource.getFile()));
		}
		ArrayList<Class> classes = new ArrayList<Class>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes.toArray(new Class[classes.size()]);
	}

	/**
	 * Source : http://www.dzone.com/snippets/get-all-classes-within-package
	 * 
	 * Recursive method used to find all classes in a given directory and
	 * sub-directories.
	 *
	 * @param directory
	 *            The base directory
	 * @param packageName
	 *            The package name for classes found inside the base directory
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("rawtypes")
	public static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
		List<Class> classes = new ArrayList<Class>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				Class clazz = Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6));
				System.out.println(clazz.isLocalClass() + " | " + clazz.isMemberClass());
				if (!clazz.isInterface() && !clazz.isMemberClass() && !Modifier.isAbstract(clazz.getModifiers())) {
					classes.add(clazz);
				}
			}
		}
		return classes;
	}

	/**
	 * <p>
	 * Gets the package name of a <code>Class</code>.
	 * </p>
	 *
	 * @param clazz
	 *            the class to get the package name for, may be
	 *            <code>null</code>.
	 * @return the package name or an empty string
	 */
	@SuppressWarnings("rawtypes")
	public static String getPackageName(Class clazz) {
		if (clazz == null) {
			return "";
		}
		return getPackageName(clazz.getName());
	}

	/**
	 * <p>
	 * Gets the package name from a <code>String</code>.
	 * </p>
	 *
	 * <p>
	 * The string passed in is assumed to be a class name - it is not checked.
	 * </p>
	 * <p>
	 * If the class is unpackaged, return an empty string.
	 * </p>
	 *
	 * @param className
	 *            the className to get the package name for, may be
	 *            <code>null</code>
	 * @return the package name or an empty string
	 */
	public static String getPackageName(String className) {
		if (className == null || className.length() == 0) {
			return "";
		}

		// Strip array encoding
		while (className.charAt(0) == '[') {
			className = className.substring(1);
		}
		// Strip Object type encoding
		if (className.charAt(0) == 'L' && className.charAt(className.length() - 1) == ';') {
			className = className.substring(1);
		}

		int i = className.lastIndexOf(".");
		if (i == -1) {
			return "";
		}
		return className.substring(0, i);
	}
}
