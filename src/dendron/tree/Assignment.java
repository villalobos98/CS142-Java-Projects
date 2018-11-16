package dendron.tree;
/***
 * @author Isaias Villalobos
 * An ActionNode that represents the assignment of the value of an
 * expression to a variable.
 */

import dendron.machine.Machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Assignment implements ActionNode {

    private String ident;
    private ExpressionNode rhs;

    /**
     * @param ident String representing the variable
     * @param rhs   The expressiong node that right be looked at.
     */
    public Assignment(String ident, ExpressionNode rhs) {
        this.ident = ident;
        this.rhs = rhs;
    }

    /**
     * @param symTab the table where variable values are stored
     */
    @Override
    public void execute(Map<String, Integer> symTab) {
        int i = rhs.evaluate(symTab);
        symTab.put(ident, i);
    }

    /**
     * Assignment on standard output as a variable followed
     * by an assignment arrow (":=") followed by the infix form of the RHS expression.
     */
    @Override
    public void infixDisplay() {
        System.out.print(ident + " := ");
        rhs.infixDisplay();
    }

    /**
     * @return the instruction and add it to the arraylist.
     */
    @Override
    public List<Machine.Instruction> emit() {
        List<Machine.Instruction> instruction = new ArrayList<>();

        Machine.Instruction expr = new Machine.Store(ident);
        instruction.addAll(rhs.emit());
        instruction.add(expr);
        return instruction;
    }
}
