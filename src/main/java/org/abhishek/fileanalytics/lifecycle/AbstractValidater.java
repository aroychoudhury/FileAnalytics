/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.fileanalytics.lifecycle;

/**
 * <tt>AbstractValidater</tt> provides the default validate method
 * implementation.
 * 
 * All validatable classes must also be initializable and destroyable.
 * 
 * @author abhishek
 * @since 1.0
 */
public class AbstractValidater extends AbstractInitializer implements Validatable {

    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.lifecycle.Validatable#validate()
     */
    @Override
    public boolean validate() {
        return false;
    }

}
