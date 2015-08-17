package org.abhishek.fileanalytics.parse;

import org.abhishek.fileanalytics.dto.yield.ParseResult;
import org.abhishek.fileanalytics.exception.ParseFailureException;
import org.abhishek.fileanalytics.exception.ValidationFailureException;

public abstract class AbstractParser<E> implements Parser<E> {
    protected boolean      initialized   = false;
    protected boolean      continueOnExc = false;
    private ParseHelper<E> parseHelper   = null;

    @Override
    public boolean isIntialized() {
        return this.initialized;
    }

    @Override
    public boolean IsContinueOnExc() {
        return this.continueOnExc;
    }

    @Override
    public void setContinueOnExc(boolean continueOnExc) {
        this.continueOnExc = continueOnExc;
    }

    @Override
    public void initialize() {
        if (this.parseHelper.validate()) {
            this.parseHelper.initialize();
        }
        this.initialized = true;
    }

    @Override
    public ParseResult<E> parseFragment(char[] lineData, int prevEndPosn) {
        // Find the start position
        int startPosn = this.calculateStartPosition(lineData, prevEndPosn);

        // Find the end position
        int endPosn = this.calculateEndPosition(lineData, startPosn);

        if (startPosn >= endPosn) {
            throw new ParseFailureException("Start [" + startPosn + "] must be lesser than End [" + endPosn + "]");
        }

        // Wrap the data in a ParseResult object
        ParseResult<E> result = this.setParseResult(startPosn, endPosn);

        // Do whatever action is required to be performed on the data
        result.setResult(this.executeHelper(lineData, startPosn, endPosn));

        return result;
    }

    protected E executeHelper(char[] lineData, int startPosn, int endPosn) {
        return this.parseHelper.parseInternal(lineData, startPosn, endPosn);
    }

    @Override
    public void destroy() {
        this.parseHelper.destroy();
    }

    @Override
    public boolean validate() {
        if (null == this.parseHelper) {
            throw new ValidationFailureException("All Parser classes must have a Helper configured");
        }
        return true;
    }

    protected ParseResult<E> setParseResult(int startPosn, int endPosn) {
        ParseResult<E> result = new ParseResult<E>();
        result.setParsedStartPosn(startPosn);
        result.setParsedEndPosn(endPosn);
        result.setParsedLength(endPosn - startPosn);
        return result;
    }

    @SuppressWarnings({
        "rawtypes",
        "unchecked"
    })
    public void setParseHelper(ParseHelper parseHelper) {
        this.parseHelper = parseHelper;
    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }
}
