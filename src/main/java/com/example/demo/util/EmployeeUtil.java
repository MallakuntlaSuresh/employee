package com.example.demo.util;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.Period;

import org.springframework.stereotype.Component;

import com.example.demo.entity.Employee;
import com.example.demo.entity.Role;

@Component
public class EmployeeUtil {
	private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
	private static final String SPECIAL = "!@#$%^&*()-_=+";
	private static final String DIGITS = "1234567890";

	public static String pwdGenerator() {
		String combinedChars = UPPER + LOWER + SPECIAL + DIGITS;
		SecureRandom random = new SecureRandom();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 15; i++) {
			int nextInt = random.nextInt(combinedChars.length());
			builder.append(combinedChars.charAt(nextInt));
		}
		return builder.toString();
	}


    public String empNullOrEmptyCheck(Employee employee) {
        String designature = employee.getDesignature();
        String email = employee.getEmail();
        String empDateOfJoin = employee.getEmpDateOfJoin();
        String empDob = employee.getEmpDob();
        String empExperience = employee.getEmpExperience();
        String empName = employee.getEmpName();
        Role role = employee.getRole();

        if (isEmptyOrNull(designature)) {
            return "Designature should not be empty or null";
        }
        if (isEmptyOrNull(email)) {
            return "Email should not be empty or null";
        }
        if (isEmptyOrNull(empDateOfJoin)) {
            return "Date Of Join should not be empty or null";
        }
        if (isEmptyOrNull(empDob)) {
            return "Date of birth should not be empty or null";
        }
        if (isEmptyOrNull(empExperience)) {
            return "Experience should not be empty or null";
        }
        if (isEmptyOrNull(empName)) {
            return "Name should not be empty or null";
        }
        if (role == null) {
            return "Role should not be null";
        }

        // Check if the role is valid
        if (!isValidRole(role)) {
            return "Invalid Role";
        }

        // Additional validation checks can be added here

        // If all checks pass, return "Success"
        return "Success";
    }
	private boolean isValidRole(Role role) {
	    for (Role validRole : Role.values()) {
	        if (validRole.equals(role)) {
	            return true;
	        }
	    }
	    return false;
	}


	public static boolean empDetailsValidation(Employee employee) {
		return isValidEmail(employee.getEmail());
	}

	private static boolean isValidEmail(String email) {
		return email.contains("@") && email.contains(".");
	}

	public static int ageCalculator(String dob) {
		LocalDate birthDate = LocalDate.parse(dob);

		LocalDate now = LocalDate.now();
		Period period = Period.between(birthDate, now);
		System.out.println("period " + period);
		return period.getYears();
	}

	public String EncryptPwd(String pwd) {
		// need to write the logic for Encryption
		return "";
	}

	public static void main(String[] args) {
		boolean pwdGenerator = isEmptyOrNull(null);
		System.out.println(pwdGenerator);
	}

	private static boolean isEmptyOrNull(String str) {
		return str == null || str.trim().isEmpty();
	}

}
