package owen.exception;

/**
 * Represents a custom exception class for catching user input errors.
 */
public class OwenException extends Exception {

    /**
     * Constructs an OwenException object.
     *
     * @param message The error message to be displayed.
     */
    public OwenException(String message) {
        super(message);
        assert message != null : "Error message should not be null";
    }
}
