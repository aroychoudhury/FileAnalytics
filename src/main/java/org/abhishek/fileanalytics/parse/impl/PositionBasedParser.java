/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.fileanalytics.parse.impl;

import org.abhishek.fileanalytics.exception.ParseFailureException;
import org.abhishek.fileanalytics.exception.ValidationFailureException;
import org.abhishek.fileanalytics.parse.AbstractFragmentParser;

public class PositionBasedParser<E> extends AbstractFragmentParser<E> {
    /**
     * Fragment start position; this is calculated based on the input character
     * array indices. All lengths are calculated as number of Characters, unless
     * specified.
     * 
     * Possible values : 0 and above.
     */
    protected int startPosition = DEFAULT;

    /**
     * Fragment end position; this is calculated based on the input character
     * array indices. All lengths are calculated as number of Characters, unless
     * specified.
     * 
     * Possible values : 1 and above.
     */
    private int   endPosition   = DEFAULT;

    public PositionBasedParser(int startPosition) {
        this.startPosition = startPosition;
    }

    public PositionBasedParser(int startPosition, int endPosition) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    @Override
    public int calculateStartPosition(char[] lineData, int prevEndPosn) {
        if (prevEndPosn >= this.startPosition) {
            throw new ParseFailureException("Overlapping positions met : " + prevEndPosn + " | " + this.startPosition);
        }
        return this.startPosition;
    }

    @Override
    public int calculateEndPosition(char[] lineData, int startPosn) {
        if (DEFAULT == this.endPosition) {
            return lineData.length; // Full length
        } else {
            return this.endPosition;
        }
    }

    @Override
    public boolean validate() {
        if (DEFAULT != startPosition && ZERO > this.startPosition) {
            throw new ValidationFailureException("Start Position cannot be Negative : " + this.startPosition);
        }
        if (DEFAULT != endPosition && ZERO > this.endPosition) {
            throw new ValidationFailureException("End Position cannot be Negative : " + this.endPosition);
        }
        return super.validate();
    }
}
