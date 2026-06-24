package com.project.movie.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.movie.dto.request.LoginRequest;
import com.project.movie.dto.request.RegisterRequest;
import com.project.movie.dto.response.JwtResponse;
import com.project.movie.dto.response.MessageResponse;
import com.project.movie.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;


	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody @Valid LoginRequest request) {
		return ResponseEntity.ok(authService.login(request));
	}

	@PostMapping("/register")
	public ResponseEntity<MessageResponse> register(@RequestBody @Valid RegisterRequest request) {
		return ResponseEntity.status(200).body(authService.register(request));
	}
}
