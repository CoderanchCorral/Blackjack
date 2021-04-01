/*
 * Copyright (C) 2018 Coderanch.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.coderanch.blackjack;

import java.util.ArrayList;
import java.util.Collections;
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
     * @return the highest score that can be made by using a large point value for aces
     *         without the hand going bust, or the lowest score if the hand is bust.
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

        return calculateBestScore(minimumScore, numberOfFreeAces);
    }

    private static int calculateBestScore(int minimumScore, int numberOfFreeAces) {
        if (numberOfFreeAces == 0) {
            return minimumScore;
        }
        var bestScoreWithBigAce = calculateBestScore(minimumScore + ACE.points(), numberOfFreeAces - 1);

        return bestScoreWithBigAce > MAX_LEGAL_SCORE
            ? calculateBestScore(minimumScore + 1, numberOfFreeAces - 1)
            : bestScoreWithBigAce;
    }

    /**
     * Gets whether the hand is bust or not.
     * A bust hand has a higher score than the legal maximum.
     *
     * @return {@code true} if {@link #bestScore()} is greater than the maximum legal score;
     *         {@code false} otherwise.
     */
    boolean isBust() {
        return bestScore() > MAX_LEGAL_SCORE;
    }

    /**
     * Gets the cards of the hand.
     *
     * @return an {@code unmodifiableList} of cards.
     */
    List<Card> cards() {
        return Collections.unmodifiableList(cards);
    }
}
