/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.fileanalytics.lifecycle;

/**
 * Indicates whether the implementation can be validated.
 * 
 * @author abhishek
 * @since 1.0
 */
public interface Validatable {

    /**
     * Contains the validation logic for this implementation.
     * 
     * Does not provide a default implementation.
     * 
     * @return true if the validation was successful else returns false.
     * @author abhishek
     * @since 1.0
     */
    boolean validate();

}
