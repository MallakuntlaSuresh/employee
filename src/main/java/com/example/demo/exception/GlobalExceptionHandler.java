package com.example.demo.exception;

import java.sql.Timestamp;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		String errorMessage = "Role values accepted for Enum class: [QA, DEV, EMPLOYEE, ADMIN].";
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		JSONObject errorJson = new JSONObject();
		errorJson.put("error", errorMessage);
		errorJson.put("timestamp", timestamp);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
				.body(errorJson.toString());
	}
}
