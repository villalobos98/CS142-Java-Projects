package dendron.tree;

import dendron.Errors;
import dendron.machine.Machine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Operations that are done on a Dendron code parse tree.
 * <p>
 * THIS CLASS IS UNIMPLEMENTED. All methods are stubbed out.
 *
 * @author Isaias Villalobos
 */
public class ParseTree {

    private Program tokenList;

    /**
     * Parse the entire list of program tokens. The program is a
     * sequence of actions (statements), each of which modifies something
     * in the program's set of variables. The resulting parse tree is
     * stored internally.
     *
     * @param program the token list (Strings)
     */
    public ParseTree(List<String> program) {
        this.tokenList = new Program();//root node of program.
        while (program.size() != 0) {
            tokenList.addAction(parseAction(program));
        }
    }

    /**
     * Parse the next action (statement) in the list.
     * (This method is not required, just suggested.)
     *
     * @param program the list of tokens
     * @return a parse tree for the action
     */
    private ActionNode parseAction(List<String> program) {
        ActionNode x = null;
        if (program.get(0).equals(":=")) {
            program.remove(0);
            String f = program.get(0);
            program.remove(0);
            x = new Assignment(f, parseExpr(program));

        } else if (program.get(0).equals("@")) {
            program.remove(0);
            x = new Print(parseExpr(program));

        }
        return x;
    }

    /**
     * Parse the next expression in the list.
     * (This method is not required, just suggested.)
     *
     * @param program the list of tokens
     * @return a parse tree for this expression
     */
    private ExpressionNode parseExpr(List<String> program) {
        String ex = program.remove(0);

        if (BinaryOperation.OPERATIONS.contains(ex)) {
            ExpressionNode leftChild = parseExpr(program);
            ExpressionNode rightChild = parseExpr(program);
            if (leftChild == null || rightChild == null) {
                Errors.report(Errors.Type.PREMATURE_END, null);
            }
            BinaryOperation b = new BinaryOperation(ex, leftChild, rightChild);
            return b;
        } else if (UnaryOperation.OPERATORS.contains(ex)) {

            UnaryOperation sqrt = new UnaryOperation(ex, parseExpr(program));
            return sqrt;

        } else if (ex.matches("^[a-zA-Z].*")) {
            Variable v = new Variable(ex);
            return v;
        } else if (ex.matches("[-+]?\\d+")) {
            int cst = Integer.parseInt(ex);
            Constant u = new Constant(cst);
            return u;
        } else {
            Errors.report(Errors.Type.ILLEGAL_VALUE, null);
            return null;
        }
    }


    /**
     * Print the program the tree represents in a more typical
     * infix style, and with one statement per line.
     *
     * @see dendron.tree.ActionNode#infixDisplay()
     */
    public void displayProgram() {
        tokenList.infixDisplay();
    }

    /**
     * Run the program represented by the tree directly
     *
     * @see dendron.tree.ActionNode  #execute(Map)
     */
    public void interpret() {
        Map<String, Integer> symTab = new HashMap<>();
        System.out.println("Interpreting the parse tree...");
        tokenList.execute(symTab);
        System.out.println("Interpretation complete.");
        System.out.println();
        System.out.println("Symbol Table Contents");
        System.out.println("=====================");

    }

    /**
     * Build the list of machine instructions for
     * the program represented by the tree.
     *
     * @return the Machine.Instruction list
     * @see Machine.Instruction#execute()
     */
    public List<Machine.Instruction> compile() {
        return tokenList.emit();
    }

}
