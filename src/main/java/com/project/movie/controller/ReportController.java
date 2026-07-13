package com.project.movie.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.movie.dto.response.BookingReportResponse;
import com.project.movie.dto.response.CapacityReportResponse;
import com.project.movie.dto.response.RevenueReportResponse;
import com.project.movie.service.ReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/reports")
@RequiredArgsConstructor
public class ReportController {
	
	private final ReportService reportService;
	
	@GetMapping("/bookings")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity <List<BookingReportResponse>> getBookReport(){
		return ResponseEntity.ok(reportService.getBookingReport());
	}
	
	@GetMapping("/capacity")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity <List<CapacityReportResponse>> getCapacityReport(){
		return ResponseEntity.ok(reportService.getCapacityReport());
	}
	
	@GetMapping("/revenue")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity <RevenueReportResponse> getRevenueReport() {
		return ResponseEntity.ok(reportService.getRevenueReport());
	}
	
}
