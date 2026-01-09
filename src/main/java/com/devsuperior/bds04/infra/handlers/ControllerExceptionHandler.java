package com.devsuperior.bds04.infra.handlers;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.bds04.infra.exceptions.CustomError;
import com.devsuperior.bds04.infra.exceptions.ValidationError;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<CustomError> methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		ValidationError err = new ValidationError(Instant.now(), status.value(), "Dados invalídos.", request.getRequestURI());
		for (FieldError f : e.getBindingResult().getFieldErrors()) {
			err.addErrros(f.getField(), f.getDefaultMessage());
		}
		return ResponseEntity.status(status).body(err);
	}
}
