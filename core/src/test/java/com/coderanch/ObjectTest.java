package com.coderanch;

import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theory;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeThat;

public abstract class ObjectTest {

  @Theory
  public void equals_isReflexive(
    @FromDataPoints("objects") Object x
  ) {
    assumeNotNull(x);
    assertThat(x, is(equalTo(x)));
  }

  @Theory
  public void equals_isSymmetric(
    @FromDataPoints("objects") Object x,
    @FromDataPoints("objects") Object y
  ) {
    assumeNotNull(x, y);
    assumeThat(x, is(equalTo(y)));
    assertThat(y, is(equalTo(x)));
  }

  @Theory
  public void equals_isTransitive(
    @FromDataPoints("objects") Object x,
    @FromDataPoints("objects") Object y,
    @FromDataPoints("objects") Object z
  ) {
    assumeNotNull(x, y, z);
    assumeThat(x, is(equalTo(y)));
    assumeThat(y, is(equalTo(z)));
    assertThat(x, is(equalTo(z)));
  }

  @Theory
  public void equals_withNull_returnsFalse(
    @FromDataPoints("objects") Object x
  ) {
    assumeNotNull(x);
    assertThat(x, is(not(equalTo(null))));
  }

  @Theory
  public void equalObjects_returnEqualHashCodes(
    @FromDataPoints("objects") Object x,
    @FromDataPoints("objects") Object y
  ) {
    assumeNotNull(x, y);
    assumeThat(x, is(equalTo(y)));
    assertThat(x.hashCode(), is(equalTo(y.hashCode())));
  }

  @Theory
  public void toString_neverReturnsNull(
    @FromDataPoints("objects") Object x
  ) {
    assumeNotNull(x);
    assertThat(x.toString(), is(notNullValue()));
  }
}