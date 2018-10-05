package com.example;

import java.util.*;
import java.util.logging.*;

/**
 * The goal of this code is to find out if code analysis will automatically add
 * commentary to a pull request when it finds problems with the code.
 */
public final class AutomatedReviewTest {
    
    private Logger LOGGER = Logger.getLogger(AutomatedReviewTest.class.getName());
    
    protected double one   = 1.0d;
    protected double two   = 2.0d;
    protected double three = 5.0d;
    protected double four  = 4.0d;
    
    private AutomatedReviewTest() {
        canBeStatic();
    }
    
    /**
     * Method description without description of parameters or return value.
     */
    public boolean hello() {
        double x = three;
        
        if (x + two - one == four) {
            LOGGER.info("Yay!".toUpperCase(Locale.ENGLISH));
        }
        
        return true;
    }
    
    private void canBeStatic() { }
}

final class SomeException extends Exception {
    
    private static java.util.Collection<Object> collection;
    
    /**
     * Gets a collection lazily, without defensive copying.
     */
    public static Collection<Object> getCollectionLazily() {
        if (collection == null) {
            collection = new ArrayList<>();
        }
        
        return collection;
    }
}
