/*
 * Copyright (C) 2018 Coderanch.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.coderanch.blackjack;

import java.util.Scanner;

final class UserInterface {

	private void mainMenu() {
		Scanner sc = new Scanner(System.in);
		int choice;
		String mainMenuStr = "1. Play Game \n10. Exit\nEnter choice";
		do {
			System.out.println(mainMenuStr);
			choice = InputUtilities.isWholeNumber();
			switch (choice) {
			case 1:
				// Code to create Or invoke a method to create an object of Player.
				break;
			case 10:
				break;
			default:
				System.out.println("Invalid choice");
			}

		} while (choice != 10);
		System.out.println("Thank you! Visit again...");
	}

	private void subMenu() {
		int choice;
		Scanner sc = new Scanner(System.in);

		String subMenuStr = "1. Add Balance\n2. Set/Update Bet\n3. Deal\n4. Display Info\n" + "10. Exit\nEnter choice";
		do {
			System.out.println(subMenuStr);
			choice = InputUtilities.isWholeNumber();
			switch (choice) {
			case 1:
				System.out.println("Enter balance");
				int balance = InputUtilities.isWholeNumber();
				if (balance == -1) {
					System.out.println("Invalid balance, only whole number is allowed");
					break;
				}
				// Rest code here
				break;
			case 2:

				break;
			case 3:

				break;
			case 4:
				break;
			case 10:
				System.out.println("Thank you! Visit again...");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid choice");
			}
		} while (choice != 10);
	}

}
