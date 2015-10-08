/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.fileanalytics.process;

/**
 * <tt>Processor</tt> parses the file contents. It leverages the file metadata
 * information, parsers and helpers to fetch file contents.
 * 
 * The information mostly contains the byte content information and line
 * numbers; this information is leveraged during the actual file processing.
 * 
 * @author abhishek
 * @since 1.0
 * @param <E>
 *            Placeholder to return any data after the file parsing.
 */
public interface Processor<E, F> {

    /**
     * This method is responsible for the main processing. All implementing
     * classes needs to mandatorily implement this method.
     * 
     * Data parsed in the preprocessing step can be accessed using the
     * {@link org.abhishek.fileanalytics.process.Processor#getContent()} method.
     * 
     * @author abhishek
     * @since 1.0
     */
    void process();

    /**
     * The <tt>execute</tt> is the place from which the parsers are invoked.
     * 
     * @return Success indicator on whether the operation ended successfully.
     * @author abhishek
     * @since 1.0
     */
    boolean execute(char[] lineChars);

    /**
     * Any parsed data which needs to be returned needs to be returned using
     * this method.
     * 
     * The default return type needs to be declared as part of the class
     * signature.
     * 
     * @return The content from the processing.
     * @author abhishek
     * @since 1.0
     */
    E getContent();

    /**
     * Additional data that needs to be set after the class has been initialized
     * can be use this option.
     * 
     * @param metadata
     *            Metadata that assists in the processing.
     * @author abhishek
     * @since 1.0
     */
    void setMetadata(F metadata);

}
