package com.coderanch.blackjack;

import java.util.*;

/**
 * A hand in a game of Blackjack.
 */
public class Hand {

    private static final int HIGHEST_SCORE = 21;
    private static final int MAX_SCORE_COUNT = 2^4;

    private List<Card> cards = new ArrayList<>();
    private boolean isStopped;
    private Set<Integer> possibleScores;
    private int bestScore;

    /**
     * Adds a card to the hand.
     *
     * @param card the card being added to the hand.
     */
    public void addCard(Card card) {
        if(isStopped){
            throw new IllegalArgumentException("Hand is already stopped");
        }
        cards.add(card);
        this.possibleScores = calculateScores();
        this.bestScore = calculateBestScore();
    }

    private Set<Integer> calculateScores() {
        var startingScore = cards.stream()
                .filter(c -> c.values().length == 1)
                .flatMapToInt(c -> Arrays.stream(c.values()))
                .sum();
        var scores = new ArrayDeque<Integer>(MAX_SCORE_COUNT);
        scores.add(startingScore);

        cards.stream()
                .filter(c -> c.values().length > 1)
                .map(Card::values)
                .forEach((int[] nums) ->{
                    var initialSize = scores.size();
                    for (int i = 0; i < initialSize; i++) {
                        var currentNum = scores.pop();
                        for (int j = 0; j < nums.length; j++) {
                            scores.addLast(currentNum + nums[j]);
                        }
                    }
                });
        return new LinkedHashSet<>(scores);
    }

    private int calculateBestScore() {
        return this.possibleScores.stream()
                .sorted((o1, o2) -> Integer.compare(o2, o1))
                .filter(i -> i <= HIGHEST_SCORE)
                .findFirst()
                .orElse(0);
    }

    /**
     * Gets the current best score.
     *
     * @return the current best score.
     */
    public int bestScore() { return this.bestScore; }

    /**
     * Stops the hand.  Cannot receive more cards.
     */
    public void stop() {
        this.isStopped = true;
    }
}
