package org.abhishek.fileanalytics.parse;

public abstract class AbstractParseHelper<E> implements ParseHelper<E> {
    protected boolean initialized = false;

    @Override
    public boolean isIntialized() {
        return this.initialized;
    }

    @Override
    public void initialize() {
        this.initialized = true;
    }

    @Override
    public void destroy() {
    }

    @Override
    public boolean validate() {
        return true;
    }
}
