package lingi2132;

import java.util.ArrayList;
import static jminusminus.CLConstants.*;
import jminusminus.CLEmitter;

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


public class Generator extends GlobalGenerator {
	public Generator(String s) {
		super(s);
	}
	
	public void generateClass() {
		CLEmitter output = new CLEmitter(true);
		output.destinationDir(outputDir);
		
		ArrayList<String> mods = new ArrayList<>();
		mods.add("public");
		// mods.add("super"); ???? ADD ????
		
		/* Class */
		output.addClass(mods, "ClassToGenerate", "java/lang/Object", null, false); // null for last argument ?
		
		/* Constructor */
		output.addMethod(mods, "<init>", "()V", null, false);
		output.addNoArgInstruction(ALOAD_0);
		output.addMemberAccessInstruction(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
		output.addNoArgInstruction(RETURN);
		
		/* Method gcd */
		mods.add("static");
		output.addMethod(mods, "gcd", "(I,I)I", null, false);
		output.addBranchInstruction(GOTO, "out");
		output.addLabel("statement");
		// Make if - else statement
		output.addNoArgInstruction(ILOAD_0);
		output.addNoArgInstruction(ILOAD_1);
		output.addBranchInstruction(IFLE, "updateB");
		// Update a
		// ...
		output.addLabel("updateB");
		// Update b
		// ...
		output.addLabel("endIf");
		// Branching instruction
		output.addNoArgInstruction(ILOAD_1);
		output.addNoArgInstruction(ICONST_0);
		output.addBranchInstruction(IFNE, "statement");
		output.addLabel("out");
		// Return
		output.addNoArgInstruction(ILOAD_0); // Maybe error because we reload the initial value ? And should use the value a
		
	}
}
