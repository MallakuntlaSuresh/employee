package com.example.demo.repo;

import com.example.demo.dto.EmployeeDto;

//EmployeeDtoMapper.java
public class EmployeeDtoMapper {
	public static EmployeeDto map(Object[] row) {
		String name = (String) row[0];
		String designature = (String) row[1];
		String empAge = (String) row[2];
		String email = (String) row[3];
		return new EmployeeDto(name, designature,empAge,email);
	}
}
