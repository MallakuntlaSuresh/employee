package com.example.demo.serviceIml;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Payslip;
import com.example.demo.repo.EmployeeDtoMapper;
import com.example.demo.repo.EmployeeRepository;
import com.example.demo.repo.PayslipRepository;
import com.example.demo.service.EmployeeService;
import com.example.demo.util.EmployeeUtil;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	private EmployeeRepository employeeRepository;
	private PayslipRepository payslipRepository;
	private EmployeeUtil employeeUtil;

	public EmployeeServiceImpl(EmployeeRepository employeeRepository, PayslipRepository payslipRepository,
			EmployeeUtil employeeUtil) {
		this.employeeRepository = employeeRepository;
		this.payslipRepository = payslipRepository;
		this.employeeUtil = employeeUtil;
	}

	@Transactional
	public ResponseEntity<String> employeCreation(Employee employee) {
		JSONObject jsonObject = new JSONObject();
		Timestamp date = new Timestamp(System.currentTimeMillis());

		try {
			// Validate employee data
			String empNullOrEmptyCheck = employeeUtil.empNullOrEmptyCheck(employee);
			if (!"Success".equals(empNullOrEmptyCheck)) {
				throw new IllegalArgumentException("Employee data validation failed: " + empNullOrEmptyCheck);
			}

			// Generate password and set first password expiration
			String pwdGenerator = EmployeeUtil.pwdGenerator();
			employee.setPassword(pwdGenerator);
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime plusDays = now.plusDays(1);
			employee.setFirstPwdExp(plusDays);

			// Fetch the latest employee record from the database
			Employee findTopEmployee = employeeRepository.findTopByOrderByEmployeeIdDesc();
			String newEmployeeId = generateEmployeeId(findTopEmployee);

			// Assign the new employee ID to the employee
			employee.setEmployeeId(newEmployeeId);

			int ageCalculator = EmployeeUtil.ageCalculator(employee.getEmpDob());
			employee.setEmpAge(String.valueOf(ageCalculator));

			Employee savedEmployee = employeeRepository.save(employee);
			if (savedEmployee == null || savedEmployee.getId() == null) {
				throw new RuntimeException("Failed to insert employee.");
			}

			Payslip payslip = createPayslipForEmployee(savedEmployee);
			Payslip savedPayslip = payslipRepository.save(payslip);
			if (savedPayslip == null || savedPayslip.getInternalId() == null) {
				throw new RuntimeException("Failed to insert Payslip.");
			}

			jsonObject.put("status", "Employee Created successfully.");
			jsonObject.put("timeStamp", date);

			return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
					.body(jsonObject.toString());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			logger.error("IllegalArgumentException occurred: {}", e.getMessage());
			jsonObject.put("status", "Failed to create employee.");
			jsonObject.put("errorMessage", e.getMessage());
			jsonObject.put("timeStamp", date);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
					.body(jsonObject.toString());
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.error("RuntimeException occurred: {}", e.getMessage());
			jsonObject.put("status", "Failed to create employee.");
			jsonObject.put("errorMessage", e.getMessage());
			jsonObject.put("timeStamp", date);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
					.body(jsonObject.toString());
		}
	}

	public synchronized String generateEmployeeId(Employee latestEmployee) {
		int lastGeneratedNumber;
		String employeeId;

		if (latestEmployee == null || latestEmployee.getEmployeeId() == null
				|| latestEmployee.getEmployeeId().isEmpty()) {
			lastGeneratedNumber = 1;
		} else {
			// Extract the last generated number from the latest employee ID
			String lastEmployeeId = latestEmployee.getEmployeeId();
			lastGeneratedNumber = Integer.parseInt(lastEmployeeId.substring(lastEmployeeId.length() - 3));
			lastGeneratedNumber++; // Increment the last generated number
		}

		// Generate the new employee ID
		YearMonth currentYearMonth = YearMonth.now();
		String yearMonth = String.format("%d%02d", currentYearMonth.getYear(), currentYearMonth.getMonthValue());
		employeeId = yearMonth + String.format("%03d", lastGeneratedNumber);

		return "FIN" + employeeId;
	}

	private Payslip createPayslipForEmployee(Employee employee) {
		double grossSalary = employee.getSalary();
		double basicSalary;
		double allowances;
		double housingAllowance;
		double transportAllowance;
		double medicalAllowance;
		double taxDeduction;
		double pensionDeduction;
		double insuranceDeduction;

		basicSalary = grossSalary * 0.75;
		allowances = grossSalary * 0.25;
		housingAllowance = 15000.0;
		transportAllowance = 8000.0;
		medicalAllowance = 7000.0;

		taxDeduction = 30000.0;
		pensionDeduction = 15000.0;
		insuranceDeduction = 10000.0;
		double all = medicalAllowance + transportAllowance + housingAllowance;
		double deductions = taxDeduction + pensionDeduction + insuranceDeduction;
		double netSalary = grossSalary - deductions;
		double d = netSalary + all;
		double act = d / 12;
		long round = Math.round(act);
		Payslip payslip = new Payslip();
		payslip.setEmpId(employee.getEmployeeId());
		payslip.setGrossSalary(grossSalary);
		payslip.setDeductions(deductions);
		payslip.setNetSalary(round);
		payslip.setBasicSalary(basicSalary);
		payslip.setAllowances(allowances);
		payslip.setHousingAllowance(housingAllowance);
		payslip.setTransportAllowance(transportAllowance);
		payslip.setMedicalAllowance(medicalAllowance);
		payslip.setTaxDeduction(taxDeduction);
		payslip.setPensionDeduction(pensionDeduction);
		payslip.setInsuranceDeduction(insuranceDeduction);
		return payslip;
	}

	@Override
	public Employee emailPwd(String email, String pwd) {
		return employeeRepository.findByEmailAndPassword(email, pwd);
	}

	@Override
	public Integer updatePwd(String pwd, String id) {
		return employeeRepository.updatePwd(pwd, id);
	}

	@Override
	public String forgetPwd(String mobileNumber) {

		return null;
	}

	@Override
	public Optional<Employee> findById(Long empId) {
		return employeeRepository.findById(empId);
	}

	@Override
	public Employee findByEmail(String email) {
		return employeeRepository.findByEmail(email);
	}

	@Override
	public void registerEmployee(Employee employee) {

	}

	@Override
	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

	@Override
	public int assignProject(String project, String id) {
		return employeeRepository.assignProject(project, id);
	}

	@Override
	public Employee findByEmployeeId(String string) {
		// TODO Auto-generated method stub
		return employeeRepository.findByEmployeeId(string);
	}

	@Override
	public List<EmployeeDto> findAllByProject(String project) {
		List<Object[]> rows = employeeRepository.findAllDetailsByProject(project);
		return rows.stream().map(EmployeeDtoMapper::map).collect(Collectors.toList());
	}

}
