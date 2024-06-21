package com.example.demo.serviceIml;

import java.util.Scanner;
import java.util.Scanner;
import java.util.Scanner;
import java.util.Scanner;

public class SalaryCalculator {
    // Constants
    private static final double PERFORMANCE_BONUS_PERCENTAGE = 0.15;
    private static final double PROFESSIONAL_TAX = 200;
    private static final double EMPLOYER_PF = 1800;
    private static final double EMPLOYEE_PF = 1800;
    private static final double EMPLOYEE_INSURANCE = 250;

    // Function to calculate monthly deductions
    private static double calculateMonthlyDeductions() {
        return PROFESSIONAL_TAX + EMPLOYER_PF + EMPLOYEE_PF + EMPLOYEE_INSURANCE;
    }

    // Function to calculate performance bonus
    private static double calculatePerformanceBonus(double yearlyGrossPay) {
        return yearlyGrossPay * PERFORMANCE_BONUS_PERCENTAGE;
    }

    // Function to calculate net take-home pay monthly
    private static double calculateNetTakeHomeMonthly(double yearlyGrossPay, double yearlyDeductions, double performanceBonus) {
        double netYearlyPay = yearlyGrossPay - yearlyDeductions + performanceBonus;
        return netYearlyPay / 12;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input CTC
        System.out.print("Enter Cost to Company (CTC) in ₹: ");
        double ctc = scanner.nextDouble();

        // Calculate performance bonus
        double performanceBonus = calculatePerformanceBonus(ctc);

        // Calculate monthly deductions
        double monthlyDeductions = calculateMonthlyDeductions();

        // Calculate yearly deductions
        double yearlyDeductions = monthlyDeductions * 12;

        // Calculate net take-home pay monthly
        double netTakeHomeMonthly = (ctc - yearlyDeductions + performanceBonus) / 12;
        double netTakeHomeAnnual = netTakeHomeMonthly * 12;

        // Output
        System.out.println("Net take home monthly: ₹" + netTakeHomeMonthly);
        System.out.println("Net take home annual: ₹" + netTakeHomeAnnual);
        
        scanner.close();
    }
}
