package org.abhishek.fileanalytics.parse.helper;

import org.abhishek.fileanalytics.datatype.VoidType;
import org.abhishek.fileanalytics.parse.AbstractParseHelper;

/**
 * @author abhishek
 * @since 1.0
 */
public class CharacterHideHelper extends AbstractParseHelper<VoidType> {
    private char maskCharacter = '*';

    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.parse.ParseHelper#parseInternal(char[],
     *      int, int)
     */
    @Override
    public VoidType parseInternal(char[] lineData,
        int startPosn,
        int endPosn) {
        for (int arrIdx = startPosn; arrIdx < endPosn; arrIdx++) {
            lineData[arrIdx] = this.maskCharacter;
        }
        return new VoidType();
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.parse.ParseHelper#parseEmpty()
     */
    @Override
    public VoidType parseEmpty() {
        return new VoidType();
    }
}
