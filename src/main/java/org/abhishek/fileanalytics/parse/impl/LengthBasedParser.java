package org.abhishek.fileanalytics.parse.impl;

import org.abhishek.fileanalytics.exception.ValidationFailureException;
import org.abhishek.fileanalytics.parse.AbstractParser;

public class LengthBasedParser<E> extends AbstractParser<E> {
    /**
     * Minimum Length of the fragment. All lengths are calculated as number of
     * Characters, unless specified.
     * 
     * If this is set to 0, it means that the entire fragment from the starting
     * position needs to be considered.
     * 
     * Possible values : 0 and above.
     */
    private int   fragmentLength   = DEFAULT;

    /**
     * Position of the fragment from the previous fragment. All lengths are
     * calculated as number of Characters, unless specified.
     * 
     * Default is initialized as negative. If remains -1 means that the target
     * Fragment lies next to the margin.
     */
    protected int distanceFromLast = DEFAULT;

    public LengthBasedParser(int distanceFromLast) {
        this.distanceFromLast = distanceFromLast;
    }

    public LengthBasedParser(int distanceFromLast, int fragmentLength) {
        this.distanceFromLast = distanceFromLast;
        this.fragmentLength = fragmentLength;
    }

    @Override
    public int calculateStartPosition(char[] lineData, int prevEndPosn) {
        if (DEFAULT == this.distanceFromLast) {
            return prevEndPosn + 1;
        } else {
            return prevEndPosn + this.distanceFromLast;
        }
    }

    @Override
    public int calculateEndPosition(char[] lineData, int startPosn) {
        if (DEFAULT == this.fragmentLength) {
            return lineData.length; // Full length
        } else {
            return startPosn + this.fragmentLength;
        }
    }

    @Override
    public boolean validate() {
        if (DEFAULT != fragmentLength && ZERO >= this.fragmentLength) {
            throw new ValidationFailureException("Fragment Length cannot be Zero or Negative : " + this.fragmentLength);
        }
        if (DEFAULT != distanceFromLast && ZERO > this.distanceFromLast) {
            throw new ValidationFailureException("Distance from Last Fragment cannot be Negative : " + this.distanceFromLast);
        }
        return super.validate();
    }
}
