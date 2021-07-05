package cinema;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private ModelMapper modelMapper;

    AtomicLong idGenerator = new AtomicLong();

    public MovieService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    private List<Movie> movies = Collections.synchronizedList(new ArrayList<>());


    public List<MovieDTO> getAllMovies() {
        Type targetListType = new TypeToken<List<MovieDTO>>() {
        }.getType();
        return modelMapper.map(movies, targetListType);
    }


    public MovieDTO createMovie(CreateMovieCommand command) {
        Movie movie = new Movie(idGenerator.incrementAndGet(), command.getTitle(), command.getDate(), command.getMaxReservation());
        movies.add(movie);
        return modelMapper.map(movie, MovieDTO.class);
    }


    public MovieDTO listMoviesByTitle(Optional<String> prefix) {
        Type targetListType = new TypeToken<List<MovieDTO>>() {
        }.getType();

        Movie movieFilteredByTitle = movies.stream().filter(e -> prefix.isEmpty() || e.getTitle().toLowerCase().equals(prefix.get().toLowerCase())).findAny().get();

        Movie movieToReturn = new Movie(movieFilteredByTitle.getTitle(), movieFilteredByTitle.getDate(), movieFilteredByTitle.getFreeSpaces());

        return modelMapper.map(movieToReturn, MovieDTO.class);
    }

    public MovieDTO findMovieById(long id) {
        return modelMapper.map(movies.stream().filter(e -> e.getId() == id)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Employee not found " + id)), MovieDTO.class);
    }

    public MovieDTO bookTicket(long id, CreateReservationCommand command) {
        Movie movie = movies.stream()
                .filter(e ->e.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Movie not found: " + id));
        movie.reservationChecker(command.getSpaces());
        return modelMapper.map(movie, MovieDTO.class);
    }



    public MovieDTO updateDate(long id, UpdateDateCommand command) {
        Movie movie = movies.stream()
                .filter(e ->e.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Movie not found: " + id));
        movie.setDate(command.getNewDate());
        return modelMapper.map(movie, MovieDTO.class);
    }

    public void deleteAllMovies() {
        idGenerator = new AtomicLong();

        movies.clear();
    }
}
