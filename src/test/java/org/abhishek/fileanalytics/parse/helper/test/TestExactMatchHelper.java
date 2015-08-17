package org.abhishek.fileanalytics.parse.helper.test;

import static org.junit.Assert.*;

import org.abhishek.fileanalytics.parse.helper.VerifyMatchHelper;
import org.junit.Before;
import org.junit.Test;

public class TestExactMatchHelper {
    private VerifyMatchHelper helper;

    private char[] lineData; // This is the master String
    private int    startPosn; // This is a random start position in the master
    private int    endPosn; // This is a random end position in the master
    private String matcher; // String that needs to matched

    @Before
    public void setUp() throws Exception {
        this.lineData = "ABHISHEK ROYCHOUDHURY".toCharArray();
        this.matcher = "ROYC";
        this.startPosn = 5;
        this.endPosn = 6;

        this.helper = new VerifyMatchHelper(this.matcher);
    }

    @Test
    public void test() {
        helper.initialize();
        assertTrue(helper.parseInternal(this.lineData, this.startPosn, this.endPosn));
    }
}
