/*
 * Copyright (C) 2018 Coderanch.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.coderanch.blackjack;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;

import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import static org.junit.Assert.assertThat;

/**
 * Tests the {@link Card} class.
 */
@RunWith(Theories.class)
public final class CardTest {

  @DataPoint
  public static final Card CARD = new Card();
  
  @Theory
  public void rank_neverReturnsNull(Card card) {
    assertThat(card.getRank(), is(notNullValue()));
  }
  
  @Theory
  public void suit_neverReturnsNull(Card card) {
    assertThat(card.getSuit(), is(notNullValue()));
  }
}
