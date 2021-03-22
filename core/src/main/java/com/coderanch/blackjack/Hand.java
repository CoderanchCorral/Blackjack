/*
 * Copyright (C) 2018 Coderanch.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.coderanch.blackjack;

import com.coderanch.util.cli.InputUtility;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.*;
import java.util.function.Predicate;

import static com.coderanch.blackjack.Card.Rank.ACE;
import static com.coderanch.util.require.Require.requireThat;
import static org.hamcrest.Matchers.*;

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
    public Hand withAdditionalCard(Card card) {
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
    public int bestScore() {
        var minimumScore = cards.stream()
                .map(Card::rank)
                .filter(not(ACE::equals))
                .mapToInt(Card.Rank::getPoints)
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
        var bestScoreWithBigAce = calculateBestScore(minimumScore + ACE.getPoints(), numberOfFreeAces - 1);

        if (bestScoreWithBigAce > MAX_LEGAL_SCORE) {
            return calculateBestScore(minimumScore + 1, numberOfFreeAces - 1);
        }
        else {
            return bestScoreWithBigAce;
        }
    }

    /**
     * Issue #25.
     *
     * @param args main arguments
     */
    public static void main(String[] args) {
        try (var iu = new InputUtility()) {
            var miniGame = new MiniGame(iu);
            miniGame.start();
        }
        catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
    }

    /**
     * Mini game of Blackjack for issue #25.
     */
    private static final class MiniGame {

        /**
         * The player's current hand.
         */
        private Hand hand;

        /**
         * The predicate used for validating user input.
         */
        private Predicate<? super String> playPredicate = InputUtility.oneOfThese("hit", "pass");

        /**
         * The deck of cards used in the game.
         */
        private Deque<Card> deck = new ArrayDeque<>(Cards.getShuffledStandardDeck(new SecureRandom()));

        /**
         * The input utility used for getting answers.
         */
        private InputUtility inputUtility;

        private MiniGame(InputUtility inputUtility) {
            this.inputUtility = inputUtility;
        }

        /**
         * Start the mini game.
         *
         * @throws IOException from inputUtility.
         */
        private void start() throws IOException {
            hand = new Hand(deck.removeLast(), deck.removeLast());
            while (true) {
                displayStatus();
                if (isGameOver()) {
                    break;
                }
                var answer = inputUtility.nextString("hit or pass?", playPredicate);
                if ("pass".equalsIgnoreCase(answer.trim())) {
                    System.out.println("You passed.  Game over.");
                    break;
                }
                dealCard();
            }
        }

        private void displayStatus() {
            System.out.println("Your cards are: ");
            hand.cards.forEach(System.out::println);
            System.out.println(String.format("Your score is: %d", hand.bestScore()));
        }

        private boolean isGameOver() {
            if (hand.bestScore() == Hand.MAX_LEGAL_SCORE) {
                System.out.println("You win. Game over.");
                return true;
            }
            if (hand.bestScore() == 0) {
                System.out.println("You lose. Game over.");
                return true;
            }
            return false;
        }

        private void dealCard() {
            System.out.println("You hit.");
            var newCard = deck.removeLast();
            hand = hand.withAdditionalCard(newCard);
            System.out.println();
            System.out.println(String.format("Your card was: %s", newCard));
        }
    }
}
