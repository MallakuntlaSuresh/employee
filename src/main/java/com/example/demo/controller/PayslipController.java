package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PayslipDto;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Payslip;
import com.example.demo.repo.PayslipRepository;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.PayslipService;

@RestController
public class PayslipController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private PayslipService payslipService;
	@Autowired
	private PayslipRepository payslipRepository;

	@PostMapping("/generate")
	public ResponseEntity<byte[]> generatePayslip(@RequestBody PayslipDto dto) {
		Payslip employee = payslipRepository.findByEmpId(dto.getEmpId());
		System.out.println("employee " + employee.getAllowances());
		if (employee == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return null;

	}
}