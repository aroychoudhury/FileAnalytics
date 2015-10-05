/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.fileanalytics.persist;

import org.abhishek.fileanalytics.lifecycle.Persistable;

public abstract class AbstractPersister<K, V extends Persistable> implements Persister<K, V> {
	@Override
	public boolean isPersistable() {
		return true;
	}
}
