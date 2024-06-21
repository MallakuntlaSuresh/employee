package com.example.demo.controller;

import java.security.SignatureException;
import java.time.LocalDateTime;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.EmpLogin;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Role;
import com.example.demo.entity.Student;
import com.example.demo.jwt.JwtUtils;
import com.example.demo.repo.EmployeeRepository;
import com.example.demo.repo.StudentRpo;
import com.example.demo.service.EmployeeService;

@RestController
public class LoginController {
	@Autowired
	StudentRpo rpo;
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	private EmployeeService employeeService;
	private JwtUtils jwtUtils;
	private EmployeeRepository repository;

	public LoginController(EmployeeService employeeService, JwtUtils jwtUtils, EmployeeRepository repository) {
		this.employeeService = employeeService;
		this.jwtUtils = jwtUtils;
		this.repository = repository;
	}

	@PostMapping("/student")
	public Student ss(@RequestBody Student errorMessage) {
		return rpo.save(errorMessage);
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody EmpLogin empLogin) throws SignatureException {
	    LocalDateTime now = LocalDateTime.now();

	    logger.info("Received login request for email: {}", empLogin.getEmail());

	    JSONObject responseJson = new JSONObject();

	    if (isNullOrEmpty(empLogin.getEmail())) {
	        return createErrorResponse(now, "Email cannot be Empty or Null", HttpStatus.BAD_REQUEST);
	    }

	    if (isNullOrEmpty(empLogin.getPassword())) {
	        return createErrorResponse(now, "Password cannot be Empty or Null", HttpStatus.BAD_REQUEST);
	    }

	    Employee employee = employeeService.emailPwd(empLogin.getEmail(), empLogin.getPassword());

	    if (employee == null) {
	        return createErrorResponse(now, "Invalid Email or Password", HttpStatus.UNAUTHORIZED);
	    }

	    if (isFirstPasswordExpired(now, employee)) {
	        return createErrorResponse(now, "Password is Expired", HttpStatus.UNAUTHORIZED);
	    }

	    String id = employee.getEmployeeId();
	    Role role = employee.getRole();
	    String token = jwtUtils.generateAccessToken(id, role);

	    responseJson.put("TimeStamp", now);
	    responseJson.put("token", token);
	    responseJson.put("status", "Login Successful");
	    employee.setToken(token);
	    repository.save(employee);

	    logger.info("Login successful for email: {}", empLogin.getEmail());
	    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(responseJson.toString());
	}


	private ResponseEntity<String> createErrorResponse(LocalDateTime now, String errorMessage, HttpStatus httpStatus) {
		JSONObject errorJson = new JSONObject();
		errorJson.put("TimeStamp", now);
		errorJson.put("status", errorMessage);

		logger.warn(errorMessage);
		return ResponseEntity.status(httpStatus).contentType(MediaType.APPLICATION_JSON).body(errorJson.toString());
	}

	private boolean isNullOrEmpty(String str) {
		return str == null || str.isEmpty();
	}

	private boolean isFirstPasswordExpired(LocalDateTime currentTime, Employee employee) {
		LocalDateTime firstPwdExp = employee.getFirstPwdExp();
		return !currentTime.isBefore(firstPwdExp) && employee.getPwdIschanged() == 0;
	}
}
