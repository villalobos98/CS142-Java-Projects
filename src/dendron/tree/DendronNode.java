package dendron.tree;

import java.util.List;
import dendron.machine.Machine;

/**
 * The top-level abstraction for all nodes in the Dendron parse tree.
 *
 * All nodes are capable of being displayed as part of an infix-format string,
 * and of emitting machine instructions so that they can be executed later.
 *
 * @author James Heliotis
 */
public interface DendronNode {
    /**
     * Show the code rooted at this node, using infix format,
     * on standard output.
     */
    void infixDisplay();

    /**
     * Generate a list of instructions that, when executed, represents
     * the intent of this DendronNode and its descendants.
     * @return the Machine Instructions for this node
     */
    List<  Machine.Instruction> emit();
}


