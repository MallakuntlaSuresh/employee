package com.example.demo.controller;

import java.sql.Timestamp;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;

@RestController
public class RegisterController {

	EmployeeService employeeService;

	public RegisterController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@PostMapping("/register")
	 @CrossOrigin(origins = "http://localhost:8000")
	public ResponseEntity<String> registerEmployee(@RequestBody Employee employee) {
		System.out.println("In Controller "+employee);
		JSONObject jsonObject = new JSONObject();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		System.out.println("In Controller Input For Registration ::" + employee);
		Employee findByEmail = employeeService.findByEmail(employee.getEmail());
		if (findByEmail == null || findByEmail.getEmail() == null || findByEmail.getEmail().isEmpty()) {
			return employeeService.employeCreation(employee);
		} else {
			jsonObject.put("status", "Email Already Exits");
			jsonObject.put("timestamp", timestamp);
			return ResponseEntity.status(409).contentType(MediaType.APPLICATION_JSON).body(jsonObject.toString());
		}

	}

}
