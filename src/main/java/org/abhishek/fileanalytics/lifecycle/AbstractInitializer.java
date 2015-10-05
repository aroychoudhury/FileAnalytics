/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.fileanalytics.lifecycle;

/**
 * <tt>AbstractInitializer</tt> provides the default implementations for the
 * initializing methods.
 * 
 * All initializable classes must also be destroyable. The vice-versa is
 * <i>not</i> however true.
 * 
 * @author abhishek
 * @since 1.0
 */
public class AbstractInitializer extends AbstractDestroyer implements Initializable, Destroyable {
    protected boolean intialized = false;

    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.lifecycle.Initializable#initialize()
     */
    @Override
    public void initialize() {
        this.intialized = true;
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.lifecycle.Initializable#intialized()
     */
    @Override
    public boolean intialized() {
        return this.intialized;
    }

}
