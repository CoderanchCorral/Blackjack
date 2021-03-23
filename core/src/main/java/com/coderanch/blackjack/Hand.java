/*
 * Copyright (C) 2018 Coderanch.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.coderanch.blackjack;

import java.util.ArrayList;
import java.util.List;
import static java.util.function.Predicate.not;

import static com.coderanch.blackjack.Card.Rank.ACE;
import static com.coderanch.util.require.Require.requireThat;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * A hand in a game of Blackjack.
 */
final class Hand {

    /**
     * Highest score in Blackjack.
     */
    private static final int MAX_LEGAL_SCORE = 21;

    /**
     * The cards in the hand.
     */
    private final List<Card> cards;

    /**
     * Creates a new hand.
     *
     * @param firstCard  first card dealt.
     * @param secondCard second card dealt
     * @throws IllegalArgumentException if either {@code firstCard} or {@code secondCard} is {@code null}.
     */
    Hand(Card firstCard, Card secondCard) {
        this(List.of(
            requireThat("firstCard", firstCard, is(notNullValue())),
            requireThat("secondCard", secondCard, is(notNullValue()))
        ));
    }

    /**
     * Constructs a new hand with the given cards.
     *
     * @param cards the cards in the hand.
     */
    private Hand(List<Card> cards) {
        this.cards = cards;
    }

    /**
     * Gets a new hand with the extra card.
     *
     * @param card the card being added to the hand.
     * @return a new hand with the extra card.
     * @throws IllegalArgumentException if {@code card} is {@code null}.
     */
    Hand withAdditionalCard(Card card) {
        requireThat("card", card, is(notNullValue()));

        var newCards = new ArrayList<Card>(this.cards);
        newCards.add(card);
        return new Hand(newCards);
    }

    /**
     * Gets the best score.
     *
     * @return the best score.
     */
    int bestScore() {
        var minimumScore = cards.stream()
            .map(Card::rank)
            .filter(not(ACE::equals))
            .mapToInt(Card.Rank::points)
            .sum();

        var numberOfFreeAces = (int) cards.stream()
            .map(Card::rank)
            .filter(ACE::equals)
            .count();

        var bestScore = calculateBestScore(minimumScore, numberOfFreeAces);
        if (bestScore > MAX_LEGAL_SCORE) {
            return 0;
        }
        else {
            return bestScore;
        }
    }

    private static int calculateBestScore(int minimumScore, int numberOfFreeAces) {
        if (numberOfFreeAces <= 0) {
            return minimumScore;
        }
        var bestScoreWithBigAce = calculateBestScore(minimumScore + ACE.points(), numberOfFreeAces - 1);

        if (bestScoreWithBigAce > MAX_LEGAL_SCORE) {
            return calculateBestScore(minimumScore + 1, numberOfFreeAces - 1);
        }
        else {
            return bestScoreWithBigAce;
        }
    }
}
