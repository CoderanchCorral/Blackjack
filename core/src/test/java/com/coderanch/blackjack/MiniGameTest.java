/*
 * Copyright (C) 2018 Coderanch.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.coderanch.blackjack;

import com.coderanch.util.cli.InputUtility;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class MiniGameTest {

    /**
     * How many times to keep hitting.
     * Ensures the loss of the game.
     */
    private static final int HIT_TO_FAILURE = 20;

    /**
     * Pass in the game.
     */
    @Test
    public void testPass() throws IOException {
        var input = "pass";
        try (
                var stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
                var inputUtility = new InputUtility(stream, StandardCharsets.UTF_8);
                var output = new ByteArrayOutputStream();
        ) {
            var miniGame = new MiniGame(inputUtility, new PrintStream(output));
            miniGame.run();
            var result = output.toString();
            assertThat("Must say the user passed.", result, containsString("You passed."));
        }
    }

    /**
     * Hit until game over.
     */
    @Test
    public void testHit() throws IOException {
        var input = IntStream.range(0, HIT_TO_FAILURE)
                .mapToObj(i -> "hit")
                .collect(Collectors.joining(System.lineSeparator()));
        try (
                var stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
                var inputUtility = new InputUtility(stream, StandardCharsets.UTF_8);
                var output = new ByteArrayOutputStream();
        ) {
            var miniGame = new MiniGame(inputUtility, new PrintStream(output));
            miniGame.run();
            var result = output.toString();
            assertThat("Must say the user lost or won.", result, containsString("Game over."));
        }
    }
}
