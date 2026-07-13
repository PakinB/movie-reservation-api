package com.project.movie.repository;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.movie.model.EnumTicketStatus;
import com.project.movie.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
	
	List<Ticket> findByUser_id(Integer userId);

	@Query("SELECT t.schedule.scheduleId, t.schedule.movie.movieName, COUNT(t) " +
		   "FROM Ticket t GROUP BY t.schedule.scheduleId, t.schedule.movie.movieName")
	List<Object[]> countTicketBySchedule();
	
	@Query("SELECT SUM(t.price) FROM Ticket t WHERE t.ticketStatus = :status")
	BigDecimal sumRevenueByStatus(@Param("status")EnumTicketStatus status);
	
	List<Ticket> findByTicketStatusAndCreatedAtBefore(EnumTicketStatus status, LocalDateTime time);
	
}
