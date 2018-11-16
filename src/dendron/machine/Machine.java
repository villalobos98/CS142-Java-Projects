package dendron.machine;

import java.util.*;

import dendron.Errors;

/**
 * An abstraction of a computing machine that reads instructions
 * and executes them. It has an instruction set, a symbol table
 * for variables (instead of general-purpose memory), and a
 * value stack on which calculations are performed.
 * <p>
 * (Everything is static to avoid the need to master the subtleties
 * of nested class instantiation or to pass the symbol table and
 * stack into every instruction when it executes.)
 * <p>
 * THIS CLASS IS NOW COMPLETE AS OF 3/9/2018
 *
 * @author James Heliotis
 * @author Isaias Villalobos
 */
public class Machine {

    /**
     * Do not instatiate this class.
     */
    private Machine() {
    }

    public static interface Instruction {
        /**
         * Run this instruction on the Machine, using the Machine's
         * value stack and symbol table.
         */
        void execute();

        /**
         * Show the instruction using text so it can be understood
         * by a person.
         *
         * @return a short string describing what this instruction will do
         */
        @Override
        String toString();
    }

    private static Map<String, Integer> table = null;
    private static Stack<Integer> stack = null;

    /**
     * Reset the Machine to a pristine state.
     *
     * @see Machine#execute
     */
    private static void reset() {
        stack = new Stack<>();
        table = new HashMap<>();
    }

    /**
     * Generate a listing of a program on standard output by
     * calling the toString() method on each instruction
     * contained therein, in order.
     *
     * @param program the list of instructions in the program
     */
    public static void displayInstructions(
            List<Machine.Instruction> program) {
        System.out.println("\nCompiled code:");
        for (Machine.Instruction instr : program) {
            System.out.println(instr);
        }
        System.out.println();
    }

    /**
     * Run a "compiled" program by executing in order each instruction
     * contained therein.
     * Report on the final size of the stack (should normally be empty)
     * and the contents of the symbol table.
     *
     * @param program a list of Machine instructions
     */
    public static void execute(List<Instruction> program) {
        reset();
        System.out.println("Executing compiled code...");
        for (Instruction instr : program) {
            instr.execute();
        }
        System.out.println("Machine: execution ended with " +
                stack.size() + " items left on the stack.");
        System.out.println();
        Errors.dump(table);
    }

    /**
     * The ADD instruction
     * Pops off the stack 2 elements and then pushes the added value.
     */
    public static class Add implements Instruction {
        /**
         * Run the microsteps for the ADD instruction.
         */
        @Override
        public void execute() {
            int op2 = stack.pop();
            int op1 = stack.pop();
            stack.push(op1 + op2);
        }

        /**
         * Show the ADD instruction as plain text.
         *
         * @return "ADD"
         */
        @Override
        public String toString() {
            return "ADD";
        }
    }

    /**
     * The STORE instruction
     */
    public static class Store implements Instruction {
        /**
         * stores name of target variable
         */
        private String name;

        /**
         * Create a STORE instruction
         *
         * @param ident the name of the target variable
         */
        public Store(String ident) {
            this.name = ident;
        }

        /**
         * Run the microsteps for the STORE instruction.
         */
        @Override
        public void execute() {
            table.put(this.name, stack.pop());
        }

        /**
         * Show the STORE instruction as plain text.
         *
         * @return "STORE" followed by the target variable name
         */
        @Override
        public String toString() {
            return "STORE " + this.name;
        }
    }

    /**
     * Subtract removes pops two elements in the list and then pushes the subtracted value onto stack
     */
    public static class Subtract implements Instruction {

        @Override
        public void execute() {
            int op1 = stack.pop();
            int op2 = stack.pop();
            stack.push(op2 - op1);
        }

        /**
         * @return String representation of the subtract tostring
         */
        @Override
        public String toString() {
            return "SUBTRACT";
        }
    }

    /**
     * Multiply pops two elements in the stack and then multiplies the value together.
     * The multiplied value then gets pushed onto the stack.
     */
    public static class Multiply implements Instruction {
        /**
         * Pops then pushes.
         */
        @Override
        public void execute() {
            int op1 = stack.pop();
            int op2 = stack.pop();
            stack.push(op1 * op2);
        }

        /**
         * @return String representation of the multiply operation.
         */
        @Override
        public String toString() {
            return "MULTIPLY";
        }
    }

    /**
     * Divide pops two elements on the stack and then divides second operator by first operator.
     * Pushes the result onto stack.
     */
    public static class Divide implements Instruction {
        @Override
        /**
         * pops elements and then divides
         */
        public void execute() {
            int op1 = stack.pop();
            int op2 = stack.pop();
            stack.push(op2 / op1);
        }

        /**
         * @return String representation of divide operation.
         */
        @Override
        public String toString() {
            return "DIVIDE";
        }
    }

    /**
     * Constant simply pushes onto the stack.
     */
    public static class PushConst implements Instruction {
        private int i;

        /**
         * @param i an integer value that will be pushed onto stack.
         */
        public PushConst(int i) {

            this.i = i;
        }

        /**
         * Push onto stack
         */
        @Override
        public void execute() {
            stack.push(this.i);
        }

        /**
         * @return String representation of the push operation
         */
        public String toString() {
            return "PUSH " + this.i;
        }

    }

    /**
     * Load the variable, by pushing onto stack and symbol table
     */
    public static class Load implements Instruction {
        private String v;

        /**
         * @param v creates a field to load onto the stack
         */
        public Load(String v) {
            this.v = v;
        }

        /**
         * Pushes the variable onto the stack by calling its symTab
         * representation.
         */
        @Override
        public void execute() {
            stack.push(table.get(v));
        }

        /**
         * @return The instruction using text so it can be understood by person
         */
        @Override
        public String toString() {
            return "LOAD " + this.v;
        }
    }

    /**
     * A unaray operation that will negate the value given
     */
    public static class Negate implements Instruction {
        /**
         * Pops off the stack and then pushes the negated value in
         */
        @Override
        public void execute() {
            int var = stack.pop();
            var = var * -1;
            stack.push(var);
        }

        /**
         * @return String representation of the negate to be understood by person
         */
        public String toString() {
            return "NEG";
        }
    }

    /**
     * Class to makes sure that the square root of the operation can be calculated.
     */
    public static class SquareRoot implements Instruction {
        /**
         * Pops off the stack and then computes the square root of the number popped.
         * Pushes the new value onto stack
         */
        @Override
        public void execute() {
            int num = stack.pop();
            int sqrt = (int) Math.sqrt(num);
            stack.push(sqrt);
        }

        /**
         * @return String representation of the square root opeoration.
         */
        public String toString() {
            return "SQRT";
        }

    }

    /**
     * Print class which prints out the popped value of the stack
     */
    public static class Print implements Instruction {
        public Print() {
        }

        /**
         * Popss and prints the value of the stack
         */
        @Override
        public void execute() {
            System.out.println("*** " + stack.pop());

        }

        /**
         * @return String represenation of the print class
         */
        @Override
        public String toString() {
            return "PRINT";
        }
    }

}