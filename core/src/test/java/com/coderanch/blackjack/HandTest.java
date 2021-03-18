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

import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Tests the {@link Hand} class.
 */
@RunWith(Theories.class)
public final class HandTest {

    /**
     * Tests that {@link Hand#withAdditionalCard(Card)}  maintains the correct score
     */
    @Theory
    public void addCard_maintainsCorrectScore() {
        var hand = new Hand(new Card(Rank.ACE, Suit.CLUBS), new Card(Rank.ACE, Suit.CLUBS));
        assertThat(hand.bestScore(), is(equalTo(12)));

        hand = hand.withAdditionalCard(new Card(Rank.KING, Suit.CLUBS));
        assertThat(hand.bestScore(), is(equalTo(12)));

        hand = hand.withAdditionalCard(new Card(Rank.EIGHT, Suit.CLUBS));
        assertThat(hand.bestScore(), is(equalTo(20)));

        hand = hand.withAdditionalCard(new Card(Rank.ACE, Suit.CLUBS));
        assertThat(hand.bestScore(), is(equalTo(21)));
    }
}