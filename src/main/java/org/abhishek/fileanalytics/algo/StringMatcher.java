/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.fileanalytics.algo;

/**
 * All String matching algorithms must implement this interface to be used in
 * the log processing.
 * 
 * String matching is required to bypass the defaults provided by Java and
 * leverage faster and more efficient alternatives.
 * 
 * The first revision of this project contains an implementation of
 * Knuth-Morris-Pratt algorithm.
 * 
 * @author abhishek
 * @since 1.0
 * @see org.abhishek.fileanalytics.algo.impl.KnuthMorrisPrattAlgo
 */
public interface StringMatcher {

    /**
     * Return offset of first occurrence of text in pattern (or N if no match),
     * where N is the last position that was matched for.
     * 
     * For no match being found, this can be the entire length of text, if no
     * valid end-point has been (searchTo value) specified.
     * 
     * For some match being found, it would return the matching index number.
     * 
     * @param text
     *            The text which needs to be searched for the target pattern.
     * @param searchFrom
     *            Starting point in the text, from where the pattern matching
     *            needs to start.
     * @param searchTo
     *            Ending point in the text where the pattern matching needs to
     *            end.
     * @return The index of match or the last position that was tried for the
     *         match.
     * @author abhishek
     * @since 1.0
     */
    int search(char[] text,
        int searchFrom,
        int searchTo);

    /**
     * Verifies whether the match was a success or failure.
     * 
     * Internally makes a call to the search method - 
     * {@link org.abhishek.fileanalytics.algo.StringMatcher#search(char[], int, int)}.
     * 
     * @param text
     *            The text which needs to be searched for the target pattern.
     * @param searchFrom
     *            Starting point in the text, from where the pattern matching
     *            needs to start.
     * @param searchTo
     *            Ending point in the text where the pattern matching needs to
     *            end.
     * @return true if the pattern was matched in the text or false if no match
     *         was found.
     * @author abhishek
     * @since 1.0
     */
    boolean matched(char[] text,
        int searchFrom,
        int searchTo);

}
