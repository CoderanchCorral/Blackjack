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
   * smcdonald4812 - filled in enum, created constructors, added an int rankValue, 
   */
  enum Rank {ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE};

  /**
   * The suit of a {@link Card}.
   * smcdonald4812 - filled in enum
   */
  enum Suit {SPADES, HEARTS, CLUBS, DIAMONDS};
  
  // smcdonald4812 - value of the rank, secondary value for the ace, Rank, and Suit
  private int rankValue, secondaryValue;
  private Rank rank;
  private Suit suit;
  //smcdonald4812 - could add a String ID here as well with UUID if wanted.
  
  //smcdonald4812 - default constructor, non-null assignment.
  
  Public Card() {
    super();
    this.isAce = false;
    this.rankValue = 1;
    this.secondaryValue = 1;
    this.rank = Rank.ONE;
    this.suit = Suit.SPADES;
  }
  
  //smcdonald4812 - Rank constructor must call this() as one has been provided.  .ordinal() is zerobased enum indexing.
  
  Public Card(Rank rank) {
    this();
    this.rank = rank;
    this.rankValue += rank.ordinal();
    this.secondaryValue += rank.ordinal();
    if(rankValue > 10 && rankValue < 14) {
      this.rankValue = 10;
      this.secondaryValue = this.rankValue;
    }
    if(rankValue == 14) {
      this.rankValue = 11;
      this.secondaryValue = 1;
    }
  }
  
  /*
  * smcdonald4812 - Suit, Rank constructor.
  */
  
  Public Card(Rank rank, Suit suit) {
    this(Rank rank);
    this.suit = suit;
  }
  
  /**
   * Gets the rank of this card.
   * 
   * @return the rank of this card; never {@literal null}
   * smcdonald4812 - non-null in this(). There are no setters as Card shouldn't be changed once it is created.
   */
  public Rank getRank() {
    return this.rank;
  }
  
  /**
   * Gets the suit of this card.
   * 
   * @return the suit of this card; never {@literal null}
   * smcdonald4812 - non-null in this().
   */
  
  public Suit getSuit() {
    return this.suit;
  }
  
  //smcdonald4812 - allows callers to get the card's rankValue. Non-null in this().
  
  public int getValue() {
    return this.value;
  }
  
  //smcdonald4812 - allows callers to get ace's secondary value, and if mistakenly used on other cards it gets their value.
  
  public int getSecondaryValue() {
    return this.secondaryValue;
  }
}
