package com.coderanch.util.cli;

import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.function.Predicate;

/**
 * Tests the {@link InputUtility} class.
 */
@RunWith(Theories.class)
public class InputUtilityTest {


    /**
     * Tests that {@link InputUtility#nextString(String, Predicate)} returns the expected value.
     */
    @Theory
    public void nextString_validatesCorrectInput() throws IOException {
        withInput("input", inputUtility -> {
            var string = inputUtility.nextString("prompt", s -> true);
            assertThat("Next string must match expected value.", string, is("input"));
        });
    }

    /**
     * Tests that {@link InputUtility#nextInt(String, Predicate)} returns the expected value.
     */
    @Theory
    public void nextInt_validatesCorrectInput() throws IOException {
        withInput("1", inputUtility -> {
            var integer = inputUtility.nextInt("prompt", i -> true);
            assertThat("Next int must match expected value.", integer, is(1));
        });
    }

    /**
     * Tests that {@link InputUtility#nextDouble(String, Predicate)} returns the expected value.
     */
    @Theory
    public void nextDouble_validatesCorrectInput() throws IOException {
        withInput("2.2", inputUtility -> {
            var dble = inputUtility.nextDouble("prompt", d -> true);
            assertThat("Next int must match expected value.", dble, is(2.2));
        });
    }

    /**
     * Tests that {@link InputUtility#nextYesNo(String, Predicate)} returns the expected value.
     */
    @Theory
    public void nextYesNo_validatesYesCorrectly() throws IOException {
        withInput(" yEs ", inputUtility -> {
            var bool = inputUtility.nextYesNo("prompt", InputUtility.yesOrNo());
            assertThat("Next yesNo must match expected value.", bool, is(true));
        });
    }

    /**
     * Tests that {@link InputUtility#nextYesNo(String, Predicate)} returns the expected value.
     */
    @Theory
    public void nextYesNo_validatesNoCorrectly() throws IOException {
        withInput("NO", inputUtility -> {
            var bool = inputUtility.nextYesNo("prompt", InputUtility.yesOrNo());
            assertThat("Next yesNo must match expected value.", bool, is(false));
        });
    }

    /**
     * Tests that {@link InputUtility#pause()} works.
     */
    @Theory
    public void pause_validatesCorrectly() throws IOException {
        var testCase = ".";
        try (var s = new ByteArrayInputStream(testCase.getBytes())) {
            var iu = new InputUtility(s, StandardCharsets.UTF_8);
            iu.pause();
        }
    }

    /**
     * Tests that {@link InputUtility#yesOrNo()} returns the expected value.
     */
    @Theory
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

    /**
     * Tests that {@link InputUtility#oneOfThese(String...)} returns the expected value.
     */
    @Theory
    public void oneOfThese_validatesCorrectly() throws IOException {

    }

    /**
     * Tests that {@link InputUtility#intRange(int, int)} returns the expected value.
     */
    @Theory
    public void intRange_validatesCorrectly() throws IOException {
    }

    /**
     * Tests that {@link InputUtility#doubleRange(double, double)} returns the expected value.
     */
    @Theory
    public void doubleRange_validatesCorrectly() throws IOException {
    }

    private static <X extends Throwable> void withInput(
            String input,
            ExceptionalConsumer<? super InputUtility, X> action
    ) throws IOException, X {
        try (var stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))) {
            var inputUtility = new InputUtility(stream, StandardCharsets.UTF_8);
            action.accept(inputUtility);
        }
    }

    @FunctionalInterface
    private static interface ExceptionalConsumer<T, X extends Throwable> {

        void accept(T argument) throws X;

    }
}