
package pass;

import java.lang.System;

public class ConditionnalOr {
	
	public static boolean ff() {
		return false || false;
	}
	
	public static boolean ft() {
		return false || true;
	}
	
	public static boolean tf() {
		/*
		 * SISI
		 * 
		 */
		return true || false;
	}
	
	public static boolean tt() {
		return true || true;
	}
	
	public static boolean test1() {
		return false && true || true;
	}

    public static void main(String[] args) {
    	/*
    	 * lkmfk
    	 */
        System.out.println(ff());
        System.out.println(ft());
        System.out.println(tf());
        System.out.println(tt());
        System.out.println(test1());
    }

}
