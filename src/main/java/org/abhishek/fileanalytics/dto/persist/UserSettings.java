package org.abhishek.fileanalytics.dto.persist;

import java.io.Serializable;

import org.abhishek.fileanalytics.constants.PersistTypes;
import org.abhishek.fileanalytics.dto.Persistable;

public class UserSettings implements Serializable, Persistable {
    private static final long serialVersionUID = 9205864585447068180L;

    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.dto.Persistable#persistType()
     */
    @Override
    public PersistTypes persistType() {
        return PersistTypes.FILE;
    }
}
