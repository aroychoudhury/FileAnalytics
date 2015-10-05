/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.fileanalytics.dto.yield;

import org.abhishek.fileanalytics.lifecycle.Yieldable;

/**
 * Encapsulates the output from the
 * {@link org.abhishek.fileanalytics.parse.Parser} classes.
 * 
 * @author abhishek
 * @since 1.0
 * @param <E>
 *            The data type of the result being retured.
 */
public class ParseResult<E> implements Yieldable {
    private boolean success         = false;
    private int     parsedLength    = 0;
    private int     parsedStartPosn = 0;
    private int     parsedEndPosn   = 0;
    private E       result          = null;

    /**
     * @return true if the parse operation was successful else returns false.
     * @author abhishek
     * @since 1.0
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success
     *            Set the parse operation success indicator.
     * @author abhishek
     * @since 1.0
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return The parsed length.
     * @author abhishek
     * @since 1.0
     */
    public int getParsedLength() {
        return parsedLength;
    }

    /**
     * @param parsedLength
     *            Set the parsed length.
     * @author abhishek
     * @since 1.0
     */
    public void setParsedLength(int parsedLength) {
        this.parsedLength = parsedLength;
    }

    /**
     * @return The parsed start position.
     * @author abhishek
     * @since 1.0
     */
    public int getParsedStartPosn() {
        return parsedStartPosn;
    }

    /**
     * @param parsedStartPosn
     *            Set the parsed start position.
     * @author abhishek
     * @since 1.0
     */
    public void setParsedStartPosn(int parsedStartPosn) {
        this.parsedStartPosn = parsedStartPosn;
    }

    /**
     * @return The parsed end position.
     * @author abhishek
     * @since 1.0
     */
    public int getParsedEndPosn() {
        return parsedEndPosn;
    }

    /**
     * @param parsedEndPosn
     *            Set the parsed end position.
     * @author abhishek
     * @since 1.0
     */
    public void setParsedEndPosn(int parsedEndPosn) {
        this.parsedEndPosn = parsedEndPosn;
    }

    /**
     * @return The parsed result.
     * @author abhishek
     * @since 1.0
     */
    public E getResult() {
        return result;
    }

    /**
     * @param result
     *            Set the parsed result.
     * @author abhishek
     * @since 1.0
     */
    public void setResult(E result) {
        this.result = result;
    }

}
