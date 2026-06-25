package com.project.movie.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.movie.dto.request.SeatRequest;
import com.project.movie.dto.response.SeatResponse;
import com.project.movie.exception.NotFoundException;
import com.project.movie.model.Schedule;
import com.project.movie.model.Seat;
import com.project.movie.repository.ScheduleRepository;
import com.project.movie.repository.SeatRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeatService {

	private final SeatRepository seatRepository;
	private final ScheduleRepository scheduleRepository;

	// get seat by schedule id
	@Transactional
	public List<SeatResponse> getSeats(Integer id) {
		return seatRepository.findByScheduleScheduleId(id).stream().map(this::toResponse).toList();
	}

	// update seat
	@Transactional
	public SeatResponse updateSeat(Integer id, SeatRequest request) {
		Schedule schedule = scheduleRepository.findById(request.getScheduleId())
				.orElseThrow(() -> new NotFoundException("ไม่พบ Schedule ID: " + id));

		Seat seat = seatRepository.findById(id).orElseThrow(() -> new NotFoundException("ไม่พบ Seat ID: " + id));

		seat.setSeatNumber(request.getSeatNumber());
		seat.setSeatRow(request.getSeatRow());
		seat.setSeatStatus(request.getSeatStatus());
		seat.setSchedule(schedule);
		
		return toResponse(seatRepository.save(seat));
	}

	public SeatResponse toResponse(Seat seat) {
		return SeatResponse.builder().seatId(seat.getSeatId()).seatNumber(seat.getSeatNumber())
				.seatRow(seat.getSeatRow()).seatStatus(seat.getSeatStatus())
				.scheduleId(seat.getSchedule().getScheduleId()).build();
	}
}
