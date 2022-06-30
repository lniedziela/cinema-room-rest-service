package cinema.controller;

import cinema.exception.BusinessLogicException;
import cinema.exception.WrongPasswordException;
import cinema.model.CinemaRoom;
import cinema.model.Seat;
import cinema.model.Stats;
import cinema.model.Ticket;
import cinema.model.request.Token;
import cinema.model.response.ErrorDTO;
import cinema.model.response.ReturnedTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
public class CinemaController {

    private final CinemaRoom cinemaRoom;
    @Value("${cinema.room.stats.password}")
    private String pass;

    @Autowired
    public CinemaController(CinemaRoom cinemaRoom) {
        this.cinemaRoom = cinemaRoom;
    }

    @GetMapping("/seats")
    public CinemaRoom seats() {
        return cinemaRoom;
    }

    @PostMapping("/purchase")
    public Ticket purchaseTicket(@RequestBody Seat seat) {
        cinemaRoom.sell(seat);
        var token = seat.getToken().toString();
        return new Ticket(token, seat);
    }

    @PostMapping("/return")
    public ReturnedTicket returnTicket(@RequestBody Token token) {
        var seat = cinemaRoom.removeTicket(token.getToken());
        return new ReturnedTicket(seat);
    }

    @PostMapping("/stats")
    public Stats stats(@RequestParam(required = false) String password) {
        if (!Objects.equals(password, pass)) {
            throw new WrongPasswordException();
        }
        return new Stats(
                cinemaRoom.getTotalIncome(),
                cinemaRoom.getAvailableSeats().size(),
                cinemaRoom.getSoldSeats().size()
        );
    }

    @ExceptionHandler(BusinessLogicException.class)
    ResponseEntity<ErrorDTO> handleBusinessLogicException(RuntimeException e) {
        return new ResponseEntity<>(
                new ErrorDTO(e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(WrongPasswordException.class)
    ResponseEntity<ErrorDTO> handleWrongPasswordException(RuntimeException e) {
        return new ResponseEntity<>(
                new ErrorDTO(e.getMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }
}
