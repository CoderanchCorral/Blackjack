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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import static org.junit.Assert.assertThrows;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;


/**
 * Tests the {@link Hand} class.
 */
@RunWith(Theories.class)
public final class HandTest {

    /**
     * Hands to test scoring.
     */
    @DataPoints
    public static final List<HandTestArgument> HANDS =
        List.of(
            new HandTestArgument(
                List.of(new Card(Rank.ACE, Suit.CLUBS),
                    new Card(Rank.ACE, Suit.CLUBS),
                    new Card(Rank.KING, Suit.CLUBS),
                    new Card(Rank.EIGHT, Suit.CLUBS),
                    new Card(Rank.ACE, Suit.CLUBS)),
                21
            ),

            new HandTestArgument(
                List.of(new Card(Rank.QUEEN, Suit.CLUBS),
                    new Card(Rank.EIGHT, Suit.CLUBS)),
                18
            ),

            new HandTestArgument(
                List.of(new Card(Rank.QUEEN, Suit.CLUBS),
                    new Card(Rank.ACE, Suit.CLUBS)),
                21
            ),

            new HandTestArgument(
                List.of(new Card(Rank.KING, Suit.CLUBS),
                    new Card(Rank.KING, Suit.CLUBS),
                    new Card(Rank.KING, Suit.CLUBS),
                    new Card(Rank.KING, Suit.CLUBS),
                    new Card(Rank.KING, Suit.CLUBS)),
                50
            ),

            new HandTestArgument(
                List.of(new Card(Rank.ACE, Suit.CLUBS),
                    new Card(Rank.ACE, Suit.CLUBS),
                    new Card(Rank.ACE, Suit.CLUBS),
                    new Card(Rank.KING, Suit.CLUBS),
                    new Card(Rank.KING, Suit.CLUBS)),
                23
            ),

            new HandTestArgument(
                List.of(new Card(Rank.ACE, Suit.CLUBS),
                    new Card(Rank.ACE, Suit.CLUBS),
                    new Card(Rank.NINE, Suit.CLUBS)),
                21
            )
        );

    /**
     * Cards to test.
     */
    @DataPoints
    public static final Set<Card> CARDS = Cards.getStandardDeck();


    /**
     * Tests that {@link Hand#withAdditionalCard(Card)} maintains the correct score.
     *
     * @param handTest contains the test hand, and the target score.
     */
    @Theory
    @SuppressWarnings("checkstyle:methodname")
    public void addCard_maintainsCorrectScore(HandTestArgument handTest) {
        assertThat(
            "The best score must match the target",
            handTest.getHand().bestScore(),
            is(handTest.getTargetScore())
        );
    }

    /**
     * Tests that passing {@code null} for the first {@code card}
     * when constructing a new hand causes an exception to be thrown.
     *
     * @param card the card to construct the hand with.
     */
    @Theory(nullsAccepted = false)
    @SuppressWarnings("checkstyle:methodname")
    public void newHand_withNullFirstCard_throwsException(Card card) {
        assertThrows("Hand must throw exception", IllegalArgumentException.class, () -> {
            new Hand(null, card);
        });
    }

    /**
     * Tests that passing {@code null} for the second {@code card}
     * when constructing a new hand causes an exception to be thrown.
     *
     * @param card the card to construct the hand with.
     */
    @Theory(nullsAccepted = false)
    @SuppressWarnings("checkstyle:methodname")
    public void newHand_withNullSecondCard_throwsException(Card card) {
        assertThrows("Hand must throw exception", IllegalArgumentException.class, () -> {
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
        assertThrows("Hand must throw exception", IllegalArgumentException.class, () -> {
            hand.withAdditionalCard(null);
        });
    }


    /**
     * Tests that hands that {@link Hand#isBust()} returns the correct answer.
     *
     * @param handTest contains the test hand, and the target score.
     */
    @Theory
    public void isBust(HandTestArgument handTest) {
        assertThat(
            "The hands over the legal limit must be bust.",
            handTest.getHand().isBust(),
            is(equalTo(handTest.getHand().bestScore() > Hand.MAX_LEGAL_SCORE))
        );
    }

    /**
     * Helper class for testing hand scores.
     */
    private static final class HandTestArgument {

        /**
         * Cards included in the test.
         */
        private final List<Card> cards;

        /**
         * The target score the hand should have.
         */
        private final int targetScore;

        HandTestArgument(List<Card> cards, int targetScore) {
            this.cards = Collections.unmodifiableList(cards);
            this.targetScore = targetScore;
        }

        List<Card> getCards() {
            return cards;
        }

        int getTargetScore() {
            return targetScore;
        }

        Hand getHand() {
            var hand = new Hand(this.cards.get(0), this.cards.get(1));
            for (int i = 2; i < this.cards.size(); i++) {
                hand = hand.withAdditionalCard(this.cards.get(i));
            }
            return hand;
        }
    }
}
