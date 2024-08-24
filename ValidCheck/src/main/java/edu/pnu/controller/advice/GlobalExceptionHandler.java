package edu.pnu.controller.advice;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> exception(MethodArgumentNotValidException e) {

		if (e.getErrorCount() == 1) {
			System.out.println(e.getFieldError().getDefaultMessage());
			return ResponseEntity.badRequest().body(e.getFieldError().getDefaultMessage());			
		}
		List<ObjectError> errlist = e.getAllErrors();
		for (ObjectError o : errlist) {
			System.out.println(o.getDefaultMessage());
		}
		return ResponseEntity.badRequest().body(errlist);
	}
	
	@ExceptionHandler(Exception.class)
	public String exception(Exception e) {
		return e.getMessage();
	}
}
