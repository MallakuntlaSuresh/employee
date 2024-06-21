package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Payslip;

@Repository
public interface PayslipRepository extends JpaRepository<Payslip, Long> {

	Payslip findByEmpId(String empId);
}
