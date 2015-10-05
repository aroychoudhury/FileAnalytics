package org.abhishek.fileanalytics.parse;

import org.abhishek.fileanalytics.dto.yield.ParseResult;
import org.abhishek.fileanalytics.exception.ParseFailureException;
import org.abhishek.fileanalytics.exception.ValidationFailureException;
import org.abhishek.fileanalytics.lifecycle.AbstractInitializer;
import org.abhishek.fileanalytics.lifecycle.Destroyable;
import org.abhishek.fileanalytics.lifecycle.Initializable;
import org.abhishek.fileanalytics.lifecycle.Validatable;

public abstract class AbstractParser<E> extends AbstractInitializer implements Parser<E>, Initializable, Destroyable, Validatable {
    protected boolean              continueOnExc = false;
    private AbstractParseHelper<E> parseHelper   = null;

    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.parse.Parser#IsContinueOnExc()
     */
    @Override
    public boolean IsContinueOnExc() {
        return this.continueOnExc;
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.parse.Parser#setContinueOnExc(boolean)
     */
    @Override
    public void setContinueOnExc(boolean continueOnExc) {
        this.continueOnExc = continueOnExc;
    }

    /**
     * @author abhishek
     * @since  1.0
     * @see org.abhishek.fileanalytics.lifecycle.AbstractInitializer#initialize()
     */
    @Override
    public void initialize() {
        super.initialize();
        if (this.parseHelper.validate()) {
            this.parseHelper.initialize();
        }
    }

    /**
     * @author abhishek
     * @since  1.0
     * @see org.abhishek.fileanalytics.parse.Parser#parseFragment(char[], int)
     */
    @Override
    public ParseResult<E> parseFragment(char[] lineData,
        int prevEndPosn) {
        // Find the start position
        int startPosn = this.calculateStartPosition(
            lineData,
            prevEndPosn);

        // Find the end position
        int endPosn = this.calculateEndPosition(
            lineData,
            startPosn);

        if (startPosn >= endPosn) {
            throw new ParseFailureException("Start [" + startPosn + "] must be lesser than End [" + endPosn + "]");
        }

        // Wrap the data in a ParseResult object
        ParseResult<E> result = this.setParseResult(
            startPosn,
            endPosn);

        // Do whatever action is required to be performed on the data
        result.setResult(this.executeHelper(
            lineData,
            startPosn,
            endPosn));

        return result;
    }

    protected E executeHelper(char[] lineData,
        int startPosn,
        int endPosn) {
        return this.parseHelper.parseInternal(
            lineData,
            startPosn,
            endPosn);
    }

    /**
     * @author abhishek
     * @since  1.0
     * @see org.abhishek.fileanalytics.lifecycle.AbstractDestroyer#destroy()
     */
    @Override
    public void destroy() {
        this.parseHelper.destroy();
        super.destroy();
    }

    /**
     * @author abhishek
     * @since  1.0
     * @see org.abhishek.fileanalytics.lifecycle.Validatable#validate()
     */
    @Override
    public boolean validate() {
        if (null == this.parseHelper) {
            throw new ValidationFailureException("All Parser classes must have a Helper configured");
        }
        return true;
    }

    protected ParseResult<E> setParseResult(int startPosn,
        int endPosn) {
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
    public void setParseHelper(AbstractParseHelper parseHelper) {
        this.parseHelper = parseHelper;
    }

    /**
     * @author abhishek
     * @since  1.0
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.getClass().getName();
    }
}
