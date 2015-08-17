package org.abhishek.fileanalytics.parse.impl;

import org.abhishek.fileanalytics.constants.MatchTypes;
import org.abhishek.fileanalytics.exception.ValidationFailureException;

public class MatchStartingPositionBasedParser<E> extends CharacterBasedParser<E> {
    /**
     * Fragment end position; this is calculated based on the input character
     * array indices. All lengths are calculated as number of Characters, unless
     * specified.
     * 
     * Possible values : 1 and above.
     */
    protected int endPosition = DEFAULT;

    public MatchStartingPositionBasedParser(String startSeparator) {
        super(startSeparator, MatchTypes.START);
    }

    public MatchStartingPositionBasedParser(String startSeparator, int endPosition) {
        super(startSeparator, MatchTypes.START);
        this.endPosition = endPosition;
    }

    @Override
    public int calculateStartPosition(char[] lineData, int prevEndPosn) {
        return this.searchDefault(lineData);
    }

    @Override
    public int calculateEndPosition(char[] lineData, int startPosn) {
        if (DEFAULT == this.endPosition) {
            return lineData.length - 1; // Full length
        } else {
            return this.endPosition;
        }
    }

    @Override
    public boolean validate() {
        if (DEFAULT != this.endPosition && ZERO >= this.endPosition) {
            throw new ValidationFailureException("End Position cannot be zero or negative : " + this.endPosition);
        }
        return super.validate();
    }
}
