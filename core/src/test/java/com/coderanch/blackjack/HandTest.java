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

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
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
                            0
                    )
            );

    /**
     * Cards to test.
     */
    @DataPoints("objects")
    public static final Set<Card> CARDS = Cards.getStandardDeck();


    /**
     * Tests that {@link Hand#withAdditionalCard(Card)} maintains the correct score.
     *
     * @param handTest
     */
    @Theory
    @SuppressWarnings("checkstyle:methodname")
    public void addCard_maintainsCorrectScore(HandTestArgument handTest) {
        var hand = new Hand(handTest.getCards().get(0), handTest.getCards().get(1));
        for (int i = 2; i < handTest.getCards().size(); i++) {
            hand = hand.withAdditionalCard(handTest.getCards().get(i));
        }
        assertThat("The best score must match the target", hand.bestScore(), is(handTest.getTargetScore()));
    }

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
    }

    /**
     * Tests that passing {@code null} for {@code card} when constructing a new hand causes an exception to be thrown.
     *
     * @param card the card to construct the hand with.
     */
    @Theory(nullsAccepted = false)
    @SuppressWarnings("checkstyle:methodname")
    public void newHand_withNullCard1_throwsException(Card card) {
        assertThrows("Hand must throw exception", NullPointerException.class, () -> {
            new Hand(null, card);
        });
    }

    /**
     * Tests that passing {@code null} for {@code card} when constructing a new hand causes an exception to be thrown.
     *
     * @param card the card to construct the hand with.
     */
    @Theory(nullsAccepted = false)
    @SuppressWarnings("checkstyle:methodname")
    public void newHand_withNullCard2_throwsException(Card card) {
        assertThrows("Hand must throw exception", NullPointerException.class, () -> {
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


}