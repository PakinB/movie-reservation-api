package com.project.movie.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
	private int status;
	private String message;
	private String path;
	private LocalDateTime timestamp;
}
