package com.project.movie.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.movie.model.EnumSeatStatus;
import com.project.movie.model.Seat;

import jakarta.persistence.LockModeType;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT s FROM Seat s WHERE s.seatId = :seatId")
	Optional<Seat> findByIdWithLock(@Param("seatId")Integer seatId);
	
	List<Seat> findBySeatNumber(Integer seatNumber);

	List<Seat> findBySeatRow(String seatRow);
	
	List<Seat> findBySeatStatus(EnumSeatStatus seatStatus);
	
	List<Seat> findByScheduleScheduleId(Integer scheduleId);
	List<Seat> findByScheduleScheduleIdAndSeatStatus(Integer scheduleId, EnumSeatStatus seatStatus);

}

