package org.abhishek.fileanalytics.parse.helper;

import java.util.Map;

import org.abhishek.fileanalytics.exception.ValidationFailureException;
import org.abhishek.fileanalytics.parse.AbstractParseHelper;

public class MultipleMatchesHelper extends AbstractParseHelper<Boolean> {
    private Map<String, String> matches = null;

    public MultipleMatchesHelper(Map<String, String> matches) {
        super();
        if (null == matches) {
            throw new ValidationFailureException("Match configuration cannot be Null");
        }
        this.matches = matches;
    }

    @Override
    public Boolean parseInternal(char[] lineData, int startPosn, int endPosn) {
        char[] extractedData = new char[endPosn - startPosn];
        for (int idx = startPosn; idx < endPosn; idx++) {
            extractedData[idx] = lineData[startPosn + idx];
        }
        return this.matches.containsKey(extractedData);
    }

    public void addMatch(String key) {
        this.matches.put(key, "A");
    }

    /**
     * @author abhishek
     * @since  1.0
     * @see org.abhishek.fileanalytics.parse.AbstractParseHelper#destroy()
     */
    @Override
    public void destroy() {
        super.destroy();
        this.matches = null;
    }
}
