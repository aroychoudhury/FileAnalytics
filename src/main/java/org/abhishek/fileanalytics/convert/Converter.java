package org.abhishek.fileanalytics.convert;

public interface Converter<E, F> {
	E convert(F chars);
}
