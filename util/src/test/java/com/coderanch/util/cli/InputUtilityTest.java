/*
 * Copyright (C) 2018 Coderanch.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.coderanch.util.cli;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Tests the {@link InputUtility} class.
 */
@RunWith(Theories.class)
public class InputUtilityTest {

    /**
     * Inputs to test for yes and no.
     */
    @DataPoints
    public static final List<String> YES_NO = List.of("   YEs ", "no", " n", "  y ");

    /**
     * Inputs to test for one of these.
     */
    @DataPoints
    public static final List<List<String>> ONE_OF_THESE
        = List.of(List.of("in1", "in2", "in3"), List.of("in1a", "in2a", "in3a"));

    /**
     * Inputs to test for intRange.
     */
    @DataPoints
    public static final int[][] INT_RANGE
        = new int[][]{{1, 5}, {5, 7}};

    /**
     * Inputs to test for doubleRange.
     */
    @DataPoints
    public static final double[][] DOUBLE_RANGE
        = new double[][]{{1.2, 5.2}, {2.3, 6.7}};

    /**
     * Tests that {@link InputUtility#nextString(String, Predicate)} returns the expected value.
     */
    @Theory
    @SuppressWarnings("checkstyle:methodname")
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
    @SuppressWarnings("checkstyle:methodname")
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
    @SuppressWarnings({"checkstyle:methodname", "checkstyle:magicnumber"})
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
    @SuppressWarnings("checkstyle:methodname")
    public void nextYesNo_validatesYesCorrectly() throws IOException {
        withInput(" yEs ", inputUtility -> {
            var bool = inputUtility.nextYesNo("prompt");
            assertThat("Next yesNo must match expected value.", bool, is(true));
        });
    }

    /**
     * Tests that {@link InputUtility#nextYesNo(String, Predicate)} returns the expected value.
     */
    @Theory
    @SuppressWarnings("checkstyle:methodname")
    public void nextYesNo_validatesNoCorrectly() throws IOException {
        withInput("NO", inputUtility -> {
            var bool = inputUtility.nextYesNo("prompt");
            assertThat("Next yesNo must match expected value.", bool, is(false));
        });
    }

    /**
     * Tests that {@link InputUtility#pause()} works.
     */
    @Theory
    @SuppressWarnings("checkstyle:methodname")
    public void pause_validatesCorrectly() throws IOException {
        withInput(" ", inputUtility -> {
            inputUtility.pause();
        });
    }


    /**
     * Tests that {@link InputUtility#yesOrNo()} returns the expected value.
     *
     * @param input user input to check.
     */
    @Theory
    @SuppressWarnings("checkstyle:methodname")
    public void yesOrNo_validatesCorrectly(String input) {
        var result = InputUtility.yesOrNo().test(input);
        assertThat("Predicate must match expected value.", result, is(true));
    }

    /**
     * Tests that {@link InputUtility#oneOfTheseIgnoringCase(String, String...)}  returns the expected value.
     *
     * @param values the strings to check against in the predicate.
     */
    @Theory
    @SuppressWarnings("checkstyle:methodname")
    public void oneOfThese_validatesCorrectly(List<String> values) {
        var result = InputUtility.oneOfTheseIgnoringCase(values.get(0), values.subList(1, values.size())
            .toArray(String[]::new))
            .test(values.get(0));
        assertThat("Predicate must match expected value.", result, is(true));
    }

    /**
     * Tests that {@link InputUtility#intRange(int, int)} returns the expected value.
     *
     * @param values the number range check against in the predicate.
     */
    @Theory
    @SuppressWarnings("checkstyle:methodname")
    public void intRange_validatesCorrectly(int[] values) throws IOException {
        var result = InputUtility.intRange(values[0], values[1]).test(values[0] + 1);
        assertThat("Predicate must match expected value.", result, is(true));
    }

    /**
     * Tests that {@link InputUtility#doubleRange(double, double)} returns the expected value.
     *
     * @param values the number range check against in the predicate.
     */
    @Theory
    @SuppressWarnings("checkstyle:methodname")
    public void doubleRange_validatesCorrectly(double[] values) throws IOException {
        var result = InputUtility.doubleRange(values[0], values[1]).test(values[0] + 1);
        assertThat("Predicate must match expected value.", result, is(true));
    }

    private static <X extends Throwable> void withInput(
        String input,
        ExceptionalConsumer<? super InputUtility, X> action
    ) throws IOException, X {
        try (
            var stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
            var inputUtility = new InputUtility(stream, StandardCharsets.UTF_8);
        ) {
            action.accept(inputUtility);
        }
    }

    @FunctionalInterface
    private interface ExceptionalConsumer<T, X extends Throwable> {

        void accept(T argument) throws X;

    }
}
