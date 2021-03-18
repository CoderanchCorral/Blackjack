/*
 * Copyright (C) 2018 Coderanch.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.coderanch.blackjack;

import java.util.*;

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
     * Max amount of aces
     */
    private static final int MAX_SCORE_COUNT = 4;


    private final List<Card> cards;
    private List<Integer> possibleScores;
    private int bestScore;

    /**
     * Creates a new hand.
     *
     * @param card  first card dealt.
     * @param card2 second card dealt
     * @throws NullPointerException if either {@code card} or {@code card2} is {@code null}.
     */
    public Hand(Card card, Card card2) {
        this(List.of(card, card2));
    }

    private Hand(List<Card> cards) {
        this.cards = cards;
        this.possibleScores = calculateScores();
        this.bestScore = calculateBestScore();
    }

    /**
     * Gets a new hand with the extra card.
     *
     * @param card the card being added to the hand.
     * @return a new hand with the extra card.
     * @throws IllegalArgumentException if {@code card} is {@code null}.
     */
    public Hand withAdditionalCard(Card card) {
        requireThat("card", card, is(notNullValue()));

        var newCards = new ArrayList<Card>(this.cards);
        newCards.add(card);
        return new Hand(newCards);
    }

    private List<Integer> calculateScores() {
        var startingScore = cards.stream()
                .filter(c -> c.rank() != Card.Rank.ACE)
                .mapToInt(Card::points)
                .sum();

        var scores = new ArrayList<Integer>(MAX_SCORE_COUNT);
        scores.add(startingScore);

        cards.stream()
                .filter(c -> c.rank() == Card.Rank.ACE)
                .forEach((Card c) -> {
                    var initialSize = scores.size();
                    for (int i = 0; i < initialSize; i++) {
                        var score = scores.get(i);
                        scores.set(i, score + 1);
                        if (i + 1 == initialSize) {
                            scores.add(score + c.points());
                        }
                    }
                });
        return scores;
    }

    private int calculateBestScore() {
        return this.possibleScores.stream()
                .sorted((o1, o2) -> Integer.compare(o2, o1))
                .filter(i -> i <= MAX_LEGAL_SCORE)
                .findFirst()
                .orElse(0);
    }

    /**
     * Gets the best score.
     *
     * @return the best score.
     */
    public int bestScore() {
        return this.bestScore;
    }
}
