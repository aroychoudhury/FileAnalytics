package org.abhishek.fileanalytics.process;

import org.abhishek.fileanalytics.lifecycle.Destroyable;
import org.abhishek.fileanalytics.lifecycle.Initializable;

public interface Processor extends Initializable, Destroyable {
    void process();

    boolean execute(char[] lineChars);
}
