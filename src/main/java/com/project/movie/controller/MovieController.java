package com.project.movie.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.movie.dto.request.MovieRequest;
import com.project.movie.dto.response.MovieResponse;
import com.project.movie.service.MovieService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

	private final MovieService movieService;

	@GetMapping
	public ResponseEntity<List<MovieResponse>> getAllMovies() {
		return ResponseEntity.ok(movieService.getAllMovies());
	}

	@GetMapping("/{id}")
	public ResponseEntity<MovieResponse> getMovieById(@PathVariable Integer id) {
		return ResponseEntity.ok(movieService.getMovieById(id));
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MovieResponse> createMovie(@RequestBody @Valid MovieRequest request) {
		return ResponseEntity.status(201).body(movieService.createMovie(request));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MovieResponse> updateMovie(@PathVariable Integer id,
			@RequestBody @Valid MovieRequest request) {
		return ResponseEntity.ok(movieService.updateMovie(id, request));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteMovie(@PathVariable Integer id) {
		movieService.deleteMovie(id);
		return ResponseEntity.noContent().build();
	}

}
