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
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeThat;

@SuppressWarnings({ "rawtypes", "unchecked", "squid:S00100" })
public class ConsistentComparableTest extends ComparableTest {

    @Theory
    public final void compareTo_withEqualObjects_returnsZero(
        @FromDataPoints("objects") Comparable x,
        @FromDataPoints("objects") Comparable y
    ) {
        assumeNotNull(x, y);
        assumeThat(x, is(equalTo(y)));
        assertThat(x.compareTo(y), is(equalTo(0)));
    }

    @Theory
    public final void compareTo_withUnequalObjects_neverReturnsZero(
        @FromDataPoints("objects") Comparable x,
        @FromDataPoints("objects") Comparable y
    ) {
        assumeNotNull(x, y);
        assumeThat(x, is(not(equalTo(y))));

        try {
            assertThat(x.compareTo(y), is(not(equalTo(0))));
        }

        catch (Exception ex) {
            assumeNoException(ex);
        }
    }
}
