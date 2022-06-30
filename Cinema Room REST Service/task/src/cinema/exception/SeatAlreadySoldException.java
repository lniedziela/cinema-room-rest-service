package cinema.exception;

public class SeatAlreadySoldException extends BusinessLogicException{
    public SeatAlreadySoldException() {
        super("The ticket has been already purchased!");
    }
}
