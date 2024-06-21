package com.example.demo.entity;

import java.time.Year;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Student")
public class Student {
	@Id
	@GeneratedValue
	private Long id;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Student(Long id, String name) {
		super();
		Long generateEmployeeId = generateEmployeeId();
		this.id = generateEmployeeId;
		this.name = name;
	}

	private Long generateEmployeeId() {
		String prefix = "FIN";
		Year year = Year.now();
		double random = Math.random();
		String s=prefix + year + random;
		long parseLong = Long.parseLong(s);
		return parseLong;
	}

	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
