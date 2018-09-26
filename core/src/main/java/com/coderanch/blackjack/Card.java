/*
 * Copyright (C) 2018 Coderanch.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.coderanch.blackjack;

/**
 * A playing card used in a game of Blackjack.
 */
final class Card {
  
  /**
   * The rank of a {@link Card}.
   */
  enum Rank {}

  /**
   * The suit of a {@link Card}.
   */
  enum Suit {}
  
  /**
   * Gets the rank of this card.
   * 
   * @return the rank of this card; never {@literal null}
   */
  Rank rank() {
    return null;
  }
  
  /**
   * Gets the suit of this card.
   * 
   * @return the suit of this card; never {@literal null}
   */
  Suit suit() {
    return null;
  }
}