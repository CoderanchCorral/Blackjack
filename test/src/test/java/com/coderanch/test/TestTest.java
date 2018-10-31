package com.coderanch.test;

import org.junit.Test;

public final class TestTest {

    private final ConsistentComparableTest testToTest = new ConsistentComparableTest();

    @Test
    public void allTests_withConsistentObjects_succeed() {
        testToTest.equals_withNull_returnsFalse("I'm not null.");

        testToTest.equals_isReflexive("I'm equal to myself.");
        testToTest.equals_isReflexive(null);

        testToTest.equals_isSymmetric(
            "We're equal to each other.",
            "We're equal to each other."
        );

        testToTest.equals_isSymmetric(
            "We're not equal to each other.",
            "See?"
        );

        testToTest.equals_isTransitive(
            "We're all equal to each other.",
            "We're all equal to each other.",
            "We're all equal to each other."
        );

        testToTest.equals_isTransitive(
            "I'm not equal to the others.",
            "But we are.",
            "But we are."
        );

        testToTest.equals_isTransitive(
            "You're all different!",
            "Yes, we are all different!",
            "I'm not."
        );

        testToTest.hashCode_withEqualObjects_returnsEqualResult(
            "Our hash codes are the same.",
            "Our hash codes are the same."
        );

        testToTest.toString_neverReturnsNull("");
    }
}
