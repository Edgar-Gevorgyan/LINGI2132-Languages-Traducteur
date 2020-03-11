// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;

/**
 * The AST node for a do-while-statement.
 */

class JDoWhileStatement extends JStatement {

    /** Test expression. */
    private JExpression condition;

    /** The body. */
    private JStatement body;

    /**
     * Constructs an AST node for a do-while-statement given its line number, the
     * test expression, and the body.
     * 
     * @param line
     *            line in which the do-while-statement occurs in the source file.
     * @param condition
     *            test expression.
     * @param body
     *            the body.
     */

    public JDoWhileStatement(int line,JExpression condition, JStatement body) {
        super(line);
        this.condition = condition;
        this.body = body;
    }

    /**
     * Analysis involves analyzing the test, checking its type and analyzing the
     * body statement.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JDoWhileStatement analyze(Context context) {
        body = (JStatement) body.analyze(context);
        condition = (JExpression) condition.analyze(context);
        condition.type().mustMatchExpected(line(), Type.BOOLEAN);
        return this;
    }

    /**
     * Generates code for the do-while loop.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
        // Need two labels
        String out = output.createLabel();
        String test = output.createLabel();
        
        output.addLabel(test);
        // Codegen body before check condition
        body.codegen(output);
        
        condition.codegen(output, test , true);
        output.addLabel(out);
    }

    /**
     * {@inheritDoc}
     */

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JDoWhileStatement line=\"%d\">\n", line());
        p.indentRight();
        p.printf("<Body>\n");
        p.indentRight();
        body.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Body>\n");
        p.printf("<TestExpression>\n");
        p.indentRight();
        condition.writeToStdOut(p);
        p.indentLeft();
        p.printf("</TestExpression>\n");
        p.indentLeft();
        p.printf("</JDoWhileStatement>\n");
        
        
    }

}