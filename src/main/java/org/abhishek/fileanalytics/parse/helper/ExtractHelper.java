package org.abhishek.fileanalytics.parse.helper;

import org.abhishek.fileanalytics.parse.AbstractParseHelper;

/**
 * @author abhishek
 * @since  1.0
 */
public class ExtractHelper extends AbstractParseHelper<String> {

    /**
     * @author abhishek
     * @since  1.0
     * @see org.abhishek.fileanalytics.parse.ParseHelper#parseInternal(char[], int, int)
     */
    @Override
    public String parseInternal(char[] lineData, int startPosn, int endPosn) {
        StringBuilder builder = new StringBuilder();
        for (int arrIdx = startPosn; arrIdx < endPosn; arrIdx++) {
            builder.append(lineData[arrIdx]);
        }
        return builder.toString();
    }

    /**
     * @author abhishek
     * @since  1.0
     * @see org.abhishek.fileanalytics.parse.ParseHelper#parseEmpty()
     */
    @Override
    public String parseEmpty() {
        return "";
    }

}
