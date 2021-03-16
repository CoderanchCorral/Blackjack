/*
 * Copyright (C) 2018 Coderanch.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.coderanch.test;

import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theory;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeNoException;
import static org.junit.Assume.assumeThat;

/**
 * Tests whether objects implementing the {@link Comparable} interface are consistent-with-equals.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ConsistentComparableTest extends ComparableTest {

    /**
     * Tests that objects that are equal according to the {@link Object#equals(java.lang.Object)} method
     * are also equal according to the {@link Comparable#compareTo(java.lang.Object)} method.
     *
     * @param x the object under test.
     * @param y the object to compare to {@code x}.
     */
    @Theory(nullsAccepted = false)
    public final void compareTo_withEqualObjects_returnsZero(
        @FromDataPoints("objects") Comparable x,
        @FromDataPoints("objects") Comparable y
    ) {
        assumeThat(x, is(equalTo(y)));
        assertThat(x.compareTo(y), is(equalTo(0)));
    }

    /**
     * Tests that objects that are unequal according to the {@link Object#equals(java.lang.Object)} method
     * are also unequal according to the {@link Comparable#compareTo(java.lang.Object)} method.
     *
     * @param x the object under test.
     * @param y the object to compare to {@code x}.
     */
    @Theory(nullsAccepted = false)
    public final void compareTo_withUnequalObjects_neverReturnsZero(
        @FromDataPoints("objects") Comparable x,
        @FromDataPoints("objects") Comparable y
    ) {
        assumeThat(x, is(not(equalTo(y))));

        try {
            assertThat(x.compareTo(y), is(not(equalTo(0))));
        }

        catch (RuntimeException ex) {
            assumeNoException(ex);
        }
    }
}
