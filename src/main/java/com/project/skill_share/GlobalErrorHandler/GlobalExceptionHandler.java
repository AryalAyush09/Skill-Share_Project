package com.project.skill_share.GlobalErrorHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.project.skill_share.response.GenericResponse;

	@ControllerAdvice
	
	public class GlobalExceptionHandler {

	    //  Common validation/duplicate/etc.
//	    @ExceptionHandler(IllegalArgumentException.class)
//	    public ResponseEntity<GenericResponse> handleIllegalArgument(IllegalArgumentException ex) {
//	        return ResponseEntity
//	                .status(HttpStatus.CONFLICT)
//	                .body(new GenericResponse(false, ex.getMessage(), null));
//	    }

	    //  Not found specific (optional)
	    @ExceptionHandler(ResourceNotFoundException.class)
	    public ResponseEntity<GenericResponse> handleNotFound(ResourceNotFoundException ex) {
	        return ResponseEntity
	                .status(HttpStatus.NOT_FOUND)
	                .body(new GenericResponse(false, ex.getMessage(), null));
	    }

	    //  Catch all unexpected exceptions
	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<GenericResponse> handleOtherExceptions(Exception ex) {
	        return ResponseEntity
	                .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new GenericResponse(false, "Something went wrong: " + ex.getMessage(), null));
	    }
	    
	    @ExceptionHandler(AlreadyExistsException.class)
	    public ResponseEntity<GenericResponse> handleAlreadyExists(AlreadyExistsException ex) {
	        return ResponseEntity
	                .status(HttpStatus.CONFLICT)
	                .body(new GenericResponse(false, ex.getMessage(), null));
	    }

	}


