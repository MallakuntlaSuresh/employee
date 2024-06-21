package com.example.demo.serviceIml;

import org.springframework.stereotype.Component;

import com.example.demo.entity.Payslip;
import com.example.demo.repo.Employee;

//PayslipGeneratorService.java
@Component
public class PayslipGeneratorService {

	public static Payslip generatePayslip(Employee employee, String month) {
		double grossSalary = employee.getSalary();
		double deductions = grossSalary * 0.1;
		double netSalary = grossSalary - deductions;
		System.out.println("grossSalary" + grossSalary);
		Payslip payslip = new Payslip();
		payslip.setGrossSalary(employee.getSalary());
		payslip.setNetSalary(netSalary);
		payslip.setDeductions(deductions);
		System.out.println("payslip" + payslip);
		return payslip;
	}

	public static void main(String[] args) {
		Employee employee = new Employee();
		employee.setId("456yuiop");
		employee.setName("yfgvhbjnk");
		employee.setSalary(99885);
		String march = "April";
		Payslip generatePayslip = generatePayslip(employee, march);
		System.out.println(generatePayslip);
	}
}