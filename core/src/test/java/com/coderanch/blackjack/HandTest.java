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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


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
     * Tests that {@link Hand#withAdditionalCard(Card)} maintains the correct score
     */
    @Theory
    public void addCard_maintainsCorrectScore(HandTestArgument argument) {
        var hand = new Hand(argument.Cards.get(0), argument.Cards.get(1));
        for (int i = 2; i < argument.Cards.size(); i++) {
            hand = hand.withAdditionalCard(argument.Cards.get(i));
        }
        assertThat("The best score must match the target", hand.bestScore(), is(argument.targetScore));
    }

    private static final class HandTestArgument {
        public final List<Card> Cards;
        public final int targetScore;

        public HandTestArgument(List<Card> cards, int targetScore) {
            Cards = Collections.unmodifiableList(cards);
            this.targetScore = targetScore;
        }
    }
}