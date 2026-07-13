package com.project.movie.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.movie.dto.response.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	 @ExceptionHandler(NotFoundException.class)
	    public ResponseEntity<ErrorResponse> handleNotFound(
	            NotFoundException e, HttpServletRequest request) {

	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	            .body(ErrorResponse.builder()
	                .status(404)
	                .message(e.getMessage())
	                .path(request.getRequestURI())
	                .timestamp(LocalDateTime.now())
	                .build());
	    }
	 @ExceptionHandler(DuplicateException.class)
	    public ResponseEntity<ErrorResponse> handleDuplicate(
	            DuplicateException e, HttpServletRequest request) {

	        return ResponseEntity.status(HttpStatus.CONFLICT)
	            .body(ErrorResponse.builder()
	                .status(409)
	                .message(e.getMessage())
	                .path(request.getRequestURI())
	                .timestamp(LocalDateTime.now())
	                .build());
	    }
	 @ExceptionHandler(InvalidSeatStatusException.class)
	 public ResponseEntity<ErrorResponse> handleInvalidSeatStatus(
			 InvalidSeatStatusException e, HttpServletRequest request) {
		 
		 return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
				 .body(ErrorResponse.builder()
						 .status(422)
						 .message(e.getMessage())
						 .path(request.getRequestURI())
						 .timestamp(LocalDateTime.now())
						 .build());
	 }
	 @ExceptionHandler(InvalidTicketStatusException.class)
	 public ResponseEntity<ErrorResponse> handleInvalidTicketStatus(
			 InvalidTicketStatusException e, HttpServletRequest request) {
		 
		 return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
				 .body(ErrorResponse.builder()
						 .status(422)
						 .message(e.getMessage())
						 .path(request.getRequestURI())
						 .timestamp(LocalDateTime.now())
						 .build());
	 }
	 @ExceptionHandler(InvalidOperationException.class)
	 public ResponseEntity<ErrorResponse> handleInvalidOperation (
			 InvalidOperationException e, HttpServletRequest request) {
		 
		 return ResponseEntity.status(HttpStatus.CONFLICT)
				 .body(ErrorResponse.builder()
						 .status(409)
						 .message(e.getMessage())
						 .path(request.getRequestURI())
						 .timestamp(LocalDateTime.now())
						 .build());
	 }
	 @ExceptionHandler(Exception.class)
	    public ResponseEntity<ErrorResponse> handleException(
	            Exception e, HttpServletRequest request) {

	        e.printStackTrace();   // log ใน console

	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body(ErrorResponse.builder()
	                .status(500)
	                .message("เกิดข้อผิดพลาดภายในระบบ")
	                .path(request.getRequestURI())
	                .timestamp(LocalDateTime.now())
	                .build());
	    }

}
