package dendron;

import java.util.Map;

/**
 * Share code for dealing with Dendron program errors:
 * <ul>
 *     <li>unknown statement type</li>
 *     <li>illegal value in expression</li>
 *     <li>uninitialized variable in expression</li>
 *     <li>premature end of statement</li>
 *     <li>extra tokens at end of statement</li>
 * </ul>
 *
 * @author James Heliotis
 */
public class Errors {

    /**
     * The exit status returned by the Java virtual machine if an
     * error is reported via this class
     */
    public static final int DENDRON_ABORT = 1;

    public enum Type {
        DIVIDE_BY_ZERO( "divide by zero" ),
        ILLEGAL_VALUE( "illegal value encountered in source" ),
        UNINITIALIZED( "uninitialized variable in expression" ),
        PREMATURE_END( "premature end of statement" );

        private final String message;

        Type( String message ) {
            this.message = message;
        }
    }

    /**
     * Report an error and stop the program. All output goes to standard error.
     *
     * @param type The kind of error, printed first
     * @param info if not null, an additional value to be printed after a colon
     */
    public static void report( Type type, Object info ) {
        System.err.print( type.message );
        if ( info != null ) {
            System.err.print( ": " + info );
        }
        System.exit( DENDRON_ABORT );
    }

    /**
     * Show on standard output the values of all the variables in the table.
     * @param table the program's symbol table
     */
    public static void dump( Map< String, Integer > table ) {
        System.out.println( "Symbol Table Contents\n=====================\n" );
        for ( String ident: table.keySet() ) {
            System.out.printf( "%12s : %11d\n", ident, table.get( ident ) );
        }
    }
}
