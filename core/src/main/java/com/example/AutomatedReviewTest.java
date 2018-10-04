package com.example;

import java.util.*;

// The goal of this code is to find out if code analysis will automatically add
// commentary to a pull request when it finds problems with the code.

public abstract class AutomatedReviewTest extends Object {

  public boolean hello(Optional unusedRaw)
  {
    float x = 3.0f;
    if (x + 2.0f * 1.0f == 5.0f)
      System.out.println("Yay\u0021".toUpperCase());
      
    return true;
  }

  private AutomatedReviewTest() { hello(Optional.of(this)); }
  
  public static void main(String... args) throws SomeError {
  	}
}

final class SomeError extends Error {

	public static java.util.Collection<Object> collection = null;
	
	protected Collection getCollectionLazily() {
		if (collection == null) {
			collection = new ArrayList<Object>();
		}
		
		return collection;
	}
}
