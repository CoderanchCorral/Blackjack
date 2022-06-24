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
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Locale;
import java.util.Random;

import static com.coderanch.util.cli.InputUtility.oneOfTheseIgnoringCase;
import static com.coderanch.util.require.Require.requireThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Mini game of Blackjack.
 */
final class MiniGame {

    /**
     * The possible choices the player can make.
     */
    private enum Choice {
        HIT, PASS
    }

    /**
     * The player's current hand.
     */
    private Hand hand;

    /**
     * The deck of cards used in the game.
     */
    private final Deque<Card> deck;
    /**
     * The input utility used for getting answers.
     */
    private final InputUtility inputUtility;

    /**
     * The print writer used for sending text to the player.
     */
    private final PrintWriter printWriter;

    /**
     * Represents whether the player has passed or not.
     */
    private boolean hasPlayerPassed;

    /**
     * Constructs new mini game of Blackjack.
     *
     * @param inputUtility used for player input.
     * @param printWriter  used for printing text output.
     * @param generator    used for creating a shuffled deck.
     */
    MiniGame(InputUtility inputUtility, PrintWriter printWriter, Random generator) {
        this.inputUtility = requireThat("inputUtility", inputUtility, is(notNullValue()));
        this.printWriter = requireThat("printWriter", printWriter, is(notNullValue()));

        requireThat("generator", generator, is(notNullValue()));

        this.deck = new ArrayDeque<>(Cards.getShuffledStandardDeck(generator));
    }

    /**
     * Run the mini game.
     * Will start a game of Blackjack.
     * Will ask the player to hit or pass until he wins, loses, or passes.
     *
     * @throws IOException if an I/O exception occurred while prompting the player for an action.
     */
    public void run() throws IOException {
        hand = new Hand(deck.remove(), deck.remove());
        displayStatus();

        while (!isGameOver()) {
            var choice = getPlayerChoice();
            switch (choice) {
                case HIT:
                    dealCard();
                    displayStatus();
                    break;

                case PASS:
                default:
                    pass();
                    printWriter.println("You passed. Game over.");
                    break;
            }
        }
    }

    /**
     * Get the player's next choice.
     *
     * @return the player's choice.
     * @throws IOException if there's a problem with the underlying stream.
     */
    private Choice getPlayerChoice() throws IOException {
        var result = inputUtility.nextString("hit or pass?", oneOfTheseIgnoringCase("hit", "pass"));
        return Choice.valueOf(result.trim().toUpperCase(Locale.ENGLISH));
    }

    /**
     * Remember that the player has passed.
     */
    private void pass() {
        hasPlayerPassed = true;
    }

    /**
     * Display the status of the game.
     */
    private void displayStatus() {
        printWriter.println("Your cards are: ");
        hand.cards().forEach(printWriter::println);
        printWriter.println(String.format("Your score is: %d", hand.bestScore()));

        if (hand.isBlackjack()) {
            printWriter.println("You win. Game over.");
        }
        if (hand.isBust()) {
            printWriter.println("You lose. Game over.");
        }
    }

    /**
     * Checks to see if the game is over.
     *
     * @return if the game is over or not.
     */
    private boolean isGameOver() {
        return hand.isBlackjack() || hand.isBust() || hasPlayerPassed;
    }

    /**
     * Deals another card to the player.
     */
    private void dealCard() {
        printWriter.println("You hit.");
        var newCard = deck.removeLast();
        hand = hand.withAdditionalCard(newCard);
        printWriter.println();
        printWriter.println(String.format("Your card was: %s", newCard));
    }


    /**
     * Play basic game of Blackjack through the console.
     *
     * @param args main arguments
     */
    public static void main(String[] args) throws IOException {
        try (var inputUtility = new InputUtility()) {
            var miniGame = new MiniGame(
                inputUtility,
                new PrintWriter(System.out, true, StandardCharsets.UTF_8),
                new Random());
            miniGame.run();
        }
    }
}
