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
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


public class GameTest {

    /**
     * Tests whether the players get a hand of cards.
     */
    @Test
    public void run() {
        var game = new Game(List.of(new HumanPlayer()));
        game.run();
        game.players().forEach(p -> {
            assertThat("Has players with hands", p.hand(), is(notNullValue()));
        });
    }

    /**
     * Tests whether the game get's new choices from the players.
     */
    @Test
    public void nextTurn() {
        var game = new Game(List.of(new HumanPlayer()));
        game.run();
        game.nextTurn();

    }

    /**
     * Tests if the game will return the players in the game.
     */
    @Test
    public void players() {
        var game = new Game(List.of(new HumanPlayer()));
        assertThat("The player list isn't null", game.players(), is(notNullValue()));
    }
}
