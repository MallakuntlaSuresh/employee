package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "payslip")
public class Payslip {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long internalId;
	private String month;
	private double grossSalary;
	private double deductions;
	private double netSalary;
	private double basicSalary;
	private double allowances;
	private double housingAllowance;
	private double transportAllowance;
	private double medicalAllowance;
	private double taxDeduction;
	private double pensionDeduction;
	private double insuranceDeduction;
	private String empId;

	public Long getInternalId() {
		return internalId;
	}

	public void setInternalId(Long internalId) {
		this.internalId = internalId;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public double getGrossSalary() {
		return grossSalary;
	}

	public void setGrossSalary(double grossSalary) {
		this.grossSalary = grossSalary;
	}

	public double getDeductions() {
		return deductions;
	}

	public void setDeductions(double deductions) {
		this.deductions = deductions;
	}

	public double getNetSalary() {
		return netSalary;
	}

	public void setNetSalary(double netSalary) {
		this.netSalary = netSalary;
	}

	public double getBasicSalary() {
		return basicSalary;
	}

	public void setBasicSalary(double basicSalary) {
		this.basicSalary = basicSalary;
	}

	public double getAllowances() {
		return allowances;
	}

	public void setAllowances(double allowances) {
		this.allowances = allowances;
	}

	public double getHousingAllowance() {
		return housingAllowance;
	}

	public void setHousingAllowance(double housingAllowance) {
		this.housingAllowance = housingAllowance;
	}

	public double getTransportAllowance() {
		return transportAllowance;
	}

	public void setTransportAllowance(double transportAllowance) {
		this.transportAllowance = transportAllowance;
	}

	public double getMedicalAllowance() {
		return medicalAllowance;
	}

	public void setMedicalAllowance(double medicalAllowance) {
		this.medicalAllowance = medicalAllowance;
	}

	public double getTaxDeduction() {
		return taxDeduction;
	}

	public void setTaxDeduction(double taxDeduction) {
		this.taxDeduction = taxDeduction;
	}

	public double getPensionDeduction() {
		return pensionDeduction;
	}

	public void setPensionDeduction(double pensionDeduction) {
		this.pensionDeduction = pensionDeduction;
	}

	public double getInsuranceDeduction() {
		return insuranceDeduction;
	}

	public void setInsuranceDeduction(double insuranceDeduction) {
		this.insuranceDeduction = insuranceDeduction;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}


}
