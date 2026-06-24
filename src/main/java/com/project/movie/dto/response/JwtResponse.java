package com.project.movie.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
	private String token;
	@Builder.Default
	private String type = "Bearer ";
	private Integer id;
	private String email;
	private String username;
	private List<String> roles;	
}
