package com.project.movie.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.movie.dto.request.LoginRequest;
import com.project.movie.dto.request.RegisterRequest;
import com.project.movie.dto.response.JwtResponse;
import com.project.movie.dto.response.MessageResponse;
import com.project.movie.exception.DuplicateException;
import com.project.movie.model.EnumRole;
import com.project.movie.model.User;
import com.project.movie.repository.UserRepository;
import com.project.movie.security.JwtProvider;
import com.project.movie.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;
	
	//login
	public JwtResponse login(LoginRequest request) {
		Authentication auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
					request.getEmail(),
					request.getPassword()
					)
				);
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) auth.getPrincipal();
		
		String role = userDetailsImpl.getAuthorities()
				.iterator().next()
				.getAuthority()
				.replace("ROLE_", "");

		
		String token = jwtProvider.generateToken(
				userDetailsImpl.getEmail(), role); 
		
		
		User user = userRepository.findByEmail(userDetailsImpl.getEmail())
				.orElseThrow();
		
		return JwtResponse.builder()
				.token(token)
				.id(user.getId())
				.email(user.getEmail())
				.username(user.getUsername())
				.roles(List.of(role))
				.build();
	}
	
	//register
	public MessageResponse register(RegisterRequest request) {
		if(userRepository.existsByEmail(request.getEmail())) {
			throw new DuplicateException("Email is already exits");
		}
		
		User user = User.builder()
				.username(request.getUsername())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(EnumRole.USER)
				.build();
		
		userRepository.save(user);
		
		return new MessageResponse("Resgister successfully");
	}

}
