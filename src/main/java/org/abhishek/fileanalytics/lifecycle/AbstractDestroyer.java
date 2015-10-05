/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.fileanalytics.lifecycle;

/**
 * Provides the default implementations for the destroying methods.
 * 
 * @author abhishek
 * @since 1.0
 */
public class AbstractDestroyer implements Destroyable {
    protected boolean destroyed = false;

    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.lifecycle.Destroyable#destroy()
     */
    @Override
    public void destroy() {
        this.destroyed = true;
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.lifecycle.Destroyable#destroyed()
     */
    @Override
    public boolean destroyed() {
        return this.destroyed;
    }

}
