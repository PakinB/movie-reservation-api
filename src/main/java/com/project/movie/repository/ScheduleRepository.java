package com.project.movie.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.movie.model.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer>{
	
	List<Schedule> findByStartDate(LocalDate startDate);
	
	List<Schedule> findByEndDate(LocalDate endDate);
	
	List<Schedule> findByShowTime(LocalTime showTime);

	List<Schedule> findByAvailableSeats(Integer availableSeats);

	List<Schedule> findByMovieMovieId(Integer movieId);
	
}
