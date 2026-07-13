package com.project.movie.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.movie.dto.request.TicketRequest;
import com.project.movie.dto.response.TicketResponse;
import com.project.movie.security.UserDetailsImpl;
import com.project.movie.service.TicketService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

	private final TicketService ticketService;

	@GetMapping("/my")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<List<TicketResponse>> getMyTicket(
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return ResponseEntity.ok(ticketService.getMyTicket(userDetails.getId()));
	}

	@PostMapping("/book")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<TicketResponse> createTicket(@RequestBody @Valid TicketRequest request,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return ResponseEntity.status(201).body(ticketService.createTicket(request, userDetails.getId()));
	}
	
	@PostMapping("/hold")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<TicketResponse> holdSeat(@RequestBody @Valid TicketRequest request,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return ResponseEntity.status(201).body(ticketService.holdSeat(request, userDetails.getId()));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<Void> deleteTicket(@PathVariable Integer id, @AuthenticationPrincipal UserDetailsImpl userDetails) {

		ticketService.deleteTicket(id, userDetails.getId());

		return ResponseEntity.noContent().build();
	}
	@PatchMapping("/{id}/cancel")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<Void> cancelTicket(@PathVariable Integer id,@AuthenticationPrincipal UserDetailsImpl userDetails){
		
		ticketService.cancelTicket(id, userDetails.getId());
		
		return ResponseEntity.noContent().build();
	}

}
