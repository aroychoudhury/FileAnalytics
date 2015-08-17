package org.abhishek.fileanalytics.parse.helper;

import org.abhishek.fileanalytics.datatype.VoidType;
import org.abhishek.fileanalytics.exception.ValidationFailureException;
import org.abhishek.fileanalytics.parse.AbstractParseHelper;
import org.abhishek.fileanalytics.parse.Parser;

public class CharacterMaskHelper extends AbstractParseHelper<VoidType> {
    private int  maskFragmentLen = 6;
    private char maskCharacter   = '*';
    private int  gapBetweenMasks = Parser.ZERO;

    public CharacterMaskHelper() {
        super();
    }

    public CharacterMaskHelper(int maskFragmentLen) {
        super();
        this.maskFragmentLen = maskFragmentLen;
    }

    @Override
    public void initialize() {
        super.initialize();
        this.gapBetweenMasks = this.maskFragmentLen / 2;
    }

    @Override
    public VoidType parseInternal(char[] lineData, int startPosn, int endPosn) {
        int mark = this.gapBetweenMasks + this.maskFragmentLen;
        for (int arrIdx = startPosn; arrIdx < endPosn; arrIdx++, mark--) {
            // reinitialize
            if (0 == mark) {
                mark = (this.gapBetweenMasks + this.maskFragmentLen);
            }

            if (this.gapBetweenMasks < mark) {
                lineData[arrIdx] = this.maskCharacter;
            }
        }
        return new VoidType();
    }

    @Override
    public boolean validate() {
        if (6 > this.maskFragmentLen) {
            throw new ValidationFailureException("Mask Fragment Length cannot be less than 6 : " + this.maskFragmentLen);
        }
        return super.validate();
    }
}
