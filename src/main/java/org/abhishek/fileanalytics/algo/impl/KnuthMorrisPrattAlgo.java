package org.abhishek.fileanalytics.algo.impl;

import org.abhishek.fileanalytics.algo.StringMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Inspired by <a
 * href="http://algs4.cs.princeton.edu/53substring/KMPplus.java.html"
 * >KMPplus.java</a>.
 * 
 * Tutorial for this at <a
 * href="https://www.youtube.com/watch?v=GTJr8OvyEVQ">Youtube</a>.
 * 
 * Original Source Code for this algorithm is available under GNU General Public
 * License, version 3 (GPLv3).
 * 
 * This implementation is not designed to be extendable; in case any added
 * feature needs to be introduced, a new class needs to be created.
 * 
 * @author abhishek
 * @since 1.0
 */
public final class KnuthMorrisPrattAlgo implements StringMatcher {
    private static final Logger logger = LoggerFactory.getLogger(KnuthMorrisPrattAlgo.class);

    /* Class variables */
    private char[]              pattern;
    private int[]               next;

    /**
     * Create Knuth-Morris-Pratt NFA from pattern. This is a initializing
     * process that saves performance when .
     * 
     * @param pattern
     *            The pattern which needs to be matched eventually.
     * @author abhishek
     * @since 1.0
     */
    public KnuthMorrisPrattAlgo(char[] pattern) {
        int patternLength = pattern.length;

        this.pattern = pattern;
        this.next = new int[patternLength];

        int j = -1;
        for (int i = 0; i < patternLength; i++) {
            if (i == 0)
                next[i] = -1;
            else if (pattern[i] != pattern[j])
                next[i] = j;
            else
                next[i] = next[j];
            while (j >= 0 && pattern[i] != pattern[j]) {
                j = next[j];
            }
            j++;
        }

        /* Used for debugging */
        if (logger.isDebugEnabled()) {
            for (int i = 0; i < patternLength; i++)
                logger.debug("next[{}] = {}", i, next[i]);
        }
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.algo.StringMatcher#search(char[], int,
     *      int)
     */
    @Override
    public int search(char[] text,
        int searchFrom,
        int searchTo) {
        int patternLength = pattern.length;
        int txtLength = text.length;

        if (0 > searchFrom) {
            searchFrom = 0;
        }
        if (0 > searchTo || searchFrom > searchTo) {
            searchTo = txtLength;
        } else {
            searchTo = searchTo + 1;
        }

        int i, j;
        for (i = searchFrom, j = 0; i < searchTo && j < patternLength; i++) {
            while (j >= 0 && text[i] != pattern[j])
                j = next[j];
            j++;
        }
        if (j == patternLength)
            return i - patternLength;
        return searchTo;
    }

    /**
     * @author abhishek
     * @since 1.0
     * @see org.abhishek.fileanalytics.algo.StringMatcher#matched(char[], int,
     *      int)
     */
    @Override
    public boolean matched(char[] text,
        int searchFrom,
        int searchTo) {
        int txtLength = text.length;

        if (0 > searchFrom) {
            searchFrom = 0;
        }
        if (0 > searchTo || searchFrom > searchTo) {
            searchTo = txtLength;
        } else {
            searchTo = searchTo + 1;
        }

        return (this.search(
            text,
            searchFrom,
            searchTo) != (searchTo + 1));
    }

    // test client
    public static void main(String[] args) {
        String pattern = "nab";
        String text = "ccaabaabaabaaabanabccaabaabaabaaabanabccaabaabaabaaabanabccaabaabaabaaabanabccaabaabaabaaabanabccaabaabaabaaabanab";
        int M = pattern.length();
        int N = text.length();

        // substring search
        StringMatcher kmp = new KnuthMorrisPrattAlgo(pattern.toCharArray());
        int offset = kmp.search(
            text.toCharArray(),
            0,
            30);

        // print results
        System.out.println("M = " + M + ", N = " + N + ", position = " + offset + ", match = " + kmp.matched(
            text.toCharArray(),
            0,
            30));
        System.out.println("text:    " + text);
        System.out.print("pattern: ");
        for (int i = 0; i < offset; i++)
            System.out.print(" ");
        System.out.println(pattern);
    }
}
