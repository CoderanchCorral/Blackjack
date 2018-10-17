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

@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class ComparableTest extends ObjectTest {

    @Theory
    public final void compareTo_isReflexive(
        @FromDataPoints("objects") Comparable x
    ) {
        assumeNotNull(x);
        assertThat(x, comparesEqualTo(x));
    }

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

        catch (Exception ex) {
            assumeNoException(ex);
        }
    }

    @Theory
    public final void compareTo_throwingException_isSymmetric(
        @FromDataPoints("objects") Comparable x,
        @FromDataPoints("objects") Comparable y
    ) {
        try {
            x.compareTo(y);
        }

        catch (Exception ex) {
            thrown.expect(ex.getClass());
            y.compareTo(x);
        }
    }

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
