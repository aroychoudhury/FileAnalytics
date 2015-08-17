package org.abhishek.fileanalytics.parse.helper;

import org.abhishek.fileanalytics.parse.AbstractParseHelper;

public class ExtractHelper extends AbstractParseHelper<String> {
    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public String parseInternal(char[] lineData, int startPosn, int endPosn) {
        StringBuilder builder = new StringBuilder();
        for (int arrIdx = startPosn; arrIdx < endPosn; arrIdx++) {
            builder.append(lineData[arrIdx]);
        }
        return builder.toString();
    }
}
