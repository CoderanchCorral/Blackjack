/*
 * Copyright (C) 2018 Coderanch.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.coderanch.blackjack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A hand consists of two Or more Cards. Dealer can have only one hand and
 * regular player can have one Or two Hands.
 * 
 *
 */
final class Hand {

	private int value;
	private int no;

	private List<Card> cardsOfHand;

	/**
	 * Constructor
	 * 
	 * @param no
	 *            It's number of Hand which could be be 1 Or 2.
	 */
	Hand(int no) {
		if (no != 1 && no != 2) {
			throw new IllegalArgumentException("Invalid hand number");
		}

		this.no = no;
		cardsOfHand = new ArrayList<>();
	}

	/**
	 * 
	 * @return Number of Hand.
	 */
	public int getNo() {
		return no;
	}

	/**
	 * Sets the value of a Hand.
	 * 
	 * @param value
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * Value of a Hand is the summation of values of all Cards a Hand has.
	 * 
	 * @return Return the total value of a Hand.
	 */
	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		Iterator<Card> itr = cardsOfHand.iterator();
		String cardsInfo = "";
		while (itr.hasNext()) {
			Card temCard = itr.next();
			cardsInfo = temCard.toString() + " \n";
		}
		return "Hand No: " + this.no + " Score: " + this.value + " " + cardsInfo;
	}

}
