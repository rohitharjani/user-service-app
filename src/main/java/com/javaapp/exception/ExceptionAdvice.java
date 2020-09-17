package com.javaapp.exception;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler(value = UserServiceException.class)
	public ResponseEntity<CustomErrorResponse> handleUserServiceException(UserServiceException ex) {
		CustomErrorResponse errorResponse = new CustomErrorResponse(ex.getStatus().name(), ex.getReason());
		errorResponse.setTimestamp(LocalDateTime.now());
		errorResponse.setStatus(ex.getStatus().value());
		return new ResponseEntity<CustomErrorResponse>(errorResponse, ex.getStatus());
	}

}
