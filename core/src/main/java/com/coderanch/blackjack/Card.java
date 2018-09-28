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
  
  enum Rank { ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING };
  enum Suit { SPADES, HEARTS, CLUBS, DIAMONDS };
  
  private final Rank rank;
  private final Suit suit;
  
  /**
  * Constructor.
  *
  * @param rank (required) Rank value. Must have value of enum Rank. 
  * @param suit (required) Suit value. Must have value of enum Suit.
  */
  
  public Card(Rank rank, Suit suit) {
    this.rank = getConstRank(rank);
    this.suit = getConstSuit(suit);
  }
  
  /*
  * Oracle states that private methods should not have a JavaDoc, 
  * also that assertions should not be used for runtime exceptions.
  * I have implemented these getters as I;ve been told logic should not be placed
  * in the constructor itself. I'm not sure if it is okay to do it this way? 
  * Any feedback would be geat. Also, suit not being of Suit is an error (cannot find symbol).
  * How is this supposed to be checked for without surrounding constructor in try catch?
   */
  
  private Suit getConstSuit(Suit suit) {
    if(suit == null) {
      return Suit.SPADES;
    }
    return suit;
  }
  
  private Rank getConstRank(Rank rank) {
    if(rank == null) {
      return Rank.TWO;
    }
    return rank;
  }
  
  /**
   * Gets the rank of this card.
   * 
   * @return the rank of this card; never {@literal null}
   * 
   */
   
   public Rank getRank() {
    return this.rank;
   }
   
  /**
   * Gets the suit of this card.
   * 
   * @return the suit of this card; never {@literal null}
   * 
   */
  
   public Suit getSuit() {
    return this.suit;
  }
}
