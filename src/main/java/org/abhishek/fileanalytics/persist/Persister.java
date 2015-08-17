package org.abhishek.fileanalytics.persist;

import org.abhishek.fileanalytics.dto.Persistable;
import org.abhishek.fileanalytics.exception.PersistFailureException;

public interface Persister<K, V extends Persistable> {
	boolean isPersistable();
	boolean exists(K key);
	void persist(K key, V value) throws PersistFailureException;
	V retrieve(K key) throws PersistFailureException;
	boolean delete(K key) throws PersistFailureException;
}
