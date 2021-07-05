package cinema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    private long id;
    private String title;
    private LocalDateTime date;
    private int maxReservation;
    private int freeSpaces;

    public Movie(String title, LocalDateTime movieTime, int maxReservation) {
        if(maxReservation < 20) {
            throw new IllegalArgumentException("MaxReservation number must be equal or larger than 20 and the given value is: " + maxReservation);
        }
        this.title = title;
        this.date = date;
        this.maxReservation = maxReservation;
        this.freeSpaces = maxReservation;
    }
    public Movie(long id, String title, LocalDateTime movieTime, int maxReservation) {
        if(maxReservation < 20) {
            throw new IllegalArgumentException("MaxReservation number must be equal or larger than 20 and the given value is: " + maxReservation);
        }
        this.id = id;
        this.title = title;
        this.date = date;
        this.maxReservation = maxReservation;
        this.freeSpaces = maxReservation;
    }

    public Movie(String title, LocalDateTime date) {
        this.title = title;
        this.date = date;
    }

    public void reservationChecker(int reservationNumber) {
        if (reservationNumber >freeSpaces) {
            throw new IllegalStateException(String.format("Available free space is: %d vs. requested reservation number is: %d", freeSpaces, reservationNumber));
        } else {
            this.freeSpaces = freeSpaces - reservationNumber;
        }
    }
}