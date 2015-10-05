/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.fileanalytics.lifecycle;

import org.abhishek.fileanalytics.constants.PersistTypes;

/**
 * Indicates that the state of this class will need to be stored for future use.
 * 
 * @author abhishek
 * @since 1.0
 */
public interface Persistable {

    /**
     * Describes the storage mechanism.
     * 
     * All types of storage mechanisms need to be configured in
     * {@link org.abhishek.fileanalytics.constants.PersistTypes}.
     * 
     * @return The type of storage being used by the implementation.
     * @author abhishek
     * @since 1.0
     */
    PersistTypes persistType();

}
