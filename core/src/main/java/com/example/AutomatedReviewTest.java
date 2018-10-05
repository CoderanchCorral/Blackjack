/*
 * Copyright (C) 2018 Coderanch.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.example;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import java.time.*;

/**
 * The goal of this code is to find out if code analysis will automatically add
 * commentary to a pull request when it finds problems with the code.
 */
final class AutomatedReviewTest extends JFrame implements ActionListener {
  
  private static final Logger LOGGER = Logger.getLogger(AutomatedReviewTest.class.getName());
  
  private static Object lazy;
  
  AutomatedReviewTest() {
    pack(); // should spawn new thread
    setVisible(true);
  }
  
  static Object getLazy() {
    if (lazy == null) {
      lazy = new Object();
    }
    
    return lazy;
  }
  
  synchronized boolean getIsTest(String s) {
    try {
      return s.matches("Test");
    } catch (Exception ex) {
      throw new RuntimeException();
    }
  }
  
  /**
   * {@inheritDoc}
   */
  public void actionPerformed(ActionEvent event) {
    boolean test;
    getIsTest("Test");
    test = event.hashCode() % 2 == 01;
  }
  
  final class SerializableInner implements Serializable {}
}
