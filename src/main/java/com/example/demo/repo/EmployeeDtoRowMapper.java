package com.example.demo.repo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.dto.EmployeeDto;

public class EmployeeDtoRowMapper implements RowMapper<EmployeeDto> {
	@Override
	public EmployeeDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		EmployeeDto empDto = new EmployeeDto();

		empDto.setEmpName(rs.getString("empName"));
		empDto.setEmpAge(rs.getString("empAge"));
		empDto.setEmail(rs.getString("email"));
		empDto.setDesignature(rs.getString("designature"));
		return empDto;
	}
}
