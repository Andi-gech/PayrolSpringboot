package com.PayrollService.Service;

import com.PayrollService.Dto.AttendanceDto;
import com.PayrollService.Dto.DeductionResultDTO;
import com.PayrollService.Dto.Deductions;
import com.PayrollService.Dto.Employeedto;
import com.PayrollService.Exceptions.BadRequestException;
import com.PayrollService.Model.PayrollModel;
import com.PayrollService.Repository.PayrollRepository;

import com.PayrollService.utils.PayroleCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PayrollService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired

    private PayrollRepository payrollRepository;
    PayroleCalculator calculator = new PayroleCalculator();




    public PayrollModel createPayroll(PayrollModel payroll, int year, int month) {
        validateEmployeeId(payroll.getEmployeeId());

        checkForExistingPayroll(payroll.getEmployeeId(),payroll.getPayPeriodStart(), payroll.getPayPeriodEnd());


        Employeedto employee = fetchEmployeeOrThrow(payroll.getEmployeeId());
        updatePayrollWithEmployeeDetails(payroll, employee);
        String deductionurl="http://localhost:4040/api/deductions";
        String attendanceurl="http://localhost:2727/api/attendance/"+employee.getId()+"/"+year+"/"+month;

        List<Deductions> deductions = fetchDeductions(deductionurl);
        List<AttendanceDto> attendance=fetchAttendance(attendanceurl);

        System.out.print(attendance);


        double baseSalary = employee.getSalary() != null ? employee.getSalary() : 0.0;
        double absentdeduction=calculator.getAbsentDeduction(attendance,baseSalary);
        payroll.setAbsentdeduction(BigDecimal.valueOf(absentdeduction));

        DeductionResultDTO DeductionsResult=calculator.calculateDeductions( baseSalary, deductions);
        System.out.println("deduction from salary"+DeductionsResult.getTotalDeductions());
        double incomeTax = calculator.calculateIncomeTax(baseSalary);
        double pensionTax = calculator.calculatePensionContribution(baseSalary, "public").doubleValue();

        BigDecimal netPay = calculateNetPay(baseSalary, incomeTax, pensionTax,DeductionsResult.getTotalDeductions(),absentdeduction);
        payroll.setNetPay(netPay);
        payroll.setPensionDeduction(BigDecimal.valueOf(pensionTax));
        payroll.setTaxDeduction(BigDecimal.valueOf(incomeTax));
        payroll.setDeductionDetails(DeductionsResult.getDeductionList());

        payroll.setTotalDeductions(BigDecimal.valueOf(incomeTax + pensionTax+ DeductionsResult.getTotalDeductions()+absentdeduction));
        payroll.setGrossPay(calculateGrossPay(baseSalary, payroll));

        return payrollRepository.save(payroll);
    }
    private void checkForExistingPayroll(Long employeeId, LocalDate newPayPeriodStart, LocalDate newPayPeriodEnd) {
        List<PayrollModel> existingPayrolls = payrollRepository.findByEmployeeId(employeeId);

        for (PayrollModel existingPayroll : existingPayrolls) {
            LocalDate existingStart = existingPayroll.getPayPeriodStart();
            LocalDate existingEnd = existingPayroll.getPayPeriodEnd();

            // Check for overlap
            if (newPayPeriodStart.isBefore(existingEnd) && newPayPeriodEnd.isAfter(existingStart)) {
                throw new BadRequestException("Payroll already exists for employee ID: " + employeeId +
                        " for the overlapping pay period: " + existingStart + " to " + existingEnd);
            }
        }
    }

    private void validateEmployeeId(Long employeeId) {
        if (employeeId == null) {
            throw new BadRequestException("Employee ID must not be null.");
        }
    }

    private Employeedto fetchEmployeeOrThrow(Long employeeId) {
        String employeeServiceUrl = "http://localhost:5555/api/v1/employee/" + employeeId;
        System.out.println("Attempting to fetch employee with ID: " + employeeId);

        try {
            Employeedto employee = restTemplate.getForObject(employeeServiceUrl, Employeedto.class);
            System.out.println("Fetched Employee: " + employee);
            if (employee == null) {
                throw new BadRequestException("Employee not found for IDs: " + employeeId);
            }
            return employee;
        } catch (HttpClientErrorException e) {
            handleEmployeeFetchError(employeeId, e);
            return null;
        }
    }
    private List<Deductions> fetchDeductions(String deductionUrl) {
        ResponseEntity<List<Deductions>> responseEntity = restTemplate.exchange(
                deductionUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Deductions>>() {}
        );
        return responseEntity.getBody();
    }
    private List<AttendanceDto> fetchAttendance(String attendanceUrl) {
        ResponseEntity<List<AttendanceDto>> responseEntity = restTemplate.exchange(
                attendanceUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AttendanceDto>>() {}
        );
        return responseEntity.getBody();
    }
    private void handleEmployeeFetchError(Long employeeId, HttpClientErrorException e) {
        if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new BadRequestException("Employee not found for IDr: " + employeeId);
        }
        throw new RuntimeException("An error occurred while retrieving the employee: " + e.getMessage());
    }

    private void updatePayrollWithEmployeeDetails(PayrollModel payroll, Employeedto employee) {
        payroll.setEmployeeId(employee.getId());

    }

    private BigDecimal calculateNetPay(double baseSalary, double incomeTax, double pensionTax,double totaldeduction,double absentdeduction) {
        return BigDecimal.valueOf(baseSalary)
                .subtract(BigDecimal.valueOf(incomeTax + pensionTax +absentdeduction+totaldeduction));
    }

    private BigDecimal calculateGrossPay(double baseSalary, PayrollModel payroll) {
        return BigDecimal.valueOf(calculator.claculategrosspay(baseSalary,
                payroll.getOvertimePay().doubleValue(),
                payroll.getBonuses().doubleValue()));
    }

    public PayrollModel getPayrollById(Long id) {
        Optional<PayrollModel> payroll = payrollRepository.findById(id);
        if (payroll.isPresent()) {
            return payroll.get();
        } else {
            throw new RuntimeException("Payroll not found with ID: " + id);
        }
    }


    public List<PayrollModel> getAllPayrolls() {
        return payrollRepository.findAll();
    }


    public List<PayrollModel> getPayrollsByEmployeeId(Long employeeId) {
        return payrollRepository.findByEmployeeId(employeeId);
    }


    public PayrollModel updatePayroll(Long id, PayrollModel payrollDetails) {
        if (!payrollRepository.existsById(payrollDetails.getEmployeeId())) {
            throw new RuntimeException("Employee not found with ID: " + payrollDetails.getEmployeeId());
        }
        PayrollModel existingPayroll = payrollRepository.findById(id).get();
        existingPayroll.setPayPeriodStart(payrollDetails.getPayPeriodStart());
        existingPayroll.setPayPeriodEnd(payrollDetails.getPayPeriodEnd());

        existingPayroll.setOvertimeHours(payrollDetails.getOvertimeHours());
        existingPayroll.setOvertimePay(payrollDetails.getOvertimePay());
        existingPayroll.setBonuses(payrollDetails.getBonuses());
        existingPayroll.setGrossPay(payrollDetails.getGrossPay());
        existingPayroll.setNetPay(payrollDetails.getNetPay());
        existingPayroll.setPayDate(payrollDetails.getPayDate());
        return payrollRepository.save(existingPayroll);
    }


    public void deletePayroll(Long id) {
        payrollRepository.deleteById(id);
    }
}
