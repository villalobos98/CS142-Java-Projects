package dendron.tree;
/**
 * The ExpressionNode for a simple variable
 */

import dendron.Errors;
import dendron.machine.Machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Variable implements ExpressionNode {
    private String name;

    /**
     * @param name given the name a variable field is created.
     */
    public Variable(String name) {
        this.name = name;
    }

    /**
     * @param symTab symbol table, if needed, to fetch variable values
     * @return an error if there the condition is met or the symtab with the name passed in.
     */
    @Override
    public int evaluate(Map<String, Integer> symTab) {
        if (name == null) {
            Errors.report(Errors.Type.UNINITIALIZED, null);
        }
        return symTab.get(name);
    }

    /**
     * printout of the variable
     */
    @Override
    public void infixDisplay() {
        System.out.print(this.name);
    }

    /**
     * @return return the list of machine instruction with the load instruction added.
     */
    @Override
    public List<Machine.Instruction> emit() {
        List x = new ArrayList();
        Machine.Load load = new Machine.Load(this.name);
        x.add(load);
        return x;
    }
}
