/*
 * Copyright (C) 2018 Coderanch.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.coderanch.blackjack;

import com.coderanch.blackjack.Card.Rank;
import com.coderanch.blackjack.Card.Suit;

import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import static org.junit.Assert.assertThrows;

/**
 * Tests the {@link Cards} class.
 */
@RunWith(Theories.class)
public final class CardsTest {

    /**
     * Tests that {@link Cards#getAllRanks()} returns a set that doesn't contain {@code null}.
     */
    @Theory
    @SuppressWarnings("checkstyle:methodname")
    public void getAllRanks_returnsSetWithoutNull() {
        assertThat("getAllRanks() may not contain null.", Cards.getAllRanks(), not(contains(nullValue())));
    }

    /**
     * Tests that {@link Cards#getAllRanks()} returns an unmodifiable set.
     */
    @Theory
    @SuppressWarnings("checkstyle:methodname")
    public void getAllRanks_returnsUnmodifiableSet() {
        var allRanks = Cards.getAllRanks();
        var rankToAdd = Rank.values()[0];

        assertThrows("getAllRanks() must be unmodifiable.", UnsupportedOperationException.class, () -> {
            allRanks.add(rankToAdd);
        });
    }

    /**
     * Tests that {@link Cards#getAllRanks()} returns a set that contains all values defined in {@link Rank}.
     */
    @Theory
    @SuppressWarnings("checkstyle:methodname")
    public void getAllRanks_returnsAllRanks() {
        assertThat("getAllRanks() must contain all ranks.", Cards.getAllRanks(), containsInAnyOrder(Rank.values()));
    }

    /**
     * Tests that {@link Cards#getAllSuits()} returns a set that doesn't contain {@code null}.
     */
    @Theory
    @SuppressWarnings("checkstyle:methodname")
    public void getAllSuits_returnsSetWithoutNull() {
        assertThat("getAllSuits() may not contain null.", Cards.getAllSuits(), not(contains(nullValue())));
    }

    /**
     * Tests that {@link Cards#getAllSuits()} returns an unmodifiable set.
     */
    @Theory
    @SuppressWarnings("checkstyle:methodname")
    public void getAllSuits_returnsUnmodifiableSet() {
        var allSuits = Cards.getAllSuits();
        var suitToAdd = Suit.values()[0];

        assertThrows("getAllSuits() must be unmodifiable.", UnsupportedOperationException.class, () -> {
            allSuits.add(suitToAdd);
        });
    }

    /**
     * Tests that {@link Cards#getAllSuits()} returns a set that contains all values defined in {@link Suit}.
     */
    @Theory
    @SuppressWarnings("checkstyle:methodname")
    public void getAllSuits_returnsAllSuits() {
        assertThat("getAllSuits() must contain all suits.", Cards.getAllSuits(), containsInAnyOrder(Suit.values()));
    }

    /**
     * Tests that {@link Cards#getStandardDeck()} returns a set that doesn't contain {@code null}.
     */
    @Theory
    @SuppressWarnings("checkstyle:methodname")
    public void getStandardDeck_returnsSetWithoutNull() {
        assertThat("getStandardDeck() may not contain null.", Cards.getStandardDeck(), not(contains(nullValue())));
    }

    /**
     * Tests that {@link Cards#getStandardDeck()} returns an unmodifiable set.
     */
    @Theory
    @SuppressWarnings("checkstyle:methodname")
    public void getStandardDeck_returnsUnmodifiableSet() {
        var standardDeck = Cards.getStandardDeck();
        var cardToAdd = new Card(Rank.values()[0], Suit.values()[0]);

        assertThrows("getStandardDeck() must be unmodifiable.", UnsupportedOperationException.class, () -> {
            standardDeck.add(cardToAdd);
        });
    }

    /**
     * Tests that {@link Cards#getStandardDeck()} returns a set that contains all possible distinct cards that can be
     * made using a combination of a {@link Rank} and a {@link Suit}.
     */
    @Theory
    @SuppressWarnings("checkstyle:methodname")
    public void getStandardDeck_returnsAllCombinationsOfRankAndSuit() {
        assertThat(
            "getStandardDeck() must contain all combinations of a rank and suit.",
            Cards.getStandardDeck().size(),
            is(equalTo(Rank.values().length * Suit.values().length))
        );
    }
}
