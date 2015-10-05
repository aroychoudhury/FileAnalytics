package org.abhishek.fileanalytics.parse;


public interface ParseHelper<E> {
    E parseInternal(char[] lineData, int startPosn, int endPosn);
}
