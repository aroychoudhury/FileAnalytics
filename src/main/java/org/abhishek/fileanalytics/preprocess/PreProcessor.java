/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.fileanalytics.preprocess;

/**
 * <tt>PreProcessor</tt> parses the file contents and stores a set of File
 * metadata information.
 * 
 * The information mostly contains the byte content information and line
 * numbers; this information is leveraged during the actual file processing.
 * 
 * @author abhishek
 * @since 1.0
 * @param <E>
 *            Placeholder to return any data after the file parsing.
 */
public interface PreProcessor<E> {

    /**
     * This method is responsible for the main preprocessing. All implementing
     * classes needs to mandatorily implement this method.
     * 
     * Data parsed in the preprocessing step can be accessed using the
     * {@link org.abhishek.fileanalytics.preprocess.PreProcessor#getContent()} method.
     * 
     * @author abhishek
     * @since 1.0
     */
    void preprocess();

    /**
     * Any parsed data which needs to be returned needs to be returned using
     * this method.
     * 
     * The default return type needs to be declared as part of the class
     * signature.
     * 
     * @return The content from the preprocessing.
     * @author abhishek
     * @since 1.0
     */
    E getContent();

}
