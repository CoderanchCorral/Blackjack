/*
 * Copyright (C) 2018 Coderanch.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.coderanch.blackjack;

import java.util.*;

import com.coderanch.blackjack.Card.Rank;
import com.coderanch.blackjack.Card.Suit;
import com.coderanch.test.ConsistentComparableTest;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import static org.junit.Assert.assertThrows;
import static org.junit.Assume.assumeThat;

/**
 * Tests the {@link Card} class.
 */
@RunWith(Theories.class)
public final class CardTest extends ConsistentComparableTest {

    /**
     * Ranks to test cards with.
     */
    @DataPoints
    public static final Set<Rank> RANKS = Cards.getAllRanks();

    /**
     * The {@code null} rank.
     */
    @DataPoint
    public static final Rank NULL_RANK = null;

    /**
     * Suits to test cards with.
     */
    @DataPoints
    public static final Set<Suit> SUITS = Cards.getAllSuits();

    /**
     * The {@code null} suit.
     */
    @DataPoint
    public static final Suit NULL_SUIT = null;

    /**
     * Cards to test.
     */
    @DataPoints("objects")
    public static final Set<Card> CARDS = Cards.getStandardDeck();

    /**
     * The {@code null} card.
     */
    @DataPoint("objects")
    public static final Card NULL_CARD = null;

    /**
     * Tests that passing {@code null} for {@code rank} when constructing a new card causes an exception to be thrown.
     *
     * @param suit the suit to construct the card with.
     */
    @Theory(nullsAccepted = false)
    @SuppressWarnings("checkstyle:methodname")
    public void newCard_withNullRank_throwsException(Suit suit) {
        assertThrows(
            "Constructing a new card with a null rank must throw an IllegalArgumentException.",
            IllegalArgumentException.class,
            () -> new Card(null, suit)
        );
    }

    /**
     * Tests that passing {@code null} for {@code suit} when constructing a new card causes an exception to be thrown.
     *
     * @param rank the rank to construct the card with.
     */
    @Theory(nullsAccepted = false)
    @SuppressWarnings("checkstyle:methodname")
    public void newCard_withNullSuit_throwsException(Rank rank) {
        assertThrows(
            "Constructing a new card with a null suit must throw an IllegalArgumentException.",
            IllegalArgumentException.class,
            () -> new Card(rank, null)
        );
    }

    /**
     * Tests that a newly constructed card has the same {@link Card#getRank() rank} and {@link Card#getSuit() suit} as
     * the values it was constructed with.
     *
     * @param rank the rank to construct the card with.
     * @param suit the suit to construct the card with.
     */
    @Theory(nullsAccepted = false)
    @SuppressWarnings("checkstyle:methodname")
    public void newCard_withRankAndSuit_isConsistent(Rank rank, Suit suit) {
        var card = new Card(rank, suit);

        assertThat("A new card must have the same rank it was constructed with.", card.rank(), is(rank));
        assertThat("A new card must have the same suit is was constructed with.", card.suit(), is(suit));
    }

    /**
     * Tests that two cards constructed with the same rank and suit are equal.
     *
     * @param rank the rank to construct both cards with.
     * @param suit the suit to construct both cards with.
     */
    @Theory(nullsAccepted = false)
    @SuppressWarnings("checkstyle:methodname")
    public void cards_withSameRankAndSuit_areEqual(Rank rank, Suit suit) {
        var firstCard  = new Card(rank, suit);
        var secondCard = new Card(rank, suit);

        assertThat("Cards that have the same rank and suit must be equal.", firstCard, is(equalTo(secondCard)));
    }

    /**
     * Tests that two cards constructed with different ranks are not equal.
     *
     * @param firstRank  the rank to construct the first card with.
     * @param secondRank the rank to construct the second card with.
     * @param suit       the suit to construct both cards with.
     */
    @Theory(nullsAccepted = false)
    @SuppressWarnings("checkstyle:methodname")
    public void cards_withDifferentRanks_areNotEqual(Rank firstRank, Rank secondRank, Suit suit) {
        assumeThat(firstRank, is(not(equalTo(secondRank))));

        var firstCard  = new Card(firstRank, suit);
        var secondCard = new Card(secondRank, suit);

        assertThat("Cards that have a different rank may not be equal.", firstCard, is(not(equalTo(secondCard))));
    }

    /**
     * Tests that two cards constructed with different suits are not equal.
     *
     * @param rank       the rank to construct both cards with.
     * @param firstSuit  the suit to construct the first card with.
     * @param secondSuit the suit to construct the second card with.
     */
    @Theory(nullsAccepted = false)
    @SuppressWarnings("checkstyle:methodname")
    public void cards_withDifferentSuits_areNotEqual(Rank rank, Suit firstSuit, Suit secondSuit) {
        assumeThat(firstSuit, is(not(equalTo(secondSuit))));

        var firstCard  = new Card(rank, firstSuit);
        var secondCard = new Card(rank, secondSuit);

        assertThat("Cards that have a different suit may not be equal.", firstCard, is(not(equalTo(secondCard))));
    }

    /**
     * Tests that the string representation of a card contains the string representations of its rank and suit.
     *
     * @param card the card to test.
     */
    @Theory(nullsAccepted = false)
    @SuppressWarnings("checkstyle:methodname")
    public void toString_containsRankAndSuit(Card card) {
        assertThat(
            "A card's string representation must contain the string representations of the card's rank and suit.",
            card,
            hasToString(both(
                containsString(card.rank().toString())).and(
                containsString(card.suit().toString()))
            )
        );
    }
}
