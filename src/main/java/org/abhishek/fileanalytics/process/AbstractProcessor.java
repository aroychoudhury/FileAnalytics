/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.fileanalytics.process;

import org.abhishek.fileanalytics.lifecycle.AbstractValidater;
import org.abhishek.fileanalytics.lifecycle.Destroyable;
import org.abhishek.fileanalytics.lifecycle.Initializable;
import org.abhishek.fileanalytics.lifecycle.Validatable;

/**
 * This class provides the default implementations for some of the processor
 * methods.
 * 
 * AbstractProcessor does not provide an implementation for the process method
 * 
 * @author abhishek
 * @since 1.0
 */
public abstract class AbstractProcessor<E, F> 
                extends AbstractValidater 
                implements Processor<E, F>, Initializable, Destroyable, Validatable {

    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.process.Processor#execute(char[])
     */
    @Override
    public boolean execute(char[] lineChars) {
        return true;
    }

    /**
     * The default implementation returns a NULL and should hence will need to
     * be checked for NULL by the client.
     * 
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.process.Processor#getContent()
     */
    @Override
    public E getContent() {
        return null;
    }

    /**
     * The default implementation sets nothing and hence makes this step
     * optional in the process.
     * 
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.process.Processor#setMetadata(java.lang.Object)
     */
    @Override
    public void setMetadata(F metadata) {
        // set the metadata
    }

}
