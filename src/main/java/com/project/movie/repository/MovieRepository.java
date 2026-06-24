package com.project.movie.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.movie.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer>{
	
	Optional<Movie> findByMovieName(String movieName);
	
	List<Movie> findByMovieYear(Integer movieYear);
	
	List<Movie> findByGenre(String genre);
	
	List<Movie> findByDescription(String description);
	
	List<Movie> findByMovieImage(String movieImage);
	
	Boolean existsByMovieName(String movieName);
	
}
