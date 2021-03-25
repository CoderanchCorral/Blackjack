/*
 * Copyright (C) 2018 Coderanch.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.coderanch.blackjack;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.coderanch.blackjack.Card.Rank;
import com.coderanch.blackjack.Card.Suit;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import static org.junit.Assert.assertThrows;


/**
 * Tests the {@link Hand} class.
 */
@RunWith(Theories.class)
public final class HandTest {

    /**
     * Hands to test scoring.
     */
    @DataPoints
    public static final List<BestScoreTestArgument> HAND_TEST_ARGUMENTS =
        List.of(
            new BestScoreTestArgument(
                List.of(new Card(Rank.ACE, Suit.CLUBS),
                    new Card(Rank.ACE, Suit.CLUBS),
                    new Card(Rank.KING, Suit.CLUBS),
                    new Card(Rank.EIGHT, Suit.CLUBS),
                    new Card(Rank.ACE, Suit.CLUBS)),
                21
            ),

            new BestScoreTestArgument(
                List.of(new Card(Rank.QUEEN, Suit.CLUBS),
                    new Card(Rank.EIGHT, Suit.CLUBS)),
                18
            ),

            new BestScoreTestArgument(
                List.of(new Card(Rank.QUEEN, Suit.CLUBS),
                    new Card(Rank.ACE, Suit.CLUBS)),
                21
            ),

            new BestScoreTestArgument(
                List.of(new Card(Rank.KING, Suit.CLUBS),
                    new Card(Rank.KING, Suit.CLUBS),
                    new Card(Rank.KING, Suit.CLUBS),
                    new Card(Rank.KING, Suit.CLUBS),
                    new Card(Rank.KING, Suit.CLUBS)),
                50
            ),

            new BestScoreTestArgument(
                List.of(new Card(Rank.ACE, Suit.CLUBS),
                    new Card(Rank.ACE, Suit.CLUBS),
                    new Card(Rank.ACE, Suit.CLUBS),
                    new Card(Rank.KING, Suit.CLUBS),
                    new Card(Rank.KING, Suit.CLUBS)),
                23
            ),

            new BestScoreTestArgument(
                List.of(new Card(Rank.ACE, Suit.CLUBS),
                    new Card(Rank.ACE, Suit.CLUBS),
                    new Card(Rank.NINE, Suit.CLUBS)),
                21
            )
        );

    /**
     * Busted hands to check.
     */
    @DataPoints("Bust Hands")
    public static final List<Hand> BUST_HANDS = List.of(
        createHand(List.of(
            new Card(Rank.ACE, Suit.CLUBS),
            new Card(Rank.ACE, Suit.CLUBS),
            new Card(Rank.KING, Suit.CLUBS),
            new Card(Rank.KING, Suit.CLUBS)
        )),
        createHand(List.of(
            new Card(Rank.QUEEN, Suit.CLUBS),
            new Card(Rank.EIGHT, Suit.CLUBS),
            new Card(Rank.FOUR, Suit.CLUBS)
        )),
        createHand(List.of(
            new Card(Rank.FIVE, Suit.CLUBS),
            new Card(Rank.FIVE, Suit.CLUBS),
            new Card(Rank.FIVE, Suit.CLUBS),
            new Card(Rank.FIVE, Suit.CLUBS),
            new Card(Rank.THREE, Suit.CLUBS)
        )),
        createHand(List.of(
            new Card(Rank.JACK, Suit.CLUBS),
            new Card(Rank.QUEEN, Suit.CLUBS),
            new Card(Rank.KING, Suit.CLUBS)
        ))
    );

    /**
     * Non-busted hands to check.
     */
    @DataPoints("Non Bust Hands")
    public static final List<Hand> NON_BUST_HANDS = List.of(
        createHand(List.of(
            new Card(Rank.ACE, Suit.CLUBS),
            new Card(Rank.KING, Suit.CLUBS),
            new Card(Rank.KING, Suit.CLUBS)
        )),
        createHand(List.of(
            new Card(Rank.QUEEN, Suit.CLUBS),
            new Card(Rank.EIGHT, Suit.CLUBS),
            new Card(Rank.THREE, Suit.CLUBS)
        )),
        createHand(List.of(
            new Card(Rank.FIVE, Suit.CLUBS),
            new Card(Rank.FIVE, Suit.CLUBS)
        )),
        createHand(List.of(
            new Card(Rank.JACK, Suit.CLUBS),
            new Card(Rank.QUEEN, Suit.CLUBS)
        ))
    );

