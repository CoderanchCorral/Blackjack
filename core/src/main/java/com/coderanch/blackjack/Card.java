/*
 * Copyright (C) 2018 Coderanch.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.coderanch.blackjack;

import static java.util.Objects.hash;

/**
 * A playing card used in a game of Blackjack.
 */
final class Card {
  
  /**
   * The rank of a {@link Card}.
   */
  enum Rank {
    ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
  }

  /**
   * The suit of a {@link Card}.
   */
  enum Suit {
    CLUBS, DIAMONDS, HEARTS, SPADES
  }
  
  private final Rank rank;
  
  private final Suit suit;

  /**
   * Constructs a new card with the specified rank and suit.
   *
   * @param rank the rank of the new card.
   * @param suit the suit of the new card.
   */
  private Card(Rank rank, Suit suit) {
    this.rank = rank;
    this.suit = suit;
  }

  static Card of(Rank rank, Suit suit) {
    if (rank == null || suit == null)
      throw new IllegalArgumentException();

    return new Card(rank, suit);
  }
  
  /**
   * Gets the rank of this card.
   * 
   * @return the rank of this card; never {@literal null}
   */
  Rank rank() {
    return rank;
  }
  
  /**
   * Gets the suit of this card.
   * 
   * @return the suit of this card; never {@literal null}
   */
  Suit suit() {
    return suit;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Card))
      return false;

    Card other = (Card) object;

    return this.rank == other.rank
        && this.suit == other.suit;
  }

  @Override
  public int hashCode() {
    return hash(rank, suit);
  }

  @Override
  public String toString() {
    return String.format("%s of %s", rank, suit);
  }
}