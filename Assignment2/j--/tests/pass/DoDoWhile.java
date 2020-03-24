
package pass;

import java.lang.System;

public class DoDoWhile {
	
	public static int test1() {
		int count = 0;
		
		do {
			count = count + 1;
		} while (2 > count);
		
		return 2;
	}
	
	public static int test2() {
		int count = 0;
		
		do {
			count = count + 1;
		} while (0 > count);
		
		return 1;
	}

    public static void main(String[] args) {
    	/*
    	 * lkmfk
    	 */
        System.out.println(test1());
        System.out.println(test2());
    }

}