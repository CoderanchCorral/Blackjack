/*
 * Copyright (C) 2018 Coderanch.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.coderanch.blackjack;

/**
 * Represents a player in the game of Blackjack.
 * Has a hand and can give choices for the game.
 */
public interface Player {

    /**
     * Get the hand of the player.
     *
     * @return the hand of the player.
     */
    Hand hand();

    /**
     * Set the hand of the player.
     *
     * @param hand the new hand of the player.
     */
    void hand(Hand hand);

    /**
     * Gets whether the player has passed or gone bust.
     *
     * @return whether the player can play or not.
     */
    boolean isFixed();

    /**
     * Fix the player.
     * The player cannot hit or pass anymore.
     *
     * @param isFixed whether the player is fixed or not.
     */
    void isFixed(boolean isFixed);

    /**
     * Asks the player for their response and returns it.
     *
     * @return the players choice.
     */
    Game.CHOICE askChoice();
}
