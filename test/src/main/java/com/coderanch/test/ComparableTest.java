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

import static java.lang.Integer.signum;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeNoException;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeThat;

/**
 * Tests objects implementing the {@link Comparable} interface.
 */
@SuppressWarnings({ "rawtypes", "unchecked", "squid:S00100" })
public class ComparableTest extends ObjectTest {

    /**
     * Tests that an object is always equal to itself.
     *
     * @param x the object under test.
     */
    @Theory
    public final void compareTo_isReflexive(
        @FromDataPoints("objects") Comparable x
    ) {
        assumeNotNull(x);
        assertThat(x, comparesEqualTo(x));
    }

    /**
     * Tests that two objects agree with each other on which of them is greater, or whether they're equal.
     *
     * @param x the object under test.
     * @param y the object to compare to {@code x}.
     */
    @Theory
    public final void compareTo_isSymmetric(
        @FromDataPoints("objects") Comparable x,
        @FromDataPoints("objects") Comparable y
    ) {
        assumeNotNull(x, y);

        try {
            int comparison = signum(x.compareTo(y));
            assertThat(comparison, is(equalTo(-signum(y.compareTo(x)))));
        }

        catch (RuntimeException ex) {
            assumeNoException(ex);
        }
    }

    /**
     * Tests that two objects throw the same exception if they can't be compared to each other.
     *
     * @param x the object under test.
     * @param y the object to compare to {@code x}.
     */
    @Theory
    public final void compareTo_throwingException_isSymmetric(
        @FromDataPoints("objects") Comparable x,
        @FromDataPoints("objects") Comparable y
    ) {
        try {
            x.compareTo(y);
        }

        catch (RuntimeException ex) {
            thrown.expect(ex.getClass());
            y.compareTo(x);
            throw new AssertionError(ex);
        }
    }

    /**
     * Tests that three objects agree with each other on which of them is greater, or whether they're equal.
     *
     * @param x the object under test.
     * @param y the object to compare to {@code x}.
     * @param z the object to compare to {@code x} and {@code y}.
     */
    @Theory
    public final void compareTo_isTransitive(
        @FromDataPoints("objects") Comparable x,
        @FromDataPoints("objects") Comparable y,
        @FromDataPoints("objects") Comparable z
    ) {
        assumeNotNull(x, y, z);

        try {
            int comparison = signum(x.compareTo(y));

            assumeThat(comparison, is(equalTo(signum(y.compareTo(z)))));
            assertThat(comparison, is(equalTo(signum(x.compareTo(z)))));
        }

        catch (Exception ex) {
            assumeNoException(ex);
        }
    }
}
