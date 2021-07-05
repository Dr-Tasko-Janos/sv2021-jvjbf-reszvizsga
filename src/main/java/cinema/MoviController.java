package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cinema")
public class MoviController {

    private MovieService movieService;

    public MoviController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/")
    public List<MovieDTO> getAll() {
        return movieService.getAllMovies();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDTO createMovieDTO(@RequestBody CreateMovieCommand command) {
        return movieService.createMovie(command);
    }

    @GetMapping
    public MovieDTO listMoviesByTitle(@RequestParam Optional<String> prefix) {
        return movieService.listMoviesByTitle(prefix);
    }

    @GetMapping("/{id}")
    public MovieDTO findMovieById(@PathVariable long id) {
        return movieService.findMovieById(id);
    }

    @PostMapping("/{id}/reserve")
    public MovieDTO bookTicket(@PathVariable long id, @RequestBody CreateReservationCommand command) {
        return movieService.bookTicket(id, command);
    }


    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDTO updateDate(@PathVariable long id, @RequestBody UpdateDateCommand command) {
        return movieService.updateDate(id, command);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllMovies() {
        movieService.deleteAllMovies();
    }

}
