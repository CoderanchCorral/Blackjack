/*
 * Copyright (C) 2018 Coderanch.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.coderanch.test;

import org.junit.Rule;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeThat;

/**
 * Tests the common methods of all objects.
 */
public abstract class ObjectTest {

    /**
     * A rule that states the kind of exception that is expected to be thrown by a test.
     */
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    /**
     * Tests that an object is never equal to null.
     */
    @Theory
    public final void equals_withNull_returnsFalse(
        @FromDataPoints("objects") Object x
    ) {
        assumeNotNull(x);
        assertThat(x, is(not(equalTo(null))));
    }

    /**
     * Tests that an object is always equal to itself.
     */
    @Theory
    public final void equals_isReflexive(
        @FromDataPoints("objects") Object x
    ) {
        assertThat(x, is(equalTo(x)));
    }

    /**
     * Tests that two objects agree with each other on whether they're equal.
     */
    @Theory
    public final void equals_isSymmetric(
        @FromDataPoints("objects") Object x,
        @FromDataPoints("objects") Object y
    ) {
        assumeNotNull(x, y);
        assertThat(x.equals(y), is(equalTo(y.equals(x))));
    }

    /**
     * Tests that three objects agree with each other on whether they're equal.
     */
    @Theory
    public final void equals_isTransitive(
        @FromDataPoints("objects") Object x,
        @FromDataPoints("objects") Object y,
        @FromDataPoints("objects") Object z
    ) {
        assumeNotNull(x, y, z);

        assumeThat(x, is(equalTo(y)));
        assumeThat(y, is(equalTo(z)));
        assertThat(x, is(equalTo(z)));
    }

    /**
     * Tests that if two objects are equal, they must have the same hash code.
     */
    @Theory
    public final void hashCode_withEqualObjects_returnsEqualResult(
        @FromDataPoints("objects") Object x,
        @FromDataPoints("objects") Object y
    ) {
        assumeNotNull(x, y);
        assumeThat(x, is(equalTo(y)));
        assertThat(x.hashCode(), is(equalTo(y.hashCode())));
    }

    /**
     * Tests that an object never has a {@code null} string representation.
     */
    @Theory
    public final void toString_neverReturnsNull(
        @FromDataPoints("objects") Object x
    ) {
        assumeNotNull(x);
        assertThat(x, hasToString(notNullValue()));
    }
}
