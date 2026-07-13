package com.project.movie.exception;

public class InvalidSeatStatusException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public InvalidSeatStatusException(String message) {
		super(message);
	}
}
