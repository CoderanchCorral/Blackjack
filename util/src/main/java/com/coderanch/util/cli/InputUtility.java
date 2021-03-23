/*
 * Copyright (C) 2018 Coderanch.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.coderanch.util.cli;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static com.coderanch.util.require.Require.requireThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Multipurpose input utility for use with a command line interface.
 * The user can pass a prompt and optional validation.
 * The utility will read the provided input stream and return a possibly validated String or primitive.
 */
public final class InputUtility implements AutoCloseable {

    /**
     * Allowed user inputs that are considered synonymous with "yes".
     */
    private static final List<String> YES_SYNONYMS = List.of("y", "yes");

    /**
     * Allowed user inputs that are considered synonymous with "no".
     */
    private static final List<String> NO_SYNONYMS = List.of("n", "no");

    /**
     * The reader to read user responses with.
     */
    private final BufferedReader reader;

    /**
     * Constructs a new input utility that reads from {@link System#in} using the UTF-8 encoding.
     * Data from the underlying stream will be buffered.
     * Do not use the same stream in a different utility.
     */
    public InputUtility() {
        this(System.in, StandardCharsets.UTF_8);
    }

    /**
     * Constructor that takes an input stream to be used.
     * Data from the underlying stream will be buffered.
     * Do not use the same stream in a different utility.
     *
     * @param inputStream input stream to be used.
     * @param charset     charset to be used.
     */
    public InputUtility(InputStream inputStream, Charset charset) {
        requireThat("inputStream", inputStream, is(notNullValue()));
        requireThat("charset", charset, is(notNullValue()));
        reader = new BufferedReader(new InputStreamReader(inputStream, charset));
    }

    /**
     * Displays the prompt and reads the input stream, returning a validated String.
     *
     * @param prompt          the prompt to display to the user.
     * @param stringPredicate the predicate to use for validation.
     * @return a possibly validated String.
     * @throws IOException when there's a problem with the underlying {@link BufferedReader}.
     */
    public String nextString(String prompt, Predicate<? super String> stringPredicate) throws IOException {
        System.out.println(prompt);
        String line;
        for (
                line = reader.readLine();
                !stringPredicate.test(line);
                line = reader.readLine()
        ) {
            System.out.println("Invalid input.");
        }

        return line;
    }

    /**
     * Displays the prompt and reads the input stream, returning a validated integer.
     *
     * @param prompt       the prompt to display to the user.
     * @param intPredicate the predicate to use for validation.
     * @return a possibly validated integer.
     * @throws IOException when there's a problem with {@link InputStream}
     */
    public int nextInt(String prompt, Predicate<Integer> intPredicate) throws IOException {
        System.out.println(prompt);
        String line;
        Integer num;
        for (
                line = reader.readLine();
                (num = this.tryIntParse(line, intPredicate)) == null;
                line = reader.readLine()
        ) {
            System.out.println("Invalid input.");
        }

        return num;
    }

    private Integer tryIntParse(String line, Predicate<Integer> intPredicate) {
        try {
            var num = Integer.parseInt(line);
            if (intPredicate.test(num)) {
                return num;
            }
            else {
                return null;
            }
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Displays the prompt and reads the input stream, returning a validated real number.
     *
     * @param prompt          the prompt to display to the user.
     * @param doublePredicate the predicate to use for validation.
     * @return a possibly validated real number.
     * @throws IOException when there's a problem with {@link InputStream}
     */
    public double nextDouble(String prompt, Predicate<Double> doublePredicate) throws IOException {
        System.out.println(prompt);
        String line;
        Double num;
        for (
                line = reader.readLine();
                (num = this.tryDoubleParse(line, doublePredicate)) == null;
                line = reader.readLine()
        ) {
            System.out.println("Invalid input.");
        }

        return num;
    }

    private Double tryDoubleParse(String line, Predicate<Double> doublePredicate) {
        try {
            var num = Double.parseDouble(line);
            if (doublePredicate.test(num)) {
                return num;
            }
            else {
                return null;
            }
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Calls {@link InputUtility#nextString(String, Predicate)}.
     * Passes the prompt and a predicate that validates "y", "n", "yes", "no".
     *
     * @param prompt          the prompt to display to the user.
     * @return true for yes and false for no.
     * @throws IOException when there's a problem with {@link InputStream}
     */
    public boolean nextYesNo(String prompt) throws IOException {
        var result = this.nextString(prompt, yesOrNo()).trim().toLowerCase();
        return YES_SYNONYMS.contains(result);
    }

    /**
     * Calls {@link InputUtility#pause(String)}.
     * The prompt defaults to "Press when ready".
     *
     * @throws IOException when there's a problem with {@link InputStream}
     */
    public void pause() throws IOException {
        pause("Press when ready");
    }

    /**
     * Calls nextString{@link InputUtility#nextString(String, Predicate)}.
     * Uses prompt entered and waits for the user to press <enter>.
     *
     * @param prompt the prompt to display to the user.
     * @throws IOException when there's a problem with {@link InputStream}
     */
    public void pause(String prompt) throws IOException {
        this.nextString(prompt, String::isBlank);
    }

    /**
     * Get a String predicate that tests for (case insensitive) "y", "n", "yes", "no".
     *
     * @return a String predicate that tests for (case insensitive) "y", "n", "yes", "no".
     */
    public static Predicate<? super String> yesOrNo() {
        return s -> {
            var cleanedString = s.trim().toLowerCase();
            if (YES_SYNONYMS.contains(cleanedString)) {
                return true;
            }
            else {
                return NO_SYNONYMS.contains(cleanedString);
            }
        };
    }

    /**
     * Get a String predicate that takes two or more Strings and tests whether any one of them match the input.
     *
     * @param these the strings for the input to match to.
     * @return a String predicate that takes two or more Strings and tests whether any one of them match the input.
     */
    public static Predicate<? super String> oneOfThese(String... these) {
        if (these.length < 2) {
            throw new IllegalArgumentException("Must be two or more Strings.");
        }
        return s -> Arrays.stream(these)
                .anyMatch(choice -> choice.equalsIgnoreCase(s.trim()));
    }

    /**
     * Get an int predicate that tests whether the input is between the two limits.
     *
     * @param lower the lower bound
     * @param upper the exclusive upper bound
     * @return an int predicate that tests whether the input is between the two limits.
     */
    public static Predicate<Integer> intRange(int lower, int upper) {
        if (lower > upper) {
            throw new IllegalArgumentException("Lower must be less than upper.");
        }
        return i -> lower <= i && i < upper;
    }

    /**
     * Get a double predicate that tests whether the input is between the two limits.
     *
     * @param lower the lower bound
     * @param upper the exclusive upper bound
     * @return a double predicate that tests whether the input is between the two limits.
     */
    public static Predicate<Double> doubleRange(double lower, double upper) {
        if (lower > upper) {
            throw new IllegalArgumentException("Lower must be less than upper.");
        }
        return d -> lower <= d && d < upper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException {
        if (this.reader != null) {
            this.reader.close();
        }
    }
}
