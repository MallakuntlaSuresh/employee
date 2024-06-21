package com.example.demo.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String employeeId;
	private String empName;
	private String designature;
	private String empAge;
	private String empDob;
	private String empDateOfJoin;
	private String empExperience;
	@Column(unique = true)
	private String email;
	private String password;
	private LocalDateTime firstPwdExp;
	private Timestamp pwdExp;
	private int pwdIschanged;
	private double salary;
	private String token;
	private Role role;
	private String project;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

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

	public String getEmpDob() {
		return empDob;
	}

	public void setEmpDob(String empDob) {
		this.empDob = empDob;
	}

	public String getEmpDateOfJoin() {
		return empDateOfJoin;
	}

	public void setEmpDateOfJoin(String empDateOfJoin) {
		this.empDateOfJoin = empDateOfJoin;
	}

	public String getEmpExperience() {
		return empExperience;
	}

	public void setEmpExperience(String empExperience) {
		this.empExperience = empExperience;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getFirstPwdExp() {
		return firstPwdExp;
	}

	public void setFirstPwdExp(LocalDateTime firstPwdExp) {
		this.firstPwdExp = firstPwdExp;
	}

	public Timestamp getPwdExp() {
		return pwdExp;
	}

	public void setPwdExp(Timestamp pwdExp) {
		this.pwdExp = pwdExp;
	}

	public int getPwdIschanged() {
		return pwdIschanged;
	}

	public void setPwdIschanged(int pwdIschanged) {
		this.pwdIschanged = pwdIschanged;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", employeeId=" + employeeId + ", empName=" + empName + ", designature="
				+ designature + ", empAge=" + empAge + ", empDob=" + empDob + ", empDateOfJoin=" + empDateOfJoin
				+ ", empExperience=" + empExperience + ", email=" + email + ", password=" + password + ", firstPwdExp="
				+ firstPwdExp + ", pwdExp=" + pwdExp + ", pwdIschanged=" + pwdIschanged + ", salary=" + salary
				+ ", token=" + token + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(designature, email, empAge, empDateOfJoin, empDob, empExperience, empName, employeeId,
				firstPwdExp, id, password, pwdExp, pwdIschanged, salary, token);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(designature, other.designature) && Objects.equals(email, other.email)
				&& Objects.equals(empAge, other.empAge) && Objects.equals(empDateOfJoin, other.empDateOfJoin)
				&& Objects.equals(empDob, other.empDob) && Objects.equals(empExperience, other.empExperience)
				&& Objects.equals(empName, other.empName) && Objects.equals(employeeId, other.employeeId)
				&& Objects.equals(firstPwdExp, other.firstPwdExp) && Objects.equals(id, other.id)
				&& Objects.equals(password, other.password) && Objects.equals(pwdExp, other.pwdExp)
				&& pwdIschanged == other.pwdIschanged
				&& Double.doubleToLongBits(salary) == Double.doubleToLongBits(other.salary)
				&& Objects.equals(token, other.token);
	}

}
