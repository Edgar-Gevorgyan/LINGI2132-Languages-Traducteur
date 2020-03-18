package regex;

import java.util.Arrays;

public class Main {
	
	public static void main(String[] args) {
		Generator gen = new Generator(10);
		String[] s = {"a","b","c"};
		Regex r = new Regex();
		System.out.println(r.parse(s));
	}
	
}
