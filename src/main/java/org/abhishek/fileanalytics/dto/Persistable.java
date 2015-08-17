package org.abhishek.fileanalytics.dto;

import org.abhishek.fileanalytics.constants.PersistTypes;

/**
 * Classes implementing this interface indicate that the contents of this class
 * will need to be stored for future use. Storage can be in Files or in
 * Database.
 * 
 * @author abhishek
 * @since 1.0
 */
public interface Persistable {
    PersistTypes persistType();
}
