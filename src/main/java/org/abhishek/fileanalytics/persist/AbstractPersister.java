package org.abhishek.fileanalytics.persist;

import org.abhishek.fileanalytics.dto.Persistable;

public abstract class AbstractPersister<K, V extends Persistable> implements Persister<K, V> {
	@Override
	public boolean isPersistable() {
		return true;
	}
}
