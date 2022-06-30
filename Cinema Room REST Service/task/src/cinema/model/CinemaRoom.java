package cinema.model;

import cinema.exception.RowOrColumnNumberOutOfBoundsException;
import cinema.exception.SeatAlreadySoldException;
import cinema.exception.WrongTokenException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;

public class CinemaRoom {
    private int totalRows;
    private int totalColumns;
    private int totalIncome;
    private List<Seat> availableSeats;
    private Set<Seat> soldSeats;
    private Map<String, Seat> tokenToSoldSeatMap;

    public CinemaRoom() {
    }

    public CinemaRoom(int totalRows, int totalColumns) {
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
        soldSeats = new HashSet<>();
        availableSeats = new ArrayList<>();
        tokenToSoldSeatMap = new HashMap<>();
        for (int row = 1; row <= totalRows; row++) {
            for (int col = 1; col <= totalColumns; col++) {
                availableSeats.add(new Seat(row, col));
            }
        }
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public void setTotalColumns(int totalColumns) {
        this.totalColumns = totalColumns;
    }

    public List<Seat> getAvailableSeats() {
        return availableSeats;
    }

    @JsonIgnore
    public Set<Seat> getSoldSeats() {
        return soldSeats;
    }

    @JsonIgnore
    public Map<String, Seat> getTokenToSoldSeatMap() {
        return tokenToSoldSeatMap;
    }

    @JsonIgnore
    public int getTotalIncome() {
        return totalIncome;
    }

    public void sell(Seat seat) {
        synchronized (this) {
            if (
                    seat.getRow() < 1 || seat.getRow() > totalRows ||
                            seat.getColumn() < 1 || seat.getColumn() > totalColumns
            ) {
                throw new RowOrColumnNumberOutOfBoundsException();
            }
            if (soldSeats.contains(seat)) {
                throw new SeatAlreadySoldException();
            }
            soldSeats.add(seat);
            availableSeats.remove(seat);
            var token = generateToken(seat).toString();
            tokenToSoldSeatMap.put(token, seat);
            totalIncome += seat.getPrice();
        }
    }

    public String generateToken(Seat seat) {
        var token = UUID.randomUUID().toString();
        seat.setToken(token);
        return token;
    }

    public Seat removeTicket(String token) {
        synchronized (this) {
            if (!tokenToSoldSeatMap.containsKey(token)) {
                throw new WrongTokenException();
            }
            Seat seat = tokenToSoldSeatMap.get(token);
            soldSeats.remove(seat);
            availableSeats.add(seat);
            tokenToSoldSeatMap.remove(token);
            totalIncome -= seat.getPrice();
            return seat;
        }
    }
}
