package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Employee;
import com.example.demo.entity.Payslip;
import com.example.demo.repo.PayslipRepository;

@Service
public class PayslipService {

	@Autowired
	private PayslipRepository payslipRepository;

	public Payslip generatePayslip(Employee employee, String month) {
		// Assuming you have some logic to calculate the payslip details
		double grossSalary = calculateGrossSalary(employee);
		double deductions = calculateDeductions(employee, grossSalary);
		double netSalary = grossSalary - deductions;

		Payslip payslip = new Payslip();
		payslip.setMonth(month);
		payslip.setGrossSalary(grossSalary);
		payslip.setDeductions(deductions);
		payslip.setNetSalary(netSalary);

		return payslipRepository.save(payslip);
	}

	private double calculateGrossSalary(Employee employee) {
		double basicSalary = 5000.0; // Example value
		double allowances = calculateAllowances(employee);
		return basicSalary + allowances;
	}

	private double calculateAllowances(Employee employee) {
		double housingAllowance = 1000.0;
		double transportAllowance = 500.0;
		double medicalAllowance = 200.0;
		return housingAllowance + transportAllowance + medicalAllowance;
	}

	private double calculateDeductions(Employee employee, double grossSalary) {
		double taxRate = 0.2;
		double pensionRate = 0.1;
		double taxDeduction = grossSalary * taxRate;
		double pensionDeduction = grossSalary * pensionRate;
		return taxDeduction + pensionDeduction;
	}
}
