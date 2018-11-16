package dendron.tree;
/**
 * A calculation represented by a binary operator and its two operands.
 */

import dendron.Errors;
import dendron.machine.Machine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BinaryOperation implements ExpressionNode {
    public static final String ADD = "+";
    public static final String DIV = "/";
    public static final String MUL = "*";
    public static final String SUB = "-";
    public static final List<String> OPERATIONS = new ArrayList<>(Arrays.asList(ADD, DIV, MUL, SUB));

    private String operator;
    private ExpressionNode leftChild;
    private ExpressionNode rightChild;

    /**
     * @param Operator   The string that will be passed into this
     * @param leftChild  The expression node that the leftchild uses to evaluate.
     * @param rightChild The expression node for the rightchild passed into operation that will be evaluated.
     */
    public BinaryOperation(String Operator, ExpressionNode leftChild, ExpressionNode rightChild) {

        operator = Operator;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    /**
     * @param symTab symbol table, if needed, to fetch variable values
     * @return the left child and right child evaluate, operation will depend on the operator passed in.
     */
    @Override
    public int evaluate(Map<String, Integer> symTab) {
        if (operator.equals(ADD)) {
            return leftChild.evaluate(symTab) + rightChild.evaluate(symTab);
        } else if (operator.equals(DIV)) {
            if (rightChild.evaluate(symTab) == 0) {
                Errors.report(Errors.Type.DIVIDE_BY_ZERO, null);
            }
            return leftChild.evaluate(symTab) / rightChild.evaluate(symTab);
        } else if (operator.equals(MUL)) {
            return leftChild.evaluate(symTab) * rightChild.evaluate(symTab);
        } else if (operator.equals(SUB)) {
            return leftChild.evaluate(symTab) - rightChild.evaluate((symTab));
        }
        return 0;
    }

    /**
     * The printout of the left child, parent and right child in the correct order.
     */
    @Override
    public void infixDisplay() {
        System.out.print(" ( ");
        this.leftChild.infixDisplay();
        System.out.print(" " + this.operator + " ");
        this.rightChild.infixDisplay();
        System.out.print(" ) ");

    }

    /**
     * @return the leftchild/rightchild emit and adds to the machine instruction.
     */
    @Override
    public List<Machine.Instruction> emit() {
        List instruction = new ArrayList();
        instruction.addAll(leftChild.emit());
        instruction.addAll(rightChild.emit());

        Machine.Instruction add = new Machine.Add();
        Machine.Instruction subtract = new Machine.Subtract();
        Machine.Instruction multiply = new Machine.Multiply();
        Machine.Instruction divide = new Machine.Divide();

        if (operator.equals(ADD)) {
            instruction.add(add);
            return instruction;
        } else if (operator.equals(SUB)) {
            instruction.add(subtract);
            return instruction;
        } else if (operator.equals(MUL)) {
            instruction.add(multiply);
            return instruction;
        } else if (operator.equals(DIV)) {
            instruction.add(divide);
            return instruction;
        }
        return null;
    }
}
