package org.abhishek.fileanalytics.parse.helper;

import org.abhishek.fileanalytics.datatype.VoidType;
import org.abhishek.fileanalytics.parse.AbstractParseHelper;

public class CharacterHideHelper extends AbstractParseHelper<VoidType> {
    private char maskCharacter   = '*';

    @Override
    public VoidType parseInternal(char[] lineData, int startPosn, int endPosn) {
        for (int arrIdx = startPosn; arrIdx < endPosn; arrIdx++) {
            lineData[arrIdx] = this.maskCharacter;
        }
        return new VoidType();
    }
}
