package com.gplayer.fusionmovies.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import com.gplayer.fusionmovies.domain.genre.GenreService;
import com.gplayer.fusionmovies.domain.genre.dto.GenreDto;
import com.gplayer.fusionmovies.domain.movie.MovieService;
import com.gplayer.fusionmovies.domain.movie.dto.MovieDto;
import java.util.List;

@Controller
public class GenreController {
    private final GenreService genreService;
    private final MovieService movieService;

    public GenreController(GenreService genreService, MovieService movieService) {
        this.genreService = genreService;
        this.movieService = movieService;
    }

    @GetMapping("/gatunek/{name}")
    public String getGenre(@PathVariable String name, Model model) {
        GenreDto genre = genreService.findGenreByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<MovieDto> movies = movieService.findMoviesByGenreName(name);
        model.addAttribute("heading", genre.getName());
        model.addAttribute("description", genre.getDescription());
        model.addAttribute("movies", movies);
        return "movie-listing";
    }

    @GetMapping("/gatunki-filmowe")
    public String getGenreList(Model model) {
        List<GenreDto> genres = genreService.findAllGenres();
        model.addAttribute("genres", genres);
        return "genre-listing";
    }
}
