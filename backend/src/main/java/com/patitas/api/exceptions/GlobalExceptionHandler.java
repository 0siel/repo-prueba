package com.patitas.api.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(InvalidStateException.class)
	public ResponseEntity<Map<String, Object>> handleInvalidStateException(InvalidStateException ex){
		return construirRespuesta(ex.getMessage(), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleNotFoundException(ResourceNotFoundException ex){
		return construirRespuesta(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex){
		Map<String, String> errors = new HashMap<>();
		
		ex.getBindingResult().getFieldErrors().forEach(error -> 
		errors.put(error.getField(), error.getDefaultMessage())
		);
		
		Map<String, Object> response = new HashMap<>();
		response.put("timestamp", LocalDateTime.now());
		response.put("status", HttpStatus.BAD_REQUEST.value());
		response.put("errors", errors );
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		
	}
	
	
	
	private ResponseEntity<Map<String, Object>> construirRespuesta(String message, HttpStatus status){
		Map<String, Object> response = new HashMap<>();
		
		response.put("timestamp", LocalDateTime.now());
		response.put("status", status.value());
		response.put("error", message);
		
		return new ResponseEntity<>(response, status);
	}
}
