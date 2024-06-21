package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	public Employee findByEmailAndPassword(String email, String pwd);

	@Transactional
	@Modifying
	@Query(value = "update employee set password = ?1, PWD_ISCHANGED = '1' where employee_Id = ?2", nativeQuery = true)
	public int updatePwd(String encryptPwd, String id);

	public Employee findByEmail(String email);

	@Query(value = "SELECT e FROM Employee e WHERE e.employeeId = (SELECT MAX(e2.employeeId) FROM Employee e2) ")
	public Employee findTopByOrderByEmployeeIdDesc();

	@Transactional
	@Modifying
	@Query(value = "update employee set project = ?1 where employee_Id = ?2", nativeQuery = true)
	public int assignProject(String project, String id);

	public Employee findByEmployeeId(String string);
	@Query(value = "SELECT  emp_name AS name,designature,emp_age, email FROM employee WHERE project = ?1", nativeQuery = true)
	 List<Object[]> findAllDetailsByProject(String project);

}
