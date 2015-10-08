package org.abhishek.fileanalytics.parse.helper;

import java.util.Map;

import org.abhishek.fileanalytics.exception.ValidationFailureException;
import org.abhishek.fileanalytics.parse.AbstractParseHelper;

/**
 * @author abhishek
 * @since 1.0
 */
public class MultipleMatchesHelper extends AbstractParseHelper<Boolean> {
    private Map<String, String> matches = null;

    /**
     * @param matches
     * @author abhishek
     * @since 1.0
     */
    public MultipleMatchesHelper(Map<String, String> matches) {
        super();
        if (null == matches) {
            throw new ValidationFailureException("Match configuration cannot be Null");
        }
        this.matches = matches;
    }

    /**
     * @author abhishek
     * @since  1.0
     * @see org.abhishek.fileanalytics.parse.ParseHelper#parseInternal(char[], int, int)
     */
    @Override
    public Boolean parseInternal(char[] lineData,
        int startPosn,
        int endPosn) {
        char[] extractedData = new char[endPosn - startPosn];
        for (int idx = startPosn; idx < endPosn; idx++) {
            extractedData[idx] = lineData[startPosn + idx];
        }
        return this.matches.containsKey(extractedData);
    }

    /**
     * @param key
     * @author abhishek
     * @since  1.0
     */
    public void addMatch(String key) {
        this.matches.put(key, "A");
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.parse.AbstractParseHelper#destroy()
     */
    @Override
    public void destroy() {
        super.destroy();
        this.matches = null;
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
