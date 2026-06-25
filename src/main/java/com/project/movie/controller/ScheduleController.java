package com.project.movie.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.movie.dto.request.ScheduleRequest;
import com.project.movie.dto.response.ScheduleResponse;
import com.project.movie.service.ScheduleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {
	private final ScheduleService scheduleService;

	@GetMapping
	public ResponseEntity<List<ScheduleResponse>> getAllSchedules() {
		return ResponseEntity.ok(scheduleService.getSchedules());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ScheduleResponse> getScheduleById(@PathVariable Integer id) {
		return ResponseEntity.ok(scheduleService.getScheduleById(id));
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ScheduleResponse> createSchedule(@RequestBody @Valid ScheduleRequest request) {
		return ResponseEntity.status(201).body(scheduleService.createSchedule(request));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ScheduleResponse> updateSchedule(@PathVariable Integer id,
			@RequestBody @Valid ScheduleRequest request) {
		return ResponseEntity.ok(scheduleService.updateSchedule(id, request));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteSchedule(@PathVariable Integer id) {
		scheduleService.deleteSchedule(id);
		return ResponseEntity.noContent().build();
	}
}
