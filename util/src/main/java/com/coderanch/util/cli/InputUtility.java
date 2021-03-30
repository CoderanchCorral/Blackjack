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
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.coderanch.util.require.Require.requireThat;
import static org.hamcrest.Matchers.*;

/**
 * Multipurpose input utility for use with a command line interface.
 * The user can pass a prompt and optional validation.
 * The utility will read the provided input stream and return a possibly validated String or primitive.
 */
public final class InputUtility implements Closeable {

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
     * @return a string that's valid according to {@code stringPredicate}.
     * @throws IOException if there's a problem while reading from the underlying stream.
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
     * @return an integer that's valid according to {@code intPredicate}.
     * @throws IOException if there's a problem while reading from the underlying stream.
     */
    public int nextInt(String prompt, Predicate<? super Integer> intPredicate) throws IOException {
        System.out.println(prompt);
        String line;
        OptionalInt num;
        for (
            line = reader.readLine(), num = tryIntParse(line, intPredicate);
            num.isEmpty();
            line = reader.readLine(), num = tryIntParse(line, intPredicate)
        ) {
            System.out.println("Invalid input.");
        }
        return num.getAsInt();
    }

    /**
     * Tries to parse an integer from the specified line and validates it.
     *
     * @param line         the string to parse.
     * @param intPredicate the predicate used to validate the integer.
     * @return an integer parsed from {@code line} if it's valid according to {@code intPredicate};
     *         {@code Optional.empty()} otherwise.
     */
    private OptionalInt tryIntParse(String line, Predicate<? super Integer> intPredicate) {
        try {
            var num = Integer.parseInt(line);
            return intPredicate.test(num) ? OptionalInt.of(num) : OptionalInt.empty();
        }
        catch (NumberFormatException e) {
            return OptionalInt.empty();
        }
    }

    /**
     * Displays the prompt and reads the input stream, returning a validated floating point number.
     *
     * @param prompt          the prompt to display to the user.
     * @param doublePredicate the predicate to use for validation.
     * @return a validated floating point number.
     * @throws IOException if there's a problem while reading from the underlying stream.
     */
    public double nextDouble(String prompt, Predicate<? super Double> doublePredicate) throws IOException {
        System.out.println(prompt);
        String line;
        OptionalDouble num;
        for (
            line = reader.readLine(), num = OptionalDouble.empty();
            num.isEmpty();
            line = reader.readLine()
        ) {
            num = tryDoubleParse(line, doublePredicate);
            if (num.isEmpty()) {
                System.out.println("Invalid input.");
            }
        }
        return num.getAsDouble();
    }

    /**
     * Tries to parse a floating point number from the string and validates it.
     *
     * @param line            the string to parse.
     * @param doublePredicate the predicate used to validate the double.
     * @return a validated Double or nothing.
     */
    private OptionalDouble tryDoubleParse(String line, Predicate<? super Double> doublePredicate) {
        try {
            var num = Double.parseDouble(line);
            return doublePredicate.test(num) ? OptionalDouble.of(num) : OptionalDouble.empty();
        }
        catch (NumberFormatException e) {
            return OptionalDouble.empty();
        }
    }

    /**
     * Gets the next yes or no answer.
     * Calls {@link InputUtility#nextString(String, Predicate)}.
     * Passes the prompt and a predicate that validates "y", "n", "yes", "no".
     *
     * @param prompt the prompt to display to the user.
     * @return true for yes and false for no.
     * @throws IOException if there's a problem while reading from the underlying stream.
     */
    public boolean nextYesNo(String prompt) throws IOException {
        var result = this.nextString(prompt, yesOrNo()).trim().toLowerCase();
        return YES_SYNONYMS.contains(result);
    }

    /**
     * Waits for the user to press &lt;enter&gt;.
     * The prompt defaults to "Press when ready".
     * Calls {@link InputUtility#pause(String)}.
     *
     * @throws IOException if there's a problem while reading from the underlying stream.
     */
    public void pause() throws IOException {
        pause("Press enter when ready");
    }

    /**
     * Uses prompt entered and waits for the user to press &lt;enter&gt;.
     * Calls nextString{@link InputUtility#nextString(String, Predicate)}.
     *
     * @param prompt the prompt to display to the user.
     * @throws IOException if there's a problem while reading from the underlying stream.
     */
    public void pause(String prompt) throws IOException {
        this.nextString(prompt, String::isBlank);
    }

    /**
     * Get a String predicate that tests for (case insensitive) "y", "n", "yes", "no".
     *
     * @return a String predicate that tests for (case insensitive) "y", "n", "yes", "no".
     */
    public static Predicate<String> yesOrNo() {
        return s -> {
            var cleanedString = s.trim().toLowerCase();
            return YES_SYNONYMS.contains(cleanedString) || NO_SYNONYMS.contains(cleanedString);
        };
    }


    /**
     * Get a String predicate that takes two or more Strings and tests whether any one of them match the input.
     *
     * @param firstOption the first of the strings to match to.
     * @param otherOptions the strings for the input to match to.
     * @return a String predicate that takes one or more Strings and tests whether any one of them match the input.
     */
    public static Predicate<String> oneOfThese(String firstOption, String... otherOptions) {
        return s -> Stream.concat(Arrays.stream(otherOptions), Stream.of(firstOption))
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
        requireThat("lower", lower, is(lessThan(upper)));
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
        requireThat("lower", lower, is(lessThan(upper)));
        return d -> lower <= d && d < upper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException {
        this.reader.close();
    }
}
