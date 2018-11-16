package dendron.tree;

import java.util.Map;

/**
 * A dendron.tree.DendronNode that performs an action but does not
 * calculate a new value. The distinction between
 * ActionNode and ExpressionNode is only useful when
 * the nodes are directly executed by an interpreter.
 *
 * @author James Heliotis
 */
public interface ActionNode extends DendronNode {
    /**
     * Perform the action represented by this node. Actions are
     * things like changing variable values.
     *
     * @param symTab the table where variable values are stored
     */
    void execute(Map<String, Integer> symTab);
}
