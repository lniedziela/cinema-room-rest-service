package cinema.exception;

public class WrongTokenException extends BusinessLogicException{
    public WrongTokenException() {
        super("Wrong token!");
    }
}
