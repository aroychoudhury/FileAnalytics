package org.abhishek.fileanalytics.process;

public abstract class AbstractProcessor implements Processor {
    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.lifecycle.Initializable#isIntialized()
     */
    @Override
    public boolean isIntialized() {
        return true;
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.lifecycle.Initializable#initialize()
     */
    @Override
    public void initialize() {
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.process.Processor#execute(char[])
     */
    @Override
    public boolean execute(char[] lineChars) {
        return false;
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.lifecycle.Destroyable#destroy()
     */
    @Override
    public void destroy() {
    }
}
