/*
 * Copyright (C) 2018 Coderanch.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.coderanch.blackjack;

import java.util.*;

import static java.util.Comparator.comparing;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import static com.coderanch.util.require.Require.requireThat;

/**
 * A playing card used in a game of Blackjack.
 */
final class Card implements Comparable<Card> {

    /**
     * The rank of a card.
     */
    enum Rank {
        ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
    }

    /**
     * The suit of a card.
     */
    enum Suit {
        SPADES, HEARTS, CLUBS, DIAMONDS
    }

    private final Rank rank;
    private final Suit suit;

    /**
     * Constructs a card with a specified rank and suit.
     *
     * @param rank the rank of the new card.
     * @param suit the suit of the new card.
     *
     * @throws IllegalArgumentException if either {@code rank} or {@code suit} is {@code null}.
     */
    Card(Rank rank, Suit suit) {
        this.rank = requireThat("rank", rank, is(notNullValue()));
        this.suit = requireThat("suit", suit, is(notNullValue()));
    }

    /**
     * Gets the rank of this card.
     *
     * @return the rank of this card; never {@code null}.
     */
    Rank rank() {
        return this.rank;
    }

    /**
     * Gets the suit of this card.
     *
     * @return the suit of this card; never {@code null}.
     */
    Suit suit() {
        return this.suit;
    }

    /**
     * Compares this card to another card.
     *
     * Cards are first compared by rank, then by suit.
     *
     * @param other {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int compareTo(Card other) {
        return comparing(Card::rank).thenComparing(Card::suit).compare(this, other);
    }

    /**
     * Compares this card to another object for equality.
     *
     * Cards are considered equal if they have the same rank and suit.
     *
     * @param object {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean equals(Object object) {
        return object instanceof Card && this.compareTo((Card) object) == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
