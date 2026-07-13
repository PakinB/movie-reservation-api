package com.project.movie.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.movie.dto.request.TicketRequest;
import com.project.movie.dto.response.TicketResponse;
import com.project.movie.exception.InvalidOperationException;
import com.project.movie.exception.InvalidSeatStatusException;
import com.project.movie.exception.InvalidTicketStatusException;
import com.project.movie.exception.NotFoundException;
import com.project.movie.model.EnumSeatStatus;
import com.project.movie.model.EnumTicketStatus;
import com.project.movie.model.Schedule;
import com.project.movie.model.Seat;
import com.project.movie.model.Ticket;
import com.project.movie.model.User;
import com.project.movie.repository.ScheduleRepository;
import com.project.movie.repository.SeatRepository;
import com.project.movie.repository.TicketRepository;
import com.project.movie.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {

	private final TicketRepository ticketRepository;
	private final UserRepository userRepository;
	private final SeatRepository seatRepository;
	private final ScheduleRepository scheduleRepository;

	// get ticket
	public List<TicketResponse> getMyTicket(Integer userId) {
		return ticketRepository.findByUser_id(userId).stream().map(this::toResponse).toList();
	}

	// create ticket
	@Transactional
	public TicketResponse createTicket(TicketRequest request, Integer id) {

		User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("ไม่พบ User ID: " + id));
		Seat seat = seatRepository.findByIdWithLock(request.getSeatId())
				.orElseThrow(() -> new NotFoundException("ไม่พบ Seat ID: " + request.getSeatId()));
				if(seat.getSeatStatus() != EnumSeatStatus.AVAILABLE) {
					throw new InvalidSeatStatusException("ที่นั่ง ID: " + seat.getSeatId() + "ไม่ว่าง");
				}
		Schedule schedule = scheduleRepository.findById(request.getScheduleId())
				.orElseThrow(() -> new NotFoundException("ไม่พบ Schedule ID: " + request.getScheduleId()));

		Ticket ticket = Ticket.builder().user(user).seat(seat).schedule(schedule).price(request.getPrice())
				.ticketStatus(EnumTicketStatus.CONFIRMED).build();

		
		Ticket saved = ticketRepository.save(ticket);
		
		seat.setSeatStatus(EnumSeatStatus.BOOKED);
		seatRepository.save(seat);

		return toResponse(saved);

	}
	
	@Transactional
	public TicketResponse holdSeat(TicketRequest request, Integer id) {
		User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("ไม่พบ User ID: " + id));
		Seat seat = seatRepository.findByIdWithLock(request.getSeatId())
				.orElseThrow(() -> new NotFoundException("ไม่พบ Seat ID: " + request.getSeatId()));
				if(seat.getSeatStatus() != EnumSeatStatus.AVAILABLE) {
					throw new InvalidSeatStatusException("ที่นั่ง ID: " + seat.getSeatId() + "ไม่ว่าง");
				}
		Schedule schedule = scheduleRepository.findById(request.getScheduleId())
				.orElseThrow(() -> new NotFoundException("ไม่พบ Schedule ID: " + request.getScheduleId()));

		Ticket ticket = Ticket.builder().user(user).seat(seat).schedule(schedule).price(request.getPrice())
				.ticketStatus(EnumTicketStatus.PENDING).build();

		
		Ticket saved = ticketRepository.save(ticket);
		
		seat.setSeatStatus(EnumSeatStatus.RESERVED);
		seatRepository.save(seat);

		return toResponse(saved);
	
	}

	@Transactional
	public void deleteTicket(Integer id, Integer userId) {
		Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new NotFoundException("ไม่พบ Ticket ID: " + id));
		
		if(!ticket.getUser().getId().equals(userId)) {
			throw new NotFoundException("ไม่พบ Ticket ID ที่ตรงกับ User ID: " + userId);
		}

		ticketRepository.deleteById(id);
	}
	
	@Transactional
	public void cancelTicket(Integer id, Integer userId) {
		Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new NotFoundException("ไม่พบ Ticket ID: " + id));
		Seat seat = ticket.getSeat();
		LocalDateTime showDateTime = LocalDateTime.of(ticket.getSchedule().getStartDate(), ticket.getSchedule().getShowTime());
		
		
		if(!ticket.getUser().getId().equals(userId)) {
			throw new NotFoundException("ไม่พบ Ticket ID ที่ตรงกับ User ID: " + userId);
		}
		if(LocalDateTime.now().isAfter(showDateTime)) {
			throw new InvalidOperationException("ไม่สามารถยกเลิกได้ ภาพยนตร์เริ่มฉายไปแล้ว");
		}
		if(ticket.getTicketStatus() != EnumTicketStatus.CONFIRMED) {
			throw new InvalidTicketStatusException("ไม่สามารถ cancel ticket status: " + ticket.getTicketStatus() + "ได้");
		}
		if(seat.getSeatStatus() != EnumSeatStatus.BOOKED) {
			throw new InvalidTicketStatusException("ไม่สามารถ cancel seat status: " + seat.getSeatStatus() + "ได้");
		}
		
		
		
		ticket.setTicketStatus(EnumTicketStatus.CANCELLED);
		ticketRepository.save(ticket);
		
		seat.setSeatStatus(EnumSeatStatus.AVAILABLE);
		seatRepository.save(seat);
		
		
	}

	public TicketResponse toResponse(Ticket ticket) {
		return TicketResponse.builder().ticketId(ticket.getTicketId()).userId(ticket.getUser().getId())
				.seatId(ticket.getSeat().getSeatId()).scheduleId(ticket.getSchedule().getScheduleId())
				.price(ticket.getPrice()).ticketStatus(ticket.getTicketStatus()).build();
	}
}
