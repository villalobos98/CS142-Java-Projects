package dendron.tree;

import dendron.machine.Machine;

import java.util.*;

/**
 * A calculation represented by a unary operator and its operand.
 */
public class UnaryOperation implements ExpressionNode {

    public static final String NEG = "_";
    public static final String SQRT = "#";
    public static final Collection<String> OPERATORS = Arrays.asList(NEG, SQRT);
    private String operation;
    private ExpressionNode expr;

    /**
     * @param operator String that will be passed in
     * @param expr     Expression node that will be evaluated
     */
    public UnaryOperation(String operator, ExpressionNode expr) {
        if (OPERATORS.contains(operator) && expr != null) {
            this.operation = operator;
            this.expr = expr;
        }
    }

    /**
     * @param symTab symbol table, if needed, to fetch variable values
     * @return the negation of the expression or the square root of the expression
     * node's evaluate
     */
    public int evaluate(Map<String, Integer> symTab) {
        if (operation.equals(NEG)) {
            return -1 * expr.evaluate(symTab);
        } else if (operation.equals(SQRT)) {
            return (int) Math.sqrt(expr.evaluate(symTab));
        }
        return Integer.MIN_VALUE;//ask prof
    }

    /**
     * Printout of the expression.
     */
    public void infixDisplay() {

        System.out.print(this.operation);
        expr.infixDisplay();
    }

    /**
     * @return the negation or sqrt instruction which gets added to the list of machine
     * instructions
     */
    public java.util.List<Machine.Instruction> emit() {
        List instruction = new ArrayList();
        instruction.addAll(expr.emit());

        Machine.Instruction negate = new Machine.Negate();
        Machine.Instruction sqrt = new Machine.SquareRoot();

        if (operation.equals(NEG)) {
            instruction.add(negate);
            return instruction;
        } else if (operation.equals(SQRT)) {
            instruction.add(sqrt);
            return instruction;
        }
        return null;
    }

}
