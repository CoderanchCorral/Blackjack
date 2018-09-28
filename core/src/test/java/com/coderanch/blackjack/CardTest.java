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

import com.coderanch.blackjack.Card.Rank;
import com.coderanch.blackjack.Card.Suit;

import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collectors.toCollection;

/**
 * Tests the {@link Card} class.
 */
@RunWith(Theories.class)
public final class CardTest {
  
  @DataPoints
  public static final Set<Rank> RANKS = unmodifiableSet(new LinkedHashSet<>(asList(Rank.values())));

  @DataPoints
  public static final Set<Suit> SUITS = unmodifiableSet(new LinkedHashSet<>(asList(Suit.values())));

  @DataPoints
  public static final Set<Card> CARDS = unmodifiableSet(
    SUITS.stream().flatMap(
      suit -> RANKS.stream().map(
        rank -> new Card(rank, suit)
      )
    ).collect(toCollection(() -> new LinkedHashSet<Card>()))
  );

  @Rule
  public ExpectedException exceptionRule = ExpectedException.none();

  @Theory
  public void newCard_withNullRank_throwsException(Suit suit) {
    exceptionRule.expect(IllegalArgumentException.class);
    new Card(null, suit);
  }

  @Theory
  public void newCard_withNullSuit_throwsException(Rank rank) {
    exceptionRule.expect(IllegalArgumentException.class);
    new Card(rank, null);
  }
  
  @Theory
  public void newCard_withRankAndSuit_isConsistent(Rank rank, Suit suit) {
    Card card = new Card(rank, suit);

    assertThat(card.getRank(), is(rank));
    assertThat(card.getSuit(), is(suit));
  }

  @Theory
  public void cards_withSameRankAndSuit_areEqual(Rank rank, Suit suit) {
    Card firstCard  = new Card(rank, suit);
    Card secondCard = new Card(rank, suit);

    assertThat(firstCard, is(equalTo(secondCard)));
  }

  @Theory
  public void cards_withDifferentRanks_areNotEqual(Rank firstRank, Rank secondRank, Suit suit) {
    assumeThat(firstRank, is(not(equalTo(secondRank))));

    Card firstCard  = new Card(firstRank,  suit);
    Card secondCard = new Card(secondRank, suit);

    assertThat(firstCard, is(not(equalTo(secondCard))));
  }

  @Theory
  public void cards_withDifferentSuits_areNotEqual(Rank rank, Suit firstSuit, Suit secondSuit) {
    assumeThat(firstSuit, is(not(equalTo(secondSuit))));

    Card firstCard  = new Card(rank, firstSuit);
    Card secondCard = new Card(rank, secondSuit);

    assertThat(firstCard, is(not(equalTo(secondCard))));
  }

  @Theory
  public void toString_containsRankAndSuit(Card card) {
    assertThat(card.toString(), both(
      containsString(card.getRank().toString())).and(
      containsString(card.getSuit().toString()))
    );
  }
}