package org.abhishek.fileanalytics.parse.helper;

import org.abhishek.fileanalytics.algos.KnuthMorrisPrattAlgo;
import org.abhishek.fileanalytics.exception.ValidationFailureException;
import org.abhishek.fileanalytics.parse.AbstractParseHelper;

public class VerifyMatchHelper extends AbstractParseHelper<Boolean> {
    private KnuthMorrisPrattAlgo matcher = null;

    public VerifyMatchHelper(String match) {
        super();
        if (null == match || "".equals(match.trim())) {
            throw new ValidationFailureException("Start Separator configured as Null/Empty");
        }
        matcher = new KnuthMorrisPrattAlgo(match.toCharArray());
    }

    @Override
    public Boolean parseInternal(char[] lineData, int startPosn, int endPosn) {
        return ((endPosn + 1) == this.matcher.search(lineData, startPosn, endPosn)) ? false : true;
    }
}