    /**
     * Hands to add to.
     */
    @DataPoints("Add card hands")
    public static final List<Hand> HANDS = List.of(
        createHand(List.of(
            new Card(Rank.KING, Suit.CLUBS),
            new Card(Rank.KING, Suit.CLUBS)
        )),
        createHand(List.of(
            new Card(Rank.QUEEN, Suit.CLUBS),
            new Card(Rank.EIGHT, Suit.CLUBS),
            new Card(Rank.THREE, Suit.CLUBS)
        )),
        createHand(List.of(
            new Card(Rank.FIVE, Suit.CLUBS),
            new Card(Rank.FIVE, Suit.CLUBS)
        )),
        createHand(List.of(
            new Card(Rank.JACK, Suit.CLUBS),
            new Card(Rank.QUEEN, Suit.CLUBS)
        ))
    );

    /**
     * Cards to test.
     */
    @DataPoints
    public static final Set<Card> CARDS = Cards.getStandardDeck();

    /**
     * Get a hand with the given cards.
     *
     * @param cards the cards in the hand.
     * @return the created hand.
     */
    private static Hand createHand(List<Card> cards) {
        var hand = new Hand(cards.get(0), cards.get(1));
        for (int i = 2; i < cards.size(); i++) {
            hand = hand.withAdditionalCard(cards.get(i));
        }
        return hand;
    }

    /**
     * Tests that {@link Hand#bestScore()} calculates the correct score.
     *
     * @param scoreTest contains the test hand, and the target score.
     */
    @Theory
    @SuppressWarnings("checkstyle:methodname")
    public void bestScore_hasCorrectScore(BestScoreTestArgument scoreTest) {
        var hand = createHand(scoreTest.cards);
        assertThat(
            "The best score must match the target",
            hand.bestScore(),
            is(scoreTest.getTargetScore())
        );
    }

    /**
     * Tests that {@link Hand#withAdditionalCard(Card)} maintains the correct score.
     *
     * @param hand the hand to test.
     * @param card the card to add to the hand.
     */
    @Theory
    @SuppressWarnings("checkstyle:methodname")
    public void withAdditionalCard_hasCorrectScore(@FromDataPoints("Add card hands") Hand hand, Card card) {
        if (card.rank() == Rank.ACE) {
            return;
        }
        var newHand = hand.withAdditionalCard(card);
        assertThat(
            "The difference must be correct",
            newHand.bestScore() - hand.bestScore(),
            is(equalTo(card.rank().points()))
        );
    }

    /**
     * Tests that passing {@code null} for the first card
     * when constructing a new hand causes an exception to be thrown.
     *
     * @param card the card to construct the hand with.
     */
    @Theory(nullsAccepted = false)
    @SuppressWarnings("checkstyle:methodname")
    public void newHand_withNullFirstCard_throwsException(Card card) {
        assertThrows("Hand must throw an exception.", IllegalArgumentException.class, () -> {
            new Hand(null, card);
        });
    }

    /**
     * Tests that passing {@code null} for the second card
     * when constructing a new hand causes an exception to be thrown.
     *
     * @param card the card to construct the hand with.
     */
    @Theory(nullsAccepted = false)
    @SuppressWarnings("checkstyle:methodname")
    public void newHand_withNullSecondCard_throwsException(Card card) {
        assertThrows("Hand must throw an exception.", IllegalArgumentException.class, () -> {
            new Hand(card, null);
        });
    }

    /**
     * Tests that passing {@code null} for {@code card} when adding a new card causes an exception to be thrown.
     */
    @Theory
    @SuppressWarnings("checkstyle:methodname")
    public void addCard_withNullCard_throwsException() {
        var hand = new Hand(new Card(Rank.ACE, Suit.CLUBS), new Card(Rank.ACE, Suit.CLUBS));
        assertThrows("Hand must throw an exception.", IllegalArgumentException.class, () -> {
            hand.withAdditionalCard(null);
        });
    }

    /**
     * Tests that hands that {@link Hand#isBust()} returns the correct answer.
     *
     * @param hand the hand to check if bust.
     */
    @Theory
    @SuppressWarnings("checkstyle:methodname")
    public void isBust_withBustHand_returnsTrue(@FromDataPoints("Bust Hands") Hand hand) {
        assertThat(
            "The hand must be bust.",
            hand.isBust(),
            is(true)
        );
    }

    /**
     * Tests that hands that {@link Hand#isBust()} returns the correct answer.
     *
     * @param hand the hand to check if not bust.
     */
    @Theory
    @SuppressWarnings("checkstyle:methodname")
    public void isBust_withNonBustHand_returnsFalse(@FromDataPoints("Non Bust Hands") Hand hand) {
        assertThat(
            "The hand must not be bust.",
            hand.isBust(),
            is(false)
        );
    }


    /**
     * Helper class for testing hand scores.
     */
    private static final class BestScoreTestArgument {

        /**
         * Cards included in the test.
         */
        private final List<Card> cards;

        /**
         * The target score the hand should have.
         */
        private final int targetScore;

        private BestScoreTestArgument(List<Card> cards, int targetScore) {
            this.cards = Collections.unmodifiableList(cards);
            this.targetScore = targetScore;
        }

        private List<Card> getCards() {
            return cards;
        }

        private int getTargetScore() {
            return targetScore;
        }

    }
}
