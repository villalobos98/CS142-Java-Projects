package dendron.tree;
/**
 * An ActionNode used to represent a sequence of other ActionNodes.
 */

import dendron.machine.Machine;

import java.util.ArrayList;

/**
 * Add a child of this Program node.
 */
public class Program extends Object implements ActionNode {
    ArrayList<ActionNode> actionNodes;

    /**
     Initialize this instance as an empty sequence of ActionNode children.
     */
    public Program() {
        this.actionNodes = new ArrayList<>();
    }
    /**
     * Execute each ActionNode in this object, from first-added to last-added.
     */
    public void addAction(ActionNode newNode) {
        this.actionNodes.add(newNode);
    }

    /**
     * Execute each ActionNode in this object, from first-added to last-added.
     * @param symTab the table where variable values are stored
     */
    public void execute(java.util.Map<String,Integer> symTab){
        for(ActionNode node: actionNodes){
            node.execute(symTab);
        }
    }

    /**
     * @return the list of machine instruction
     */
    public java.util.List<Machine.Instruction> emit() {
        ArrayList<Machine.Instruction> instructions = new ArrayList<>();
        for(ActionNode node: actionNodes){
            instructions.addAll(node.emit());
        }
        return instructions;
    }

    /**
     * Show the infix displays of all children on standard output. The order is first-added to last-added.
     */
    public void infixDisplay() {
        for(ActionNode node: actionNodes){
            node.infixDisplay();
            System.out.println();
        }
    }

}
