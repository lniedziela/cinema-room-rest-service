package cinema.exception;

public class RowOrColumnNumberOutOfBoundsException extends BusinessLogicException {
    public RowOrColumnNumberOutOfBoundsException() {
        super("The number of a row or a column is out of bounds!");
    }
}
