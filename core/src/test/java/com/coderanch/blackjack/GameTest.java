/*
 * Copyright (C) 2018 Coderanch.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.coderanch.blackjack;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public final class GameTest {

    private static final int LOWEST_POSSIBLE_START_SCORE = 3;

    /**
     * Tests whether the players get a hand of cards.
     */
    @Test
    public void run() {
        var game = new Game(List.of(new HumanPlayer()));
        game.run();
        game.players().forEach(p -> {
            assertThat("The player must have a hand.", p.hand(), is(notNullValue()));
            assertThat(
                    "The player must have cards in the hand.",
                    p.hand().bestScore(),
                    is(greaterThan(LOWEST_POSSIBLE_START_SCORE)));
        });
    }

    /**
     * Tests whether the game gets new choices from the players.
     */
    @Test
    public void nextTurn() {
        var testPlayer = new TestPlayer();
        var game = new Game(List.of(testPlayer));
        game.run();
        game.nextTurn();
        assertThat(
                "The player must have chosen more than once.",
                ((TestPlayer) game.players().get(0)).choiceCount,
                is(greaterThan(0))
        );
    }

    /**
     * Tests whether the game finishes.
     */
    @Test
    public void isFinished() {
        var testPlayer = new TestPlayer();
        var game = new Game(List.of(testPlayer));
        assertThat("The game must not be finished.", game.isFinished(), is(equalTo(false)));
        game.run();
        while (!game.players().stream().allMatch(Player::isFixed)) {
            game.nextTurn();
        }
        assertThat("The game must be finished.", game.isFinished(), is(equalTo(true)));
    }

    /**
     * Tests if the game will return the players in the game.
     */
    @Test
    public void players() {
        var game = new Game(List.of(new HumanPlayer()));
        assertThat("The player list must not be null", game.players(), is(notNullValue()));
        assertThat("The player list must not be empty", game.players().size(), is(greaterThan(0)));
    }

    private static final class TestPlayer implements Player {

        /**
         * How many times the player chose.
         */
        private int choiceCount;

        @Override
        public Hand hand() {
            return null;
        }

        @Override
        public boolean isFixed() {
            return false;
        }

        @Override
        public Game.CHOICE askChoice() {
            choiceCount++;
            return Game.CHOICE.HIT;
        }
    }
}
