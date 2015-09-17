/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.fileanalytics.algo;

/**
 * TODO
 * @author abhishek
 * @since  1.0
 */
public interface StringMatcher {

    // return offset of first occurrence of text in pattern (or N if no match)
    // simulate the NFA to find match
    int search(char[] text,
        int searchFrom,
        int searchTo);

    boolean matched(char[] text,
        int searchFrom,
        int searchTo);

}
