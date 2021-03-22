/*
 * Copyright (C) 2018 Coderanch.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.coderanch.blackjack;

import com.coderanch.util.cli.InputUtility;

import java.io.IOException;
import java.io.PrintStream;
import java.security.SecureRandom;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Predicate;

import static com.coderanch.util.require.Require.requireThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Mini game of Blackjack.
 */
public final class MiniGame {

    private enum CHOICE {
        HIT, PASS
    }

    /**
     * The player's current hand.
     */
    private Hand hand;

    /**
     * The predicate used for validating user input.
     */
    private static final Predicate<? super String> PLAY_PREDICATE = InputUtility.oneOfThese("hit", "pass");

    /**
     * The deck of cards used in the game.
     */
    private final Deque<Card> deck = new ArrayDeque<>(Cards.getShuffledStandardDeck(new SecureRandom()));

    /**
     * The input utility used for getting answers.
     */
    private final InputUtility inputUtility;

    /**
     * The print stream used for sending text to the user.
     */
    private final PrintStream printStream;

    /**
     * Represents whether the user has passed or not.
     */
    private boolean isPassed;

    private MiniGame(InputUtility inputUtility, PrintStream printStream) {
        requireThat("inputUtility", inputUtility, is(notNullValue()));
        requireThat("printStream", printStream, is(notNullValue()));
        this.inputUtility = inputUtility;
        this.printStream = printStream;
    }

    /**
     * Run the mini game.
     * Will start a game of Blackjack.
     * Will ask the user to hit or pass until he wins, loses, or passes.
     *
     * @throws IOException if an I/O exception occurred while prompting the user for an action.
     */
    private void run() throws IOException {
        hand = new Hand(deck.removeLast(), deck.removeLast());
        displayStatus();

        while (!isGameOver()) {
            var choice = getUserChoice();
            switch (choice) {
                case HIT:
                    dealCard();
                    displayStatus();
                    break;

                case PASS:
                default:
                    pass();
                    break;
            }
        }
    }

    private CHOICE getUserChoice() throws IOException {
        var result = inputUtility.nextString("hit or pass?", PLAY_PREDICATE);
        return Enum.valueOf(CHOICE.class, result.trim().toUpperCase());
    }

    private void pass() {
        isPassed = true;
    }

    private void displayStatus() {
        printStream.println("Your cards are: ");
        hand.getCards().forEach(printStream::println);
        printStream.println(String.format("Your score is: %d", hand.bestScore()));
    }

    private boolean isGameOver() {
        if (hand.bestScore() == Hand.MAX_LEGAL_SCORE) {
            printStream.println("You win. Game over.");
            return true;
        }
        if (hand.bestScore() == 0) {
            printStream.println("You lose. Game over.");
            return true;
        }
        if (isPassed) {
            printStream.println("You passed. Game over.");
            return true;
        }

        return false;
    }

    private void dealCard() {
        printStream.println("You hit.");
        var newCard = deck.removeLast();
        hand = hand.withAdditionalCard(newCard);
        printStream.println();
        printStream.println(String.format("Your card was: %s", newCard));
    }


    /**
     * Play basic game of Blackjack through the console.
     *
     * @param args main arguments
     */
    public static void main(String[] args) {
        try (var inputUtility = new InputUtility()) {
            var miniGame = new MiniGame(inputUtility, System.out);
            miniGame.run();
        }
        catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
    }
}
