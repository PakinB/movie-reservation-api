package com.project.movie.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.movie.model.EnumTicketStatus;
import com.project.movie.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
	
	Optional<Ticket> findByUserId(Integer id);
	
	List<Ticket> findBySeatSeatId(Integer seatId);

	List<Ticket> findByScheduleScheduleId(Integer scheduleId);
	
	List<Ticket> findByPrice(BigDecimal price);
	
	List<Ticket> findByTicketStatus(EnumTicketStatus ticketStatus);
	
}
