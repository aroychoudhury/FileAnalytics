package org.abhishek.fileanalytics.parse;

import org.abhishek.fileanalytics.dto.Validatable;
import org.abhishek.fileanalytics.dto.yield.ParseResult;
import org.abhishek.fileanalytics.lifecycle.Destroyable;
import org.abhishek.fileanalytics.lifecycle.Initializable;

public interface Parser<E> extends Initializable, Destroyable, Validatable {
    /** Predefined constants */
    public static final int  DEFAULT        = -10;
    public static final int  ZERO           = 0;

    ParseResult<E> parseFragment(char[] lineData, int prevEndPosn);

    int calculateStartPosition(char[] lineData, int prevEndPosn);

    int calculateEndPosition(char[] lineData, int startPosn);

    boolean IsContinueOnExc();

    void setContinueOnExc(boolean continueOnExc);
}
