package com.javaapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserServiceException extends ResponseStatusException {
	
	/**
	 * The default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * Constructor for UserServiceException
     * @param status the HttpStatus
     */
    public UserServiceException(HttpStatus status) {
    	super(status);
    }
	
	/**
     * Constructor for UserServiceException
     * @param status the HttpStatus
     * @param message the exception message
     */
    public UserServiceException(HttpStatus status, String message) {
    	super(status, message);
    }
    
    /**
     * Constructor for UserServiceException
     * @param status the HttpStatus
     * @param message the exception message
     * @param cause the Throwable
     */
    public UserServiceException(HttpStatus status, String message, Throwable cause) {
    	super(status, message, cause);
    }

}
