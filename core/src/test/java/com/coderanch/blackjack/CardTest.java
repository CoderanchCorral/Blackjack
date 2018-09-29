/*
 * Copyright (C) 2018 Coderanch.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.coderanch.blackjack;

import java.util.*;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import com.coderanch.ObjectTest;
import com.coderanch.blackjack.Card.Rank;
import com.coderanch.blackjack.Card.Suit;

import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeThat;

/**
 * Tests the {@link Card} class.
 */
@RunWith(Theories.class)
public final class CardTest extends ObjectTest {

  /**
   * Ranks to test cards with.
   */
  @DataPoints
  public static final Set<Rank> RANKS = Cards.getAllRanks();

  /**
   * Suits to test cards with.
   */
  @DataPoints
  public static final Set<Suit> SUITS = Cards.getAllSuits();

  /**
   * Cards to test.
   */
  @DataPoints("objects")
  public static final Set<Card> CARDS = Cards.getStandardDeck();

  /**
   * A rule that states the kind of exception that is expected to be thrown by a test.
   */
  @Rule
  public final ExpectedException thrown = ExpectedException.none();

  /**
   * Tests that passing {@code null} for {@code rank} when constructing a new card causes an exception to be thrown.
   *
   * @param suit the suit to construct the card with.
   */
  @Theory
  public void newCard_withNullRank_throwsException(Suit suit) {
    assumeNotNull(suit);

    thrown.expect(IllegalArgumentException.class);

    Card card = Card.of(null, suit);
  }

  /**
   * Tests that passing {@code null} for {@code suit} when constructing a new card causes an exception to be thrown.
   *
   * @param rank the rank to construct the card with.
   */
  @Theory
  public void newCard_withNullSuit_throwsException(Rank rank) {
    assumeNotNull(rank);

    thrown.expect(IllegalArgumentException.class);

    Card card = Card.of(rank, null);
  }
  
  @Theory
  public void newCard_withRankAndSuit_isConsistent(Rank rank, Suit suit) {
    assumeNotNull(rank, suit);

    Card card = Card.of(rank, suit);

    assertThat(card.rank(), is(rank));
    assertThat(card.suit(), is(suit));
  }

  /**
   * Tests that two cards constructed with the same rank and suit are equal.
   *
   * @param rank the rank to construct both cards with.
   * @param suit the suit to construct both cards with.
   */
  @Theory
  public void cards_withSameRankAndSuit_areEqual(Rank rank, Suit suit) {
    assumeNotNull(rank, suit);

    Card firstCard  = Card.of(rank, suit);
    Card secondCard = Card.of(rank, suit);

    assertThat(firstCard, is(equalTo(secondCard)));
  }

  /**
   * Tests that two cards constructed with different ranks are not equal.
   *
   * @param firstRank  the rank to construct the first card with.
   * @param secondRank the rank to construct the second card with.
   * @param suit       the suit to construct both cards with.
   */
  @Theory
  public void cards_withDifferentRanks_areNotEqual(Rank firstRank, Rank secondRank, Suit suit) {
    assumeNotNull(firstRank, secondRank, suit);
    assumeThat(firstRank, is(not(equalTo(secondRank))));

    Card firstCard  = Card.of(firstRank,  suit);
    Card secondCard = Card.of(secondRank, suit);

    assertThat(firstCard, is(not(equalTo(secondCard))));
  }

  /**
   * Tests that two cards constructed with different suits are not equal.
   *
   * @param rank       the rank to construct both cards with.
   * @param firstSuit  the suit to construct the first card with.
   * @param secondSuit the suit to construct the second card with.
   */
  @Theory
  public void cards_withDifferentSuits_areNotEqual(Rank rank, Suit firstSuit, Suit secondSuit) {
    assumeNotNull(rank, firstSuit, secondSuit);
    assumeThat(firstSuit, is(not(equalTo(secondSuit))));

    Card firstCard  = Card.of(rank, firstSuit);
    Card secondCard = Card.of(rank, secondSuit);

    assertThat(firstCard, is(not(equalTo(secondCard))));
  }

  /**
   * Tests that the string representation of a card contains the string representations of its rank and suit.
   *
   * @param card the card to test.
   */
  @Theory
  public void toString_containsRankAndSuit(Card card) {
    assumeNotNull(card);

    assertThat(card.toString(), both(
      containsString(card.rank().toString())).and(
      containsString(card.suit().toString()))
    );
  }
}