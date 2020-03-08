package lingi2132;

public class Generator extends GlobalGenerator {
	public Generator(String s) {
		super(s);
	}
	
	public void generateClass() {
		/**
		 *package packageOfClassToGenerate;
		 *
		 *public class ClassToGenerate {
		 *	public static int gcd(int a, int b) {
		 *		while (b != 0) {
		 *			if (a > b) {
		 *				a = a - b;
		 *			} else {
		 *				b = b - a;
		 *			}
		 *		}
		 *	return a;
		 *	}
		 *}
		 */
	}
}
