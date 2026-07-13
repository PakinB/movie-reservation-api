package com.project.movie.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.movie.model.EnumSeatStatus;
import com.project.movie.model.EnumTicketStatus;
import com.project.movie.model.Ticket;
import com.project.movie.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketScheduler {

	private final TicketRepository ticketRepository;

	@Scheduled(fixedRate = 60 * 1000)
	@Transactional
	public void cancelExpiredHolds() {
		
		
		LocalDateTime expiredBefore = LocalDateTime.now().minusMinutes(10);
		
		List<Ticket>expiredTickets = ticketRepository.findByTicketStatusAndCreatedAtBefore(EnumTicketStatus.PENDING, expiredBefore);
		
		expiredTickets.forEach(ticket -> {
			ticket.setTicketStatus(EnumTicketStatus.CANCELLED);
			ticket.getSeat().setSeatStatus(EnumSeatStatus.AVAILABLE);
		});
		
	}
}
