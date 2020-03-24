// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package fail;

import java.lang.System;

// This program has lexical errors and shouldn't compile.

public class DoDoWhileFail {

    public static void main(String[] args) {
    	int a = 2;
        do {
        	System.out.println(2);
        } while(true)
    }

}
