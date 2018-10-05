/*
 * Copyright (C) 2018 Coderanch.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.example;

import java.util.*;
import java.util.logging.*;

import java.lang.*;

import java.time.*;

/**
 * The goal of this code is to find out if code analysis will automatically add
 * commentary to a pull request when it finds problems with the code.
 */
public final class AutomatedReviewTest {
    
    private static final Logger LOGGER = Logger.getLogger(AutomatedReviewTest.class.getName());
    
    private static final long ONE   = 1L;
    private static final long TWO   = 2L;
    private static final long THREE = 3L;
    private static final long FOUR  = 4L;
    
    private static Object lazy;
    
    private AutomatedReviewTest() {
        canBeStatic();
    }
    
    /**
     * Method description without description of parameters or return value.
     *
     * @return blabla
     */
    public boolean hello() {
        long x = THREE;
        
        if (x + TWO - ONE == FOUR) {
            LOGGER.info(() -> "Yay!".toUpperCase(Locale.ENGLISH));
        }
        
        return true;
    }
    
    static void canBeStatic() {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Gets an object lazily.
     */
    public static Object getLazily() {
        if (lazy == null) {
            lazy = new Object();
        }
        
        return lazy;
    }
}
