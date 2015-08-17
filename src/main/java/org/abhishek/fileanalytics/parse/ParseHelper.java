package org.abhishek.fileanalytics.parse;

import org.abhishek.fileanalytics.dto.Validatable;
import org.abhishek.fileanalytics.lifecycle.Destroyable;
import org.abhishek.fileanalytics.lifecycle.Initializable;

public interface ParseHelper<E> extends Initializable, Destroyable, Validatable {
    E parseInternal(char[] lineData, int startPosn, int endPosn);
}
