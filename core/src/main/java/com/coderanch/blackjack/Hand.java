/*
 * Copyright (C) 2018 Coderanch.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.coderanch.blackjack;

import java.util.*;

/**
 * A hand in a game of Blackjack.
 */
public class Hand {

    private static final int HIGHEST_SCORE = 21;
    private static final int MAX_SCORE_COUNT = 2^4;
    private static final int HAND_SPLIT_CARD_MAX = 2;

    private List<Card> cards = new ArrayList<>();
    private boolean isStopped;
    private Set<Integer> possibleScores;
    private int bestScore;

    /**
     * Adds a card to the hand.
     *
     * @param card the card being added to the hand.
     */
    public void addCard(Card card) {
        if(isStopped){
            throw new IllegalArgumentException("Hand is already stopped");
        }
        cards.add(card);
        refreshScore();
    }

    private void refreshScore() {
        this.possibleScores = calculateScores();
        this.bestScore = calculateBestScore();
    }

    private Set<Integer> calculateScores() {
        var startingScore = cards.stream()
                .filter(c -> c.values().length == 1)
                .flatMapToInt(c -> Arrays.stream(c.values()))
                .sum();
        var scores = new ArrayDeque<Integer>(MAX_SCORE_COUNT);
        scores.add(startingScore);

        cards.stream()
                .filter(c -> c.values().length > 1)
                .map(Card::values)
                .forEach((int[] nums) ->{
                    var initialSize = scores.size();
                    for (int i = 0; i < initialSize; i++) {
                        var currentNum = scores.pop();
                        for (int j = 0; j < nums.length; j++) {
                            scores.addLast(currentNum + nums[j]);
                        }
                    }
                });
        return new LinkedHashSet<>(scores);
    }

    private int calculateBestScore() {
        return this.possibleScores.stream()
                .sorted((o1, o2) -> Integer.compare(o2, o1))
                .filter(i -> i <= HIGHEST_SCORE)
                .findFirst()
                .orElse(0);
    }

    /**
     * Gets the current best score.
     *
     * @return the current best score.
     */
    public int bestScore() {
        return this.bestScore;
    }

    /**
     * Stops the hand.  Cannot receive more cards.
     */
    public void stop() {
        this.isStopped = true;
    }

    /**
     * Gets whether whether the hand can be split or not.
     *
     * @return whether the hand can be split or not.
     */
    public boolean canSplit() {
        if(cards.size() != HAND_SPLIT_CARD_MAX) {
            return false;
        }
        return cards.get(0).rank() == cards.get(1).rank();
    }

    /**
     * Split the hand.
     *
     * @return the hand with the second card.
     */
    public Hand split() {
        if(!this.canSplit()) {
            throw new IllegalHandSplitException("This hand cannot be split.");
        }
        var newHand = new Hand();
        newHand.addCard(this.cards.remove(1));
        this.refreshScore();
        return newHand;
    }

    /**
     * When a hand that cannot split tries to split.
     */
    public static class IllegalHandSplitException extends RuntimeException {

        public IllegalHandSplitException(String message) {
            super(message);
        }
    }
}
