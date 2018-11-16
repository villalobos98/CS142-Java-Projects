package dendron.tree;
/**
 * A node that represents the displaying of the value of an expression on the console
 */

import dendron.machine.Machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Print implements ActionNode {

    private final ExpressionNode printee;

    /**
     * @param printee Expression that the constructor takes to create a field.
     */
    public Print(ExpressionNode printee) {
        this.printee = printee;
    }

    /**
     * @param symTab the table where variable values are stored
     *               print out the expression nodes evaluate which then calls the symTab.
     */
    @Override
    public void execute(Map<String, Integer> symTab) {
        int i = printee.evaluate(symTab);
        System.out.println("=== " + i);
    }

    /**
     * Printout the infix display
     */
    @Override
    public void infixDisplay() {
        System.out.print("PRINT ");
        printee.infixDisplay();
    }

    /**
     * @return list of instruction.
     */
    @Override
    public List<Machine.Instruction> emit() {
        List instruction = new ArrayList();
        instruction.addAll(printee.emit());

        Machine.Instruction print = new Machine.Print();
        return instruction;

    }
}
