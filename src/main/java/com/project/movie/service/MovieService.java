package com.project.movie.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.movie.dto.request.MovieRequest;
import com.project.movie.dto.response.MovieResponse;
import com.project.movie.exception.NotFoundException;
import com.project.movie.model.Movie;
import com.project.movie.repository.MovieRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieService {

	private final MovieRepository movieRepository;

	// get movie
	public List<MovieResponse> getAllMovies() {
		return movieRepository.findAll().stream().map(this::toResponse).toList();
	}

	// get movie by id
	public MovieResponse getMovieById(Integer id) {
		Movie movie = movieRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("ไม่พบ Movie ID: " + id));
		return toResponse(movie);
	}

	// create movie
	@Transactional
	public MovieResponse createMovie(MovieRequest request) {
		Movie movie = Movie.builder()
				.movieName(request.getMovieName())
				.movieYear(request.getMovieYear())
				.genre(request.getGenre())
				.description(request.getDescription())
				.movieImage(request.getMovieImage())
				.build();
		
		return toResponse(movieRepository.save(movie));
	}

	// update movie
	@Transactional
	public MovieResponse updateMovie(Integer id, MovieRequest request) {
		Movie movie = movieRepository.findById(id).orElseThrow(() -> new NotFoundException("ไม่พบ Movie ID: " + id));

		movie.setMovieName(request.getMovieName());
		movie.setMovieYear(request.getMovieYear());
		movie.setGenre(request.getGenre());
		movie.setDescription(request.getDescription());
		movie.setMovieImage(request.getMovieImage());

		return toResponse(movieRepository.save(movie));
	}

	// delete movie
	public void deleteMovie(Integer id) {
		if (!movieRepository.existsById(id)) {
			throw new NotFoundException("ไม่พบ Movie ID: " + id);
		}
		movieRepository.deleteById(id);
	}

	private MovieResponse toResponse(Movie movie) {
		return MovieResponse.builder().movieId(movie.getMovieId()).movieName(movie.getMovieName())
				.movieYear(movie.getMovieYear()).genre(movie.getGenre()).description(movie.getDescription())
				.movieImage(movie.getMovieImage()).build();
	}
}
