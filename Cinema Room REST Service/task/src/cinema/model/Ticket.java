package cinema.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ticket {
    private String token;
    @JsonProperty("ticket")
    private Seat seat;

    public Ticket() {
    }

    public Ticket(String token, Seat seat) {
        this.token = token;
        this.seat = seat;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
