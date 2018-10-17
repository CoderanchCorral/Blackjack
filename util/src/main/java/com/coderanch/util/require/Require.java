/*
 * Copyright (C) 2018 Coderanch.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.coderanch.util.require;

import java.util.function.*;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

/**
 * Utility class for enforcing requirements on method parameters.
 */
public final class Require {

    private Require() {}

    /**
     * Requires that the specified argument that was passed to a method matches a certain condition.
     *
     * @param <T>              the type of the method parameter.
     * @param <X>              the type of exception to throw if the argument doesn't match the condition.
     *
     * @param parameterName    the name of the method parameter being validated.
     * @param argument         the argument that was passed to the parameter with name {@code parameterName}.
     * @param matcher          the condition that {@code argument} must match.
     * @param exceptionFactory a function that creates the exception to throw when {@code matcher} fails.
	 *                         The function will be passed the exception message to create the exception with.
     *
     * @return {@code argument}.
     *
     * @throws X if {@code argument} doesn't match the condition described by {@code matcher}.
     */
    public static <T, X extends Throwable> T requireThat(
        String parameterName,
        T argument,
        Matcher<? super T> matcher,
        Function<String, X> exceptionFactory
    ) throws X {
        if (!matcher.matches(argument)) {
            Description description = new StringDescription()
                .appendText("Expected ")
                .appendDescriptionOf(matcher)
                .appendText(String.format(" for parameter '%s', but found ", parameterName))
                .appendValue(argument);

            throw exceptionFactory.apply(description.toString());
        }

        return argument;
    }

    /**
     * Requires that the specified argument that was passed to a method matches a certain condition.
     *
     * Calling this method is is equivalent to calling
	 * {@link
     *     #requireThat(java.lang.String, java.lang.Object, org.hamcrest.Matcher, java.util.function.Function)
     *     requireThat(parameterName, argument, matcher, IllegalArgumentException::new)
     * }.
     *
     * @param <T>           the type of the method parameter.
     *
     * @param parameterName the name of the method parameter being validated.
     * @param argument      the argument that was passed to the parameter with name {@code parameterName}.
     * @param matcher       the condition that {@code argument} must match.
     *
     * @return {@code argument}.
     *
     * @throws IllegalArgumentException if {@code argument} doesn't match the condition described by {@code matcher}.
     */
    public static <T> T requireThat(String parameterName, T argument, Matcher<? super T> matcher) {
        return requireThat(parameterName, argument, matcher, IllegalArgumentException::new);
    }

    /**
     * Requires that the specified index that was passed to a method matches a certain condition.
     *
     * Calling this method is is equivalent to calling
	 * {@link
     *     #requireThat(java.lang.String, java.lang.Object, org.hamcrest.Matcher, java.util.function.Function)
     *     requireThat(parameterName, index, matcher, IndexOutOfBoundsException::new)
     * }.
     *
     * @param <T>           the type of the method parameter.
     *
     * @param parameterName the name of the method parameter being validated.
     * @param index         the index that was passed to the parameter with name {@code parameterName}.
     * @param matcher       the condition that {@code index} must match.
     *
     * @return {@code index}.
     *
     * @throws IndexOutOfBoundsException if {@code index} doesn't match the condition described by {@code matcher}.
     */
    public static <T> T requireThatIndex(String parameterName, T index, Matcher<? super T> matcher) {
        return requireThat(parameterName, index, matcher, IndexOutOfBoundsException::new);
    }
}
