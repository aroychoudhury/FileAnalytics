/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.fileanalytics.preprocess;

import org.abhishek.fileanalytics.lifecycle.AbstractValidater;
import org.abhishek.fileanalytics.lifecycle.Destroyable;
import org.abhishek.fileanalytics.lifecycle.Initializable;
import org.abhishek.fileanalytics.lifecycle.Validatable;

/**
 * This class provides the default implementations for some of the preprocessor
 * methods.
 * 
 * AbstractPreProcessor does not provide an implementation for the
 * {@link org.abhishek.fileanalytics.preprocess.PreProcessor#preprocess()} method.
 * 
 * @author abhishek
 * @since 1.0
 */
public abstract class AbstractPreProcessor<E> extends AbstractValidater implements PreProcessor<E>, Initializable, Destroyable, Validatable {

    /**
     * The default implementation of
     * {@link org.abhishek.fileanalytics.preprocess.PreProcessor#getContent()} returns
     * a NULL and should hence will need to be checked for NULL by the client.
     * 
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.process.Processor#getContent()
     */
    @Override
    public E getContent() {
        return null;
    }

}
