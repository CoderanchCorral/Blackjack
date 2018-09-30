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
  * The rank of {@link} card
  **/
  enum Rank { ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING };
  
  /**
  * The suit of {@link} card.
  **/
  enum Suit { SPADES, HEARTS, CLUBS, DIAMONDS };
  
  private final Rank rank;
  private final Suit suit;
  
  /**
  * Constructor.
  * @param rank (required) Rank value. Must have value of enum Rank. 
  * @param suit (required) Suit value. Must have value of enum Suit.
  */
  
  public Card(Rank rank, Suit suit) {
    this.rank = rank;
    this.suit = suit;
  }
  
  /**
   * Gets the rank of this card.
   * @return the rank of this card; never {@literal null}
   */
   
   public Rank getRank() {
    return this.rank;
   }
   
  /**
   * Gets the suit of this card.
   * @return the suit of this card; never {@literal null}
   */
  
   public Suit getSuit() {
    return this.suit;
  }
    
    @Override
    public String toString() {
        return rank + " of " + suit;
        
    }
}
