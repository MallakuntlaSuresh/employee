package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.entity.Employee;

public interface EmployeeService {

	public Employee emailPwd(String email, String pwd);

	public ResponseEntity<String> employeCreation(Employee employee);

	public Integer updatePwd(String pwd, String id);

	public String forgetPwd(String mobileNumber);

	public Optional<Employee> findById(Long empId);

	public Employee findByEmail(String email);

	void registerEmployee(Employee employee);

	public java.util.List<Employee> findAll();

	public int assignProject(String project, String id);

	public Employee findByEmployeeId(String string);

	public List<EmployeeDto> findAllByProject(String project);

}
