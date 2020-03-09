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
		output.destinationDir(super.outputDir);
		
		ArrayList<String> mods = new ArrayList<>();
		mods.add("public");
		mods.add("super");

		
		/* Class */
		output.addClass(mods, "packageOfClassToGenerate/ClassToGenerate", "java/lang/Object", null, false); // null for last argument ?
		
		mods.clear();
		mods.add("public");
		
		/* Constructor */
		output.addMethod(mods, "<init>", "()V", null, false);
		output.addNoArgInstruction(ALOAD_0);
		output.addMemberAccessInstruction(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
		output.addNoArgInstruction(RETURN);
		
		/* Method gcd */
		mods.add("static");
		output.addMethod(mods, "gcd", "(II)I", null, false);
		output.addLabel("in");
		output.addNoArgInstruction(ILOAD_1);
		output.addBranchInstruction(IFEQ, "out");
		output.addNoArgInstruction(ILOAD_0);
		output.addNoArgInstruction(ILOAD_1);
		output.addBranchInstruction(IF_ICMPLE, "updateB");
		output.addNoArgInstruction(ILOAD_0);
		output.addNoArgInstruction(ILOAD_1);
		output.addNoArgInstruction(ISUB);
		output.addNoArgInstruction(ISTORE_0);
		output.addBranchInstruction(GOTO, "in");
		
		output.addLabel("updateB");
		output.addNoArgInstruction(ILOAD_1);
		output.addNoArgInstruction(ILOAD_0);
		output.addNoArgInstruction(ISUB);
		output.addNoArgInstruction(ISTORE_1);
		output.addBranchInstruction(GOTO, "in");
		
		output.addLabel("out");
		output.addNoArgInstruction(ILOAD_0);
		output.addNoArgInstruction(IRETURN);
		
		
		// Write
		output.write();
	}
}
