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

import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collectors.toCollection;

/**
 * Utility class containing useful operations related to playing cards.
 */
final class Cards {

    /**
     * An unmodifiable set containing all card ranks.
     */
    private static final Set<Rank> RANKS = unmodifiableSet(EnumSet.allOf(Rank.class));

    /**
     * An unmodifiable set containing all card suits.
     */
    private static final Set<Suit> SUITS = unmodifiableSet(EnumSet.allOf(Suit.class));

    /**
     * An unmodifiable set containing all distinct cards.
     */
    private static final Set<Card> STANDARD_DECK = unmodifiableSet(
        RANKS.stream().flatMap(
            rank -> SUITS.stream().map(
                suit -> new Card(rank, suit)
            )
        ).collect(toCollection(() -> new LinkedHashSet<>(RANKS.size() * SUITS.size())))
    );

    private Cards() { }

    /**
     * Gets all ranks a card can consist of.
     *
     * @return an unmodifiable set containing all {@linkplain Rank#values() card ranks}.
     */
    static Set<Rank> getAllRanks() {
        return RANKS;
    }

    /**
     * Gets all suits a card can consist of.
     *
     * @return an unmodifiable set containing all {@linkplain Suit#values() card suits}.
     */
    static Set<Suit> getAllSuits() {
        return SUITS;
    }

    /**
     * Gets a standard deck of cards.
     *
     * @return an unmodifiable set containing all distinct cards that can be made
     *         using a combination of a {@link Rank} and a {@link Suit}.
     */
    static Set<Card> getStandardDeck() {
        return STANDARD_DECK;
    }

    /**
     * Gets a shuffled standard deck of cards.
     *
     * @param generator a random number generator that is used to shuffle the cards.
     * @return an unmodifiable set containing all distinct cards that can be made
     *         using a combination of a {@link Rank} and a {@link Suit}.
     *         The cards will be shuffled with a distribution that depends on the properties of {@code generator}.
     */
    static Set<Card> getShuffledStandardDeck(Random generator) {
        var cards = new ArrayList<>(getStandardDeck());
        Collections.shuffle(cards, generator);
        return unmodifiableSet(new LinkedHashSet<>(cards));
    }
}
