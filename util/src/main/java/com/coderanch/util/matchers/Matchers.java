/*
 * Copyright (C) 2018 Coderanch.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.coderanch.util.matchers;

import java.util.function.*;

import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;

/**
 * Utility class to create common matchers.
 */
public final class Matchers {

    private Matchers() {}

    private static final Matcher<String> TRIMMED_STRING_MATCHER = createTypeSafeMatcher(
        "a trimmed string",
        string -> string.trim().equals(string)
    );

    /**
     * Creates a type-safe matcher from a description and a predicate.
     *
     * @param <T>         the type of the argument that will be matched.
     *
     * @param description a description of the argument that will be matched.
     * @param predicate   a predicate that will be applied to the argument when it is matched.
     *
     * @return a matcher with the given {@code description} that will successfully match an argument
     *         if the {@code predicate} on that argument returns {@code true}.
     */
    public static <T> Matcher<T> createTypeSafeMatcher(String description, Predicate<? super T> predicate) {
        return new CustomTypeSafeMatcher<T>(description) {
            @Override
            protected boolean matchesSafely(T item) {
                return predicate.test(item);
            }
        };
    }

    /**
     * Returns a matcher that matches when the examined string is trimmed.
     *
     * @return a matcher that returns {@code true} when the examined string doesn't
     *         start or end with whitespace characters.
     */
    public static Matcher<String> trimmedString() {
        return TRIMMED_STRING_MATCHER;
    }

    /**
     * Returns a matcher that matches when the examined string is trimmed and non-empty.
     *
     * @return a matcher that returns {@code true} when the examined string doesn't
     *         start or end with whitespace characters, and also isn't empty.
     */
    public static Matcher<String> nonEmptyTrimmedString() {
        return both(trimmedString()).and(not(isEmptyString()));
    }
}
