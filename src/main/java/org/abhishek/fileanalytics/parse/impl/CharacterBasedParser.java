/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.fileanalytics.parse.impl;

import org.abhishek.fileanalytics.algo.StringMatcher;
import org.abhishek.fileanalytics.algo.impl.KnuthMorrisPrattAlgo;
import org.abhishek.fileanalytics.constants.MatchTypes;
import org.abhishek.fileanalytics.exception.ParseFailureException;
import org.abhishek.fileanalytics.exception.ValidationFailureException;
import org.abhishek.fileanalytics.parse.AbstractParser;

public class CharacterBasedParser<E> extends AbstractParser<E> {
    /**
     * Start Separator array indicates that the Fragment has ended. This is
     * probably the best way to track the growing Fragment.
     * 
     * For example, for a Fragment like '[2015:07:01 12:00:00AM]' - the end
     * separator would be [20; the 20 signifies the possible 2015/2016 values.
     */
    protected char[]             startSeparator    = null;

    /**
     * Length of the Start Separator character array.
     */
    protected int                startSeparatorLen = ZERO;

    /**
     * End Separator array indicates that the Fragment has ended. This is
     * probably the best way to track the growing Fragment.
     */
    protected char[]             endSeparator      = null;

    /**
     * Length of the End Separator character array.
     */
    protected int                endSeparatorLen   = ZERO;

    /**
     * Indicates whether to consider only the Start Separator or End Separator
     * or Both.
     */
    protected MatchTypes         matchType         = null;

    private StringMatcher startMatcher      = null;
    private StringMatcher endMatcher        = null;

    public CharacterBasedParser(String separator, MatchTypes matchType) {
        super();
        this.matchType = matchType;
        this.apply(separator);
    }

    public CharacterBasedParser(String startSeparator, String endSeparator) {
        this.matchType = MatchTypes.BOTH;
        this.applyStartSeparator(startSeparator);
        this.applyEndSeparator(endSeparator);
    }

    protected void apply(String separator) {
        if (null == separator || "".equals(separator.trim())) {
            throw new ValidationFailureException("Start Separator configured as Null/Empty");
        }
        this.applyStartSeparator(separator);
        this.applyEndSeparator(separator);
    }

    private void applyStartSeparator(String separator) {
        if (this.matchType.isStartMatcher()) {
            this.startSeparator = separator.trim().toCharArray();
            this.startSeparatorLen = this.startSeparator.length;
        }
    }

    private void applyEndSeparator(String separator) {
        if (this.matchType.isEndMatcher()) {
            this.endSeparator = separator.trim().toCharArray();
            this.endSeparatorLen = this.endSeparator.length;
        }
    }

    @Override
    public void initialize() {
        super.initialize();

        if (matchType.isStartMatcher())
            this.startMatcher = new KnuthMorrisPrattAlgo(this.startSeparator);

        if (matchType.isEndMatcher())
            this.endMatcher = new KnuthMorrisPrattAlgo(this.endSeparator);
    }

    @Override
    public int calculateStartPosition(char[] lineData, int prevEndPosn) {
        if (DEFAULT == prevEndPosn || ZERO > prevEndPosn) {
            return this.searchStartMatcher(lineData, DEFAULT, DEFAULT);
        } else {
            return this.searchStartMatcher(lineData, (prevEndPosn + 1), DEFAULT);
        }
    }

    @Override
    public int calculateEndPosition(char[] lineData, int startPosn) {
        return this.searchEndMatcher(lineData, (startPosn + 1), DEFAULT) + (this.endSeparatorLen - 1);
    }

    protected int searchDefault(char[] lineData) {
        return this.searchBetween(lineData, DEFAULT, DEFAULT);
    }

    protected int searchFrom(char[] lineData, int startPosn) {
        return this.searchBetween(lineData, startPosn, DEFAULT);
    }

    protected int searchTo(char[] lineData, int endPosn) {
        return this.searchBetween(lineData, DEFAULT, endPosn);
    }

    protected int searchBetween(char[] lineData, int startPosn, int endPosn) {
        if (MatchTypes.START == this.matchType)
            return this.startMatcher.search(lineData, startPosn, endPosn);
        if (MatchTypes.END == this.matchType)
            return this.endMatcher.search(lineData, startPosn, endPosn);
        if (MatchTypes.BOTH == this.matchType)
            throw new ParseFailureException("Search Matcher selection is ambiguous");

        /* This is an impossible case */
        return DEFAULT;
    }

    protected int searchStartMatcher(char[] lineData, int startPosn, int endPosn) {
        return this.startMatcher.search(lineData, startPosn, endPosn);
    }

    protected int searchEndMatcher(char[] lineData, int startPosn, int endPosn) {
        return this.endMatcher.search(lineData, startPosn, endPosn);
    }

    @Override
    public boolean validate() {
        if (this.matchType.isStartMatcher() && ZERO == this.startSeparatorLen) {
            throw new ValidationFailureException("Start Separator is configured as Empty : " + this.startSeparatorLen);
        }
        if (this.matchType.isEndMatcher() && ZERO == this.endSeparatorLen) {
            throw new ValidationFailureException("End Separator is configured as Empty : " + this.endSeparatorLen);
        }
        return super.validate();
    }

    @Override
    public void destroy() {
        this.startMatcher = null;
        this.endMatcher = null;
        super.destroy();
    }
}
