package com.coderanch.util.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collector;

/**
 * Multipurpose input utility for use with a command line interface.
 * The user can pass a prompt and optional validation.
 * The utility will read the provided input stream and return a possibly validated String or primitive.
 */
public class InputUtility {

    private static final List<String> YES = Collections.unmodifiableList(List.of("y", "yes"));
    private static final List<String> NO = Collections.unmodifiableList(List.of("n",  "no"));

    private InputStream inputStream;

    /**
     * If no input stream is provided, System.in will be used.
     */
    public InputUtility() {
        this(System.in);
    }

    /**
     * Constructor that takes an input stream to be used.
     *
     * @param inputStream input stream to be used.
     */
    public InputUtility(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * Displays the prompt and reads the input stream, returning a possibly validated String.
     *
     * @param prompt the prompt to display to the user.
     * @param stringPredicate the predicate to use for validation.
     * @return a possibly validated String.
     * @throws IOException
     */
    public String nextString(String prompt, Predicate<String> stringPredicate) throws IOException {
        System.out.println(prompt);
        try(var br = new BufferedReader(new InputStreamReader(inputStream))){
            while(true) {
              var line = br.readLine();
              if(stringPredicate.test(line)) {
                  return line;
              }
              else {
                  System.out.println("Invalid input.");
              }
            }
        }
    }

    /**
     * Displays the prompt and reads the input stream, returning a possibly validated integer.
     *
     * @param prompt the prompt to display to the user.
     * @param intPredicate the predicate to use for validation.
     * @return a possibly validated integer.
     * @throws IOException
     */
    public int nextInt(String prompt, Predicate<Integer> intPredicate) throws IOException {
        System.out.println(prompt);
        try(var br = new BufferedReader(new InputStreamReader(inputStream))){
            while(true) {
              var line = br.readLine();
                try {
                    var num = Integer.parseInt(line);
                    if(intPredicate.test(num)) {
                        return num;
                    }
                    else {
                        System.out.println("Invalid input.");
                    }
                } catch (Exception e) {
                    System.out.println("Must be integer.");
                }
            }
        }
    }

    /**
     * Displays the prompt and reads the input stream, returning a possibly validated real number.
     *
     * @param prompt the prompt to display to the user.
     * @param doublePredicate the predicate to use for validation.
     * @return a possibly validated real number.
     * @throws IOException
     */
    public double nextDouble(String prompt, Predicate<Double> doublePredicate) throws IOException {
        System.out.println(prompt);
        try(var br = new BufferedReader(new InputStreamReader(inputStream))){
            while(true) {
              var line = br.readLine();
                try {
                    var num = Double.parseDouble(line);
                    if(doublePredicate.test(num)) {
                        return num;
                    }
                    else {
                        System.out.println("Invalid input.");
                    }
                } catch (Exception e) {
                    System.out.println("Must be double.");
                }
            }
        }
    }

    /**
     * Calls {@link InputUtility#nextString(String, Predicate)}.
     * Passes the prompt and a predicate that validates "y", "n", "yes", "no".
     * 
     * @param prompt the prompt to display to the user.
     * @param stringPredicate the predicate to use for validation.
     * @return true for yes and false for no.
     * @throws IOException
     */
    public boolean nextYesNo(String prompt, Predicate<String> stringPredicate) throws IOException {
        var result = this.nextString(prompt, stringPredicate).trim().toLowerCase();
        if(YES.contains(result)) {
            return true;
        }
        else return false;
    }

    /**
     * Calls {@link InputUtility#pause(String)}.
     * The prompt defaults to "Press when ready".
     *
     * @throws IOException
     */
    public void pause() throws IOException {
        pause("Press when ready");
    }

    /**
     * Calls nextString{@link InputUtility#nextString(String, Predicate)}.
     * Uses prompt entered and waits for the user to press ".".
     *
     * @param prompt the prompt to display to the user.
     * @throws IOException
     */
    public void pause(String prompt) throws IOException {
        this.nextString(prompt, s -> s.trim().equals("."));
    }

    /**
     * Get a String predicate that tests for (case insensitive) "y", "n", "yes", "no".
     *
     * @return  a String predicate that tests for (case insensitive) "y", "n", "yes", "no".
     */
    public static Predicate<String> yesOrNo() {
        return s -> {
            var cleanedString = s.trim().toLowerCase();
            if(YES.contains(cleanedString)) {
                return true;
            }
            else if(NO.contains(cleanedString)) {
                return true;
            }
            return false;
        };
    }

    /**
     * Get a String predicate that takes two or more Strings and tests whether any one of them match the input.
     *
     * @param these the strings for the input to match to.
     * @return a String predicate that takes two or more Strings and tests whether any one of them match the input.
     */
    public static Predicate<String> oneOfThese(String...these){
        return s -> {
            var result = Arrays.stream(these)
                    .filter(option -> option.equalsIgnoreCase(s))
                    .findFirst()
                    .orElse(null);
            return result != null;
        };
    }

    /**
     * Get an int predicate that tests whether the input is between the two limits.
     *
     * @param lower the lower bound
     * @param upper the exclusive upper bound
     * @return an int predicate that tests whether the input is between the two limits.
     */
    public static Predicate<Integer> intRange(int lower, int upper) {
        return i -> {
            if(i >= upper) {
                return false;
            }
            if(i < lower) {
                return false;
            }
            return true;
        };
    }

    /**
     * Get a double predicate that tests whether the input is between the two limits.
     *
     * @param lower the lower bound
     * @param upper the exclusive upper bound
     * @return a double predicate that tests whether the input is between the two limits.
     */
    public static Predicate<Double> doubleRange(double lower, double upper) {
        return d -> {
            if(d >= upper) {
                return false;
            }
            if(d < lower) {
                return false;
            }
            return true;
        };
    }
}
