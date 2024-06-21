package com.example.demo.dto;

import java.util.Objects;

public class EmployeeDto {
	private String empName;
	private String designature;
	private String empAge;
	private String email;

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getDesignature() {
		return designature;
	}

	public void setDesignature(String designature) {
		this.designature = designature;
	}

	public String getEmpAge() {
		return empAge;
	}

	public void setEmpAge(String empAge) {
		this.empAge = empAge;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "EmployeeDto [empName=" + empName + ", designature=" + designature + ", empAge=" + empAge + ", email="
				+ email + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(designature, email, empAge, empName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeeDto other = (EmployeeDto) obj;
		return Objects.equals(designature, other.designature) && Objects.equals(email, other.email)
				&& Objects.equals(empAge, other.empAge) && Objects.equals(empName, other.empName);
	}

	public EmployeeDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmployeeDto(String empName, String designature, String empAge, String email) {
		super();
		this.empName = empName;
		this.designature = designature;
		this.empAge = empAge;
		this.email = email;
	}

}
