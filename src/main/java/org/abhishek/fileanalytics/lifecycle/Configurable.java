/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.fileanalytics.lifecycle;

import org.abhishek.fileanalytics.constants.ConfigTypes;

/**
 * Implemented by classes which act as configurators to the application.
 * 
 * @author abhishek
 * @since 1.0
 */
public interface Configurable {

    /**
     * Method to convey the type of configuration that implementation provided.
     * 
     * Possible values need to be configured in
     * {@link org.abhishek.fileanalytics.constants.ConfigTypes}.
     * 
     * @return
     * @author abhishek
     * @since 1.0
     */
    ConfigTypes configurationType();

}
