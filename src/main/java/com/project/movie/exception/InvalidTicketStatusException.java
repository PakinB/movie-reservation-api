package com.project.movie.exception;

public class InvalidTicketStatusException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public InvalidTicketStatusException(String message) {
		super(message);
	}
}
