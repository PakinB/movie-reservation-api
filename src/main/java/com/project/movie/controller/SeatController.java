package com.project.movie.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.movie.dto.request.SeatRequest;
import com.project.movie.dto.response.SeatResponse;
import com.project.movie.service.SeatService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {

	private final SeatService seatService;

	@GetMapping("/schedules/{id}")
	public ResponseEntity<List<SeatResponse>> getSeats(@PathVariable Integer id) {
		return ResponseEntity.ok(seatService.getSeats(id));
	}

	@PutMapping("/{id}/status")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<SeatResponse> updateSeat(@PathVariable Integer id, @RequestBody @Valid SeatRequest request) {
		return ResponseEntity.ok(seatService.updateSeat(id, request));
	}

}
