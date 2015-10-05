/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.fileanalytics.parse.impl;

import org.abhishek.fileanalytics.constants.MatchTypes;
import org.abhishek.fileanalytics.exception.ValidationFailureException;

public class MatchEndingPositionBasedParser<E> extends CharacterBasedParser<E> {
    /**
     * Fragment start position; this is calculated based on the input character
     * array indices. All lengths are calculated as number of Characters, unless
     * specified.
     * 
     * Possible values : 0 and above.
     */
    protected int startPosition = DEFAULT;

    public MatchEndingPositionBasedParser(String startSeparator) {
        super(startSeparator, MatchTypes.END);
    }

    public MatchEndingPositionBasedParser(String startSeparator, int startPosition) {
        super(startSeparator, MatchTypes.END);
        this.startPosition = startPosition;
    }

    @Override
    public int calculateStartPosition(char[] lineData, int prevEndPosn) {
        if (DEFAULT == this.startPosition) {
            return 0; // Full length
        } else {
            return this.startPosition;
        }
    }

    @Override
    public int calculateEndPosition(char[] lineData, int startPosn) {
        return this.searchDefault(lineData);
    }

    @Override
    public boolean validate() {
        if (DEFAULT != this.startPosition && ZERO > this.startPosition) {
            throw new ValidationFailureException("Start Position cannot be zero or negative : " + this.startPosition);
        }
        return super.validate();
    }
}
