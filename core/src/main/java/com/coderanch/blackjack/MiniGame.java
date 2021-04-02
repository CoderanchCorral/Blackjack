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
import java.util.Locale;
import java.util.function.Predicate;

import static com.coderanch.util.require.Require.requireThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Mini game of Blackjack.
 */
public final class MiniGame {

    /**
     * Highest score in Blackjack.
     */
    private static final int MAX_LEGAL_SCORE = 21;

    /**
     * The predicate used for validating user input.
     */
    private static final Predicate<? super String> PLAY_PREDICATE = InputUtility.oneOfTheseIgnoringCase("hit", "pass");

    /**
     * The possible choices the user can make.
     */
    private enum CHOICE {
        HIT, PASS
    }

    /**
     * The player's current hand.
     */
    private Hand hand;

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

    /**
     * Constructs new mini game of Blackjack.
     *
     * @param inputUtility used for user input.
     * @param printStream used for printing text output.
     */
    public MiniGame(InputUtility inputUtility, PrintStream printStream) {
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
    public void run() throws IOException {
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

    /**
     * Get the user's next choice.
     *
     * @return the user's choice.
     * @throws IOException if there's a problem with the underlying stream.
     */
    private CHOICE getUserChoice() throws IOException {
        var result = inputUtility.nextString("hit or pass?", PLAY_PREDICATE);
        return Enum.valueOf(CHOICE.class, result.trim().toUpperCase(Locale.ENGLISH));
    }

    /**
     * Remember that the user has passed.
     */
    private void pass() {
        isPassed = true;
    }

    /**
     * Display the status of the game.
     */
    private void displayStatus() {
        printStream.println("Your cards are: ");
        hand.cards().forEach(printStream::println);
        printStream.println(String.format("Your score is: %d", hand.bestScore()));
    }

    /**
     * Checks to see if the game is over.
     *
     * @return if the game is over or not.
     */
    private boolean isGameOver() {
        if (hand.bestScore() == MAX_LEGAL_SCORE) {
            printStream.println("You win. Game over.");
            return true;
        }
        if (hand.isBust()) {
            printStream.println("You lose. Game over.");
            return true;
        }
        if (isPassed) {
            printStream.println("You passed. Game over.");
            return true;
        }
        return false;
    }

    /**
     * Deals another card to the user.
     */
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
