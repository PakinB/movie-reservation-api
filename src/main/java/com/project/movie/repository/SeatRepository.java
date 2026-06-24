package com.project.movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.movie.model.EnumSeatStatus;
import com.project.movie.model.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {
	
	List<Seat> findBySeatNumber(Integer seatNumber);

	List<Seat> findBySeatRow(String seatRow);
	
	List<Seat> findBySeatStatus(EnumSeatStatus seatStatus);
	
	List<Seat> findByScheduleScheduleId(Integer scheduleId);
	List<Seat> findByScheduleScheduleIdAndSeatStatus(Integer scheduleId, EnumSeatStatus seatStatus);

}

