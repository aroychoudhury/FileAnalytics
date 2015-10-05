/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.fileanalytics.lifecycle;

public interface Destroyable {
	void destroy();

	boolean destroyed();
}
