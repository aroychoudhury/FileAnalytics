package org.abhishek.fileanalytics.parse;

/**
 * TODO
 * 
 * @author abhishek
 * @since 1.0
 * @param <E>
 */
public interface ParseHelper<E> {

    E parseInternal(char[] lineData,
        int startPosn,
        int endPosn);

    E parseEmpty();

}
