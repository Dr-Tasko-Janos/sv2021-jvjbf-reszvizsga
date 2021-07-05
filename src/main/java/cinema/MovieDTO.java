package cinema;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MovieDTO {

    private int id;
    private String title;
    private LocalDateTime date;
    private int maxReservation;
    private int freeSpaces;
}
