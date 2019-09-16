package com.coderanch.blackjack;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class LiuModuleTest {

  @Test
  public void liu_test() {
    assertThat(LiuModule.getNumberFour(), is(equalTo(4)));
  }
}