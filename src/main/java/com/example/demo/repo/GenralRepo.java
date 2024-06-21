package com.example.demo.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.EmployeeDto;

@Repository
public class GenralRepo {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<EmployeeDto> findAll() {
		List<EmployeeDto> sightBills = jdbcTemplate.query("select * from employee", new EmployeeDtoRowMapper());
		return sightBills;
	}
}
