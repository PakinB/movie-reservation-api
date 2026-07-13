package com.project.movie.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.movie.dto.response.BookingReportResponse;
import com.project.movie.dto.response.CapacityReportResponse;
import com.project.movie.dto.response.RevenueReportResponse;
import com.project.movie.model.EnumSeatStatus;
import com.project.movie.model.EnumTicketStatus;
import com.project.movie.model.Seat;
import com.project.movie.repository.ScheduleRepository;
import com.project.movie.repository.SeatRepository;
import com.project.movie.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
	
	private final TicketRepository ticketRepository;
	private final ScheduleRepository scheduleRepository;
	private final SeatRepository seatRepository;
	
	
	
	public List<BookingReportResponse> getBookingReport() {
		return ticketRepository.countTicketBySchedule().stream().map(this::toBookingResponse).toList();
	}
	
	public List<CapacityReportResponse> getCapacityReport() {
		return scheduleRepository.findAll()
				.stream()
				.map(schedule -> toCapacityResponse(schedule.getScheduleId()))
				.toList();
	}
	public RevenueReportResponse getRevenueReport() {
		BigDecimal sumRevenue = ticketRepository.sumRevenueByStatus(EnumTicketStatus.CONFIRMED);
		if(sumRevenue == null) sumRevenue = BigDecimal.ZERO;
		return toRevenueResponse(sumRevenue);
	}
	
	private BookingReportResponse toBookingResponse(Object[] row) {
		Integer scheduleId = (Integer) row[0];
		String movieTitle = (String) row[1];
		Long count = (Long) row[2];
		return BookingReportResponse.builder()
				.scheduleId(scheduleId)
				.movieTitle(movieTitle)
				.totalBookings(count.intValue())
				.build();
	}
	private CapacityReportResponse toCapacityResponse(Integer scheduleId) {
		
		List<Seat> allSeats = seatRepository.findByScheduleScheduleId(scheduleId);
		List<Seat> bookedSeats = seatRepository.findByScheduleScheduleIdAndSeatStatus(scheduleId, EnumSeatStatus.BOOKED);
		
		int total = allSeats.size();
		int booked = bookedSeats.size();
		
		//calculate percent
		String percent = total == 0 ? "0%":
			String.format("%.1f%%", (booked * 100.0) / total);
		
		
		return CapacityReportResponse.builder()
				.scheduleId(scheduleId)
				.totalSeats(total)
				.bookedSeats(booked)
				.utilizationPercent(percent)
				.build();
	}
	private RevenueReportResponse toRevenueResponse(BigDecimal sumRevenue) {
		return RevenueReportResponse.builder()
				.totalRevenue(sumRevenue)
				.build();
	}
	
}
