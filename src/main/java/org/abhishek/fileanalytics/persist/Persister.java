/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.fileanalytics.persist;

import org.abhishek.fileanalytics.exception.PersistFailureException;
import org.abhishek.fileanalytics.lifecycle.Persistable;

public interface Persister<K, V extends Persistable> {
	boolean isPersistable();
	boolean exists(K key);
	void persist(K key, V value) throws PersistFailureException;
	V retrieve(K key) throws PersistFailureException;
	boolean delete(K key) throws PersistFailureException;
}
