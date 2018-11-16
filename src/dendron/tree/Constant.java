package dendron.tree;

import dendron.machine.Machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Constant implements ExpressionNode {
    /**
     * An expression node representing a constant, i.e., literal value
     */
    private int value;

    /**
     * @param value value passed into the constructor to make a field
     */
    public Constant(int value) {
        this.value = value;
    }

    /**
     * @param symTab symbol table, if needed, to fetch variable values
     * @return return the value of the constant
     */
    @Override
    public int evaluate(Map<String, Integer> symTab) {
        return value;
    }

    /**
     * Printout the value of the constnat.
     */
    @Override
    public void infixDisplay() {
        System.out.print(value);
    }

    /**
     * @return add instruction of the constant to the list of machine instruction.
     */
    @Override
    public List<Machine.Instruction> emit() {
        List instruction = new ArrayList();

        Machine.Instruction constant = new Machine.PushConst(value);

        instruction.add(constant);
        return instruction;

    }
}
