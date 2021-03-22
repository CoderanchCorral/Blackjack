/*
 * Copyright (C) 2018 Coderanch.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.coderanch.blackjack;

import java.util.List;

/**
 * A game of Blackjack.
 */
public final class Game {


    /**
     * Possible Blackjack game choices.
     */
    public enum CHOICE {
        HIT, PASS
    }

    /**
     * Represents whether the game has finished or not.
     */
    private boolean isFinished;

    /**
     * Players in the game.
     */
    private final List<Player> players;

    /**
     * Constructs a new game of Blackjack.
     *
     * @param players players in the game.
     */
    public Game(List<Player> players) {
        throw new UnsupportedOperationException();
    }

    /**
     * Run the game.
     * Deal cards to the players
     */
    public void run() {
        throw new UnsupportedOperationException();
    }

    /**
     * Start the next turn in the game.
     * Ask each player their next move.
     */
    public void nextTurn() {
        throw new UnsupportedOperationException();
    }

    private CHOICE getChoice(Player player) {
        throw new UnsupportedOperationException();
    }

    /**
     * Getter for players.
     *
     * @return players
     */
    public List<Player> players() {
        throw new UnsupportedOperationException();
    }

    /**
     * Getter for isFinished.
     *
     * @return if the game is finished.
     */
    public boolean isFinished() {
        return isFinished;
    }

}
