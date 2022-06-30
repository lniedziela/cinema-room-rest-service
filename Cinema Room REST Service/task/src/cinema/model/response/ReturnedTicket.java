package cinema.model.response;

import cinema.model.Seat;
import cinema.model.Ticket;

public class ReturnedTicket {
    private Seat returnedTicket;

    public ReturnedTicket() {
    }

    public ReturnedTicket(Seat returnedTicket) {
        this.returnedTicket = returnedTicket;
    }

    public Seat getReturnedTicket() {
        return returnedTicket;
    }

    public void setReturnedTicket(Seat returnedTicket) {
        this.returnedTicket = returnedTicket;
    }
}
