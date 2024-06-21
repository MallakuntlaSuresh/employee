package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.PwdDto;
import com.example.demo.entity.Employee;
import com.example.demo.jwt.JwtUtils;
import com.example.demo.service.EmployeeService;

@RestController
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	EmployeeService employeeService;
	JwtUtils jwtUtils;

	public UserController(EmployeeService employeeService, JwtUtils jwtUtils) {
		this.employeeService = employeeService;
		this.jwtUtils = jwtUtils;
	}


	@PostMapping("/updatepwd")
	public ResponseEntity<String> updatePassword(@RequestHeader("token") String token, @RequestBody PwdDto pwdDto) {
		LocalDateTime now = LocalDateTime.now();
		JSONObject jsonObject = new JSONObject();
		String pwd = pwdDto.getPwd();
		String confirmPwd = pwdDto.getConfirmPwd();
		if (pwd == null || pwd.isEmpty()) {
			return createErrorResponse("Password Should Not be Empty or Null", now);
		}
		if (pwd.length() < 8) {
			return createErrorResponse("Password Should Have Minimum Length 8 Characters", now);
		}
		if (confirmPwd == null || confirmPwd.isEmpty()) {
			return createErrorResponse("Confirm Password Should Not be Empty or Null", now);
		}
		if (!confirmPwd.equals(pwd)) {
			return createErrorResponse("Confirm Password Must Be Same As Password", now);
		}

		// Validate token
		Map<String, String> validateToken = jwtUtils.validateToken(token);
		if (validateToken.containsKey("error")) {
			return createErrorResponse(validateToken.get("error"), now);
		}

		String id = validateToken.get("id");

		// Check if the token exists in the database
		Employee findByEmployeeId = employeeService.findByEmployeeId(id);
		String dbToken = findByEmployeeId.getToken();
		if (dbToken == null || dbToken.isEmpty()) {
			return createErrorResponse("No token found in the database for the user", now);
		}

		// Compare the token provided in the request header with the one stored in the
		// database
		if (!dbToken.equals(token)) {
			return createErrorResponse("Invalid token", now);
		}

		// Update password
		Integer updatePwd = employeeService.updatePwd(confirmPwd, id);
		if (updatePwd > 0) {
			logger.info("Password updated successfully for user with Id: {}", id);
			jsonObject.put("status", "Password Updated Successfully");
		} else {
			logger.error("Failed to update password for user with Id: {}", id);
			jsonObject.put("status", "Something happened while updating password");
		}
		jsonObject.put("timestamp", now);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(jsonObject.toString());
	}

	private ResponseEntity<String> createErrorResponse(String errorMessage, LocalDateTime timestamp) {
		JSONObject errorJson = new JSONObject();
		errorJson.put("status", errorMessage);
		errorJson.put("timestamp", timestamp);
		logger.warn(errorMessage);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
				.body(errorJson.toString());
	}

	@GetMapping("/employees")
	public ResponseEntity<String> showAllEmployees(@RequestHeader("token") String token) {
		LocalDateTime now = LocalDateTime.now();
		Map<String, String> validateToken = jwtUtils.validateToken(token);
		if (validateToken.containsKey("error")) {
			return createErrorResponse(validateToken.get("error"), now);
		}
		String id = validateToken.get("id");
		// Check if the token exists in the database
		Employee findByEmployeeId = employeeService.findByEmployeeId(id);
		String dbToken = findByEmployeeId.getToken();
		if (dbToken == null || dbToken.isEmpty()) {
			return createErrorResponse("No token found in the database for the user", now);
		}
		// Compare the token provided in the request header with the one stored in the
		// database
		if (!dbToken.equals(token)) {
			return createErrorResponse("Invalid token", now);
		}

		List<Employee> employees = employeeService.findAll();
		if (employees.isEmpty()) {
			return createErrorResponse("No employees found", now);
		}
		List<EmployeeDto> employeeDtos = employees.stream().map(employee -> new EmployeeDto(employee.getEmpName(),
				employee.getDesignature(), employee.getEmpAge(), employee.getEmail())).collect(Collectors.toList());
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("status", "success");
		jsonResponse.put("message", "Your Team");
		jsonResponse.put("employees", employeeDtos);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(jsonResponse.toString());
	}

	@PostMapping("/forgetpassword")
	public ResponseEntity<String> forgetPwd(@RequestHeader("token") String token, @RequestBody PwdDto pwdDto) {
		LocalDateTime now = LocalDateTime.now();
		JSONObject jsonObject = new JSONObject();
		String pwd = pwdDto.getPwd();
		String confirmPwd = pwdDto.getConfirmPwd();
		if (pwd == null || pwd.isEmpty()) {
			return createErrorResponse("Password Should Not be Empty or Null", now);
		}
		if (pwd.length() < 8) {
			return createErrorResponse("Password Should Have Minimum Length 8 Characters", now);
		}
		if (confirmPwd == null || confirmPwd.isEmpty()) {
			return createErrorResponse("Confirm Password Should Not be Empty or Null", now);
		}
		if (!confirmPwd.equals(pwd)) {
			return createErrorResponse("Confirm Password Must Be Same As Password", now);
		}

		// Validate token
		Map<String, String> validateToken = jwtUtils.validateToken(token);
		if (validateToken.containsKey("error")) {
			return createErrorResponse(validateToken.get("error"), now);
		}

		String id = validateToken.get("id");

		// Check if the token exists in the database
		Employee findByEmployeeId = employeeService.findByEmployeeId(id);
		String dbToken = findByEmployeeId.getToken();
		if (dbToken == null || dbToken.isEmpty()) {
			return createErrorResponse("No token found in the database for the user", now);
		}

		// Compare the token provided in the request header with the one stored in the
		// database
		if (!dbToken.equals(token)) {
			return createErrorResponse("Invalid token", now);
		}
		Integer updatePwd = employeeService.updatePwd(confirmPwd, id);
		if (updatePwd > 0) {
			logger.info("Password updated successfully for user with Id: {}", validateToken);
			jsonObject.put("status", "Password Updated Successfully");
		} else {
			logger.error("Failed to update password for user with Id: {}", validateToken);
			jsonObject.put("status", "Something happened while updating password");
		}
		jsonObject.put("timestamp", now);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(jsonObject.toString());
	}

	@PostMapping("/assignproject")
	public ResponseEntity<String> assignProject(@RequestHeader("token") String token, @RequestBody String project) {
		LocalDateTime now = LocalDateTime.now();
		JSONObject request = new JSONObject(project);
		String projects = request.getString("project");
		JSONObject jsonObject = new JSONObject();
		Map<String, String> validateToken = jwtUtils.validateToken(token);
		if (validateToken.containsKey("error")) {
			return createErrorResponse(validateToken.get("error"), now);
		}
		String id = validateToken.get("id");
		String role = validateToken.get("role");
		// Check if the token exists in the database
		Employee findByEmployeeId = employeeService.findByEmployeeId(id);
		String dbToken = findByEmployeeId.getToken();
		if (dbToken == null || dbToken.isEmpty()) {
			return createErrorResponse("No token found in the database for the user", now);
		}
		// Compare the token provided in the request header with the one stored in the
		// database
		if (!dbToken.equals(token)) {
			return createErrorResponse("Invalid token", now);
		}
		if (!"ADMIN".equals(role)) {
			return createErrorResponse("Only ADMIN can assign projects", now, HttpStatus.UNAUTHORIZED);
		}
		try {
			int responseCode = employeeService.assignProject(projects, id);
			if (responseCode == 1) {
				jsonObject.put("status", "success");
				jsonObject.put("message", "Project assigned successfully");
				return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(jsonObject.toString());
			} else {
				return createErrorResponse("Failed to assign project", now, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.error("Error occurred while assigning project: {}", e.getMessage());
			return createErrorResponse("An unexpected error occurred", now, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private ResponseEntity<String> createErrorResponse(String message, LocalDateTime timestamp, HttpStatus status) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("status", "error");
		jsonObject.put("message", message);
		jsonObject.put("timestamp", timestamp.toString());
		return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(jsonObject.toString());
	}

	@GetMapping("/showMyTeam")
	public ResponseEntity<String> showMyTeam(@RequestHeader("token") String token) {
		LocalDateTime now = LocalDateTime.now();
		Map<String, String> validateToken = jwtUtils.validateToken(token);
		if (validateToken.containsKey("error")) {
			return createErrorResponse(validateToken.get("error"), now);
		}
		String id = validateToken.get("id");
		// Check if the token exists in the database
		Employee findByEmployeeId = employeeService.findByEmployeeId(id);
		String dbToken = findByEmployeeId.getToken();
		if (dbToken == null || dbToken.isEmpty()) {
			return createErrorResponse("No token found in the database for the user", now);
		}
		// Compare the token provided in the request header with the one stored in the
		// database
		if (!dbToken.equals(token)) {
			return createErrorResponse("Invalid token", now);
		}
		String project = findByEmployeeId.getProject();
		if (project == null || project.isEmpty()) {
			return createErrorResponse("No project assigned for the user", now);
		}

		List<EmployeeDto> employees = employeeService.findAllByProject(project);
		if (employees == null || employees.isEmpty()) {
			return createErrorResponse("No one is working on this project", now);
		}
		if (employees.size() == 1) {
			return createErrorResponse("Only You are  working on this project", now);
		}
		// Now create a proper response
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("status", "success");
		jsonResponse.put("message", "Your Team");
		jsonResponse.put("employees", employees);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(jsonResponse.toString());
	}

}
