package org.abhishek.fileanalytics.dto.yield;

import org.abhishek.fileanalytics.dto.Yieldable;

public class ParseResult<E> implements Yieldable {
    private boolean success         = false;
    private int     parsedLength    = 0;
    private int     parsedStartPosn = 0;
    private int     parsedEndPosn   = 0;
    private E       result          = null;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getParsedLength() {
        return parsedLength;
    }

    public void setParsedLength(int parsedLength) {
        this.parsedLength = parsedLength;
    }

    public int getParsedStartPosn() {
        return parsedStartPosn;
    }

    public void setParsedStartPosn(int parsedStartPosn) {
        this.parsedStartPosn = parsedStartPosn;
    }

    public int getParsedEndPosn() {
        return parsedEndPosn;
    }

    public void setParsedEndPosn(int parsedEndPosn) {
        this.parsedEndPosn = parsedEndPosn;
    }

    public E getResult() {
        return result;
    }

    public void setResult(E result) {
        this.result = result;
    }

}
