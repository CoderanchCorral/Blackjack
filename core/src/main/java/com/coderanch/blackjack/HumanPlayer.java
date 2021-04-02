/*
 * Copyright (C) 2018 Coderanch.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.coderanch.blackjack;

/**
 * A player that is controlled by human input.
 */
public final class HumanPlayer implements Player {
    @Override
    public Hand hand() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void hand(Hand hand) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isFixed() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void isFixed(boolean isFixed) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Game.CHOICE askChoice() {
        throw new UnsupportedOperationException();
    }
}
