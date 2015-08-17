package org.abhishek.fileanalytics.parse.impl;

import org.abhishek.fileanalytics.constants.MatchTypes;
import org.abhishek.fileanalytics.exception.ValidationFailureException;

public class MatchStartingLengthBasedParser<E> extends CharacterBasedParser<E> {
    /**
     * Minimum Length of the fragment. All lengths are calculated as number of
     * Characters, unless specified.
     * 
     * If this is set to -10, it means that the entire fragment from the
     * starting position needs to be considered.
     * 
     * Possible values : 1 and above.
     */
    private int fragmentLength = DEFAULT;

    public MatchStartingLengthBasedParser(String startSeparator) {
        super(startSeparator, MatchTypes.START);
    }

    public MatchStartingLengthBasedParser(String startSeparator, int fragmentLength) {
        super(startSeparator, MatchTypes.START);
        this.fragmentLength = fragmentLength;
    }

    @Override
    public int calculateStartPosition(char[] lineData, int prevEndPosn) {
        if (DEFAULT == prevEndPosn || ZERO > prevEndPosn) {
            if (DEFAULT != this.fragmentLength) {
                return this.searchTo(lineData, this.fragmentLength);
            }
            return this.searchDefault(lineData);
        } else {
            if (DEFAULT != this.fragmentLength) {
                return this.searchBetween(lineData, (prevEndPosn + 1), (prevEndPosn + 1 + this.fragmentLength));
            }
            return this.searchFrom(lineData, (prevEndPosn + 1));
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
        if (DEFAULT != this.fragmentLength && ZERO > this.fragmentLength) {
            throw new ValidationFailureException("Length is incorrect : " + this.fragmentLength);
        }
        return super.validate();
    }
}
