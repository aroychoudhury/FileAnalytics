package org.abhishek.fileanalytics.parse.helper;

import org.abhishek.fileanalytics.datatype.VoidType;
import org.abhishek.fileanalytics.exception.ValidationFailureException;
import org.abhishek.fileanalytics.parse.AbstractParseHelper;
import org.abhishek.fileanalytics.parse.FragmentParser;

/**
 * @author abhishek
 * @since 1.0
 */
public class CharacterMaskHelper extends AbstractParseHelper<VoidType> {
    private int  maskFragmentLen = 6;
    private char maskCharacter   = '*';
    private int  gapBetweenMasks = FragmentParser.ZERO;

    /**
     * @author abhishek
     * @since 1.0
     */
    public CharacterMaskHelper() {
        super();
    }

    /**
     * @param maskFragmentLen
     * @author abhishek
     * @since 1.0
     */
    public CharacterMaskHelper(int maskFragmentLen) {
        super();
        this.maskFragmentLen = maskFragmentLen;
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.lifecycle.AbstractInitializer#initialize()
     */
    @Override
    public void initialize() {
        super.initialize();
        this.gapBetweenMasks = this.maskFragmentLen / 2;
    }

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

    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.lifecycle.AbstractValidater#validate()
     */
    @Override
    public boolean validate() {
        if (6 > this.maskFragmentLen) {
            throw new ValidationFailureException("Mask Fragment Length cannot be less than 6 : " + this.maskFragmentLen);
        }
        return super.validate();
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
