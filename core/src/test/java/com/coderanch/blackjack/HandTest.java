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
     * Tests that {@link Hand#addCard(Card)} ()} maintains the correct score
     */
    @Theory
    public void addCard_maintainsCorrectScore() {
        var hand = new Hand();
        hand.addCard(new Card(Rank.ACE, Suit.CLUBS));
        assertThat(hand.bestScore(), is(equalTo(11)));

        hand.addCard(new Card(Rank.ACE, Suit.CLUBS));
        assertThat(hand.bestScore(), is(equalTo(12)));

        hand.addCard(new Card(Rank.KING, Suit.CLUBS));
        assertThat(hand.bestScore(), is(equalTo(12)));

        hand.addCard(new Card(Rank.EIGHT, Suit.CLUBS));
        assertThat(hand.bestScore(), is(equalTo(20)));

        hand.addCard(new Card(Rank.ACE, Suit.CLUBS));
        assertThat(hand.bestScore(), is(equalTo(21)));
    }

}