package com.project.movie.exception;

public class InvalidOperationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidOperationException(String message) {
		super(message);
	}
}
