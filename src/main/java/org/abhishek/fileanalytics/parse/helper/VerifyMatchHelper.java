package org.abhishek.fileanalytics.parse.helper;

import org.abhishek.fileanalytics.algo.StringMatcher;
import org.abhishek.fileanalytics.algo.impl.KnuthMorrisPrattAlgo;
import org.abhishek.fileanalytics.exception.ValidationFailureException;
import org.abhishek.fileanalytics.parse.AbstractParseHelper;

/**
 * @author abhishek
 * @since  1.0
 */
public class VerifyMatchHelper extends AbstractParseHelper<Boolean> {
    private StringMatcher matcher = null;

    /**
     * @param match
     * @author abhishek
     * @since 1.0
     */
    public VerifyMatchHelper(String match) {
        super();
        if (null == match || "".equals(match.trim())) {
            throw new ValidationFailureException("Start Separator configured as Null/Empty");
        }
        matcher = new KnuthMorrisPrattAlgo(match.toCharArray());
    }

    /**
     * @author abhishek
     * @since  1.0
     * @see org.abhishek.fileanalytics.parse.ParseHelper#parseInternal(char[], int, int)
     */
    @Override
    public Boolean parseInternal(char[] lineData, int startPosn, int endPosn) {
        return this.matcher.matched(lineData, startPosn, endPosn);
    }

    /**
     * @author abhishek
     * @since  1.0
     * @see org.abhishek.fileanalytics.parse.ParseHelper#parseEmpty()
     */
    @Override
    public Boolean parseEmpty() {
        return new Boolean(true);
    }
}
