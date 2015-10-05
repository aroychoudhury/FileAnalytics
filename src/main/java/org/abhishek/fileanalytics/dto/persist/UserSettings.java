/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.fileanalytics.dto.persist;

import java.io.Serializable;

import org.abhishek.fileanalytics.constants.PersistTypes;
import org.abhishek.fileanalytics.lifecycle.Persistable;

/**
 * User configuread settings which can be persisted to be reused across user
 * sessions.
 * 
 * @author abhishek
 * @since 1.0
 */
public class UserSettings implements Serializable, Persistable {
    private static final long serialVersionUID = 9205864585447068180L;

    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.lifecycle.Persistable#persistType()
     */
    @Override
    public PersistTypes persistType() {
        return PersistTypes.FILE;
    }
}
