package owen.exception;

/**
 * Custom exception class for catching user input errors
 */
public class OwenException extends Exception {

    /**
     * Constructor for OwenException
     *
     * @param message the error message to be displayed
     */
    public OwenException(String message) {
        super(message);
        assert message != null : "Error message should not be null";
    }
}
