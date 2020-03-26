// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package fail;

import java.lang.System;

// This program has lexical errors and shouldn't compile.

public class DoConditionalOrFail {

    public static void main(String[] args) {
        boolean a = true || || true;
    }

}
