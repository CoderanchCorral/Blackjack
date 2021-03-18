package com.coderanch.util.cli;

import junit.framework.TestCase;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Tests the {@link InputUtility} class.
 */
@RunWith(Theories.class)
public class InputUtilityTest {


    /**
     * @throws IOException
     */
    @Theory()
    public void nextString_validatesCorrectInput() throws IOException {
        var testCase = "input";
        try (var s = new ByteArrayInputStream(testCase.getBytes())) {
            var iu = new InputUtility(s);
            var result = iu.nextString("prompt", s1 -> s1.equals(testCase));
            assertThat("Matches testCase", testCase, is(equalTo(result)));
        }
    }

    /**
     * @throws IOException
     */
    @Theory()
    public void nextInt_validatesCorrectInput() throws IOException {
        var testCase = "1";
        try (var s = new ByteArrayInputStream(testCase.getBytes())) {
            var iu = new InputUtility(s);
            var result = iu.nextInt("prompt", n -> n == 1);
            assertThat("Matches testCase", Integer.parseInt(testCase), is(equalTo(result)));
        }
    }

    /**
     * @throws IOException
     */
    @Theory()
    public void nextDouble_validatesCorrectInput() throws IOException {
        var testCase = "2.2";
        try (var s = new ByteArrayInputStream(testCase.getBytes())) {
            var iu = new InputUtility(s);
            var result = iu.nextDouble("prompt", n -> n == 2.2d);
            assertThat("Matches testCase", Double.parseDouble(testCase), is(equalTo(result)));
        }
    }

    /**
     * @throws IOException
     */
    @Theory()
    public void nextYesNo_validatesYesCorrectly() throws IOException {
        var testCase = " yEs ";
        try (var s = new ByteArrayInputStream(testCase.getBytes())) {
            var iu = new InputUtility(s);
            var result = iu.nextYesNo("prompt", InputUtility.yesOrNo());
            assertThat("Matches testCase", true, is(equalTo(result)));
        }
    }

    /**
     * @throws IOException
     */
    @Theory()
    public void nextYesNo_validatesNoCorrectly() throws IOException {
        var testCase = "NO";
        try (var s = new ByteArrayInputStream(testCase.getBytes())) {
            var iu = new InputUtility(s);
            var result = iu.nextYesNo("prompt", InputUtility.yesOrNo());
            assertThat("Matches testCase", false, is(equalTo(result)));
        }
    }

    /**
     * @throws IOException
     */
    @Theory()
    public void pause_validatesCorrectly() throws IOException {
        var testCase = ".";
        try (var s = new ByteArrayInputStream(testCase.getBytes())) {
            var iu = new InputUtility(s);
            iu.pause();
        }
    }

    /**
     * @throws IOException
     */
    @Theory()
    public void yesOrNo_validatesCorrectly() throws IOException {
        var result = InputUtility.yesOrNo().test("   YEs ");
        assertThat("Matches correctly", true, is(equalTo(result)));

        var result2 = InputUtility.yesOrNo().test("no");
        assertThat("Matches correctly", true, is(equalTo(result2)));

        var result3 = InputUtility.yesOrNo().test(" n");
        assertThat("Matches correctly", true, is(equalTo(result3)));

        var result4 = InputUtility.yesOrNo().test("  y ");
        assertThat("Matches correctly", true, is(equalTo(result4)));
    }


    public void testOneOfThese() {
    }

    public void testIntRange() {
    }

    public void testDoubleRange() {
    }
}