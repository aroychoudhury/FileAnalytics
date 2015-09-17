package org.abhishek.fileanalytics.algo.impl;

import org.abhishek.fileanalytics.algo.StringMatcher;

/**
 * Inspired by http://algs4.cs.princeton.edu/53substring/KMPplus.java.
 * Tutorial for this at https://www.youtube.com/watch?v=GTJr8OvyEVQ. 
 * 
 * @author abhishek
 * @since  1.0
 */
public class KnuthMorrisPrattAlgo implements StringMatcher {
	private char[] pattern;
	private int[] next;

	// create Knuth-Morris-Pratt NFA from pattern
	public KnuthMorrisPrattAlgo(char[] pattern) {
		this.pattern = pattern;
		int patternLength = pattern.length;
		next = new int[patternLength];
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
        /*
         * for (int i = 0; i < patternLength; i++) System.out.println("next[" +
         * i + "] = " + next[i]);
         */
	}

	// return offset of first occurrence of text in pattern (or N if no match)
	// simulate the NFA to find match
	/**
     * @author abhishek
     * @since  1.0
     * @see org.abhishek.fileanalytics.algo.StringMatcher#search(char[], int, int)
     */
	@Override
    public int search(char[] text, int searchFrom, int searchTo) {
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
     * @since  1.0
     * @see org.abhishek.fileanalytics.algo.StringMatcher#matched(char[], int, int)
     */
	@Override
    public boolean matched(char[] text, int searchFrom, int searchTo) {
	    return (this.search(text, searchFrom, searchTo) != searchTo);
	}

	// test client
	public static void main(String[] args) {
		String pattern = "nab";
		String text = "ccaabaabaabaaabanabccaabaabaabaaabanabccaabaabaabaaabanabccaabaabaabaaabanabccaabaabaabaaabanabccaabaabaabaaabanab";
		int M = pattern.length();
		int N = text.length();

		// substring search
		StringMatcher kmp = new KnuthMorrisPrattAlgo(pattern.toCharArray());
		int offset = kmp.search(text.toCharArray(), 20, -10);

		// print results
		System.out.println("M = " + M + ", N = " + N + ", offset = " + offset);
		System.out.println("text:    " + text);
		System.out.print("pattern: ");
		for (int i = 0; i < offset; i++)
			System.out.print(" ");
		System.out.println(pattern);
	}
}
