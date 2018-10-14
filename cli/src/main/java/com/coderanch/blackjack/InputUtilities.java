/*
 * Copyright (C) 2018 Coderanch.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.coderanch.blackjack;

import java.util.Scanner;

/**
 * Utility class to verify input values.
 */
final class InputUtilities {
	/**
	 * This method verifies If name is valid Or not. Valid name consists of
	 * characters and spaces having minimum length 3 and maximum 20.
	 * 
	 * @param name
	 *            Name to be validated. false If passed name is invalid name.
	 * @return Returns true If It's a valid name else return false.
	 */
	public static boolean isValidName(String name) {

		if (name.length() < 3 || name.length() > 20) {
			return false;
		}
		name = name.toUpperCase();
		for (int i = 0; i < name.length(); i++) {

			char ch = name.charAt(i);

			if ((!(ch >= 65 && ch <= 90)) && (ch != ' ')) {

				return false;
			}
		}
		return true;
	}

	/**
	 * This method verifies whether users input is whole number Or not.
	 * 
	 * @return Returns -1 If user input is not an int value.
	 */
	public static int isWholeNumber() {
		Scanner sc = new Scanner(System.in);
		return sc.hasNextInt() ? sc.nextInt() : -1;
	}

}
