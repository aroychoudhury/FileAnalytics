package org.abhishek.fileanalytics.dto;

import org.abhishek.fileanalytics.constants.ConfigTypes;

/**
 * This interface is to be implemented by classes which act as configurators to
 * the application.
 * 
 * @author abhishek
 * @since 1.0
 */
public interface Configurable {
    ConfigTypes configurationType();
}
