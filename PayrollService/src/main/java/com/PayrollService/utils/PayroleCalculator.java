package com.PayrollService.utils;

import com.PayrollService.Dto.AttendanceDto;
import com.PayrollService.Dto.DeductionResultDTO;
import com.PayrollService.Dto.Deductions;
import com.PayrollService.Model.DeductionSalary;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class  PayroleCalculator {

    // Tax brackets and rates
    private static final double[] TAX_BRACKETS = {0, 600, 1650, 3200, 5250, 7800, 10900}; // Income thresholds
    private static final double[] TAX_RATES = {0.0, 0.1, 0.15, 0.2, 0.25, 0.3, 0.35};

    // Pension contribution rates
    private static final double PRIVATE_EMPLOYEE_PENSION_EMPLOYEE = 0.07;
    private static final double PRIVATE_EMPLOYEE_PENSION_EMPLOYER = 0.11;
    private static final double PUBLIC_EMPLOYEE_PENSION_EMPLOYEE = 0.07;
    private static final double PUBLIC_EMPLOYEE_PENSION_EMPLOYER = 0.11;
    private static final double MILITARY_POLICE_PENSION_EMPLOYEE = 0.07;
    private static final double MILITARY_POLICE_PENSION_EMPLOYER = 0.25;

    public double calculateIncomeTax(double salary) {
        double tax = 0.0;
        for (int i = 0; i < TAX_BRACKETS.length - 1; i++) {
            if (salary > TAX_BRACKETS[i]) {
                double taxableIncome = Math.min(salary, TAX_BRACKETS[i + 1]) - TAX_BRACKETS[i];
                tax += taxableIncome * TAX_RATES[i];
            }
        }
        return tax;
    }
    public double claculategrosspay(double salary,double overtimepay,double bonuses) {
        return salary + overtimepay + bonuses;

    }

    public BigDecimal calculatePensionContribution(double salary, String employeeType) {
        double employeeContribution = 0.0;
        double employerContribution = 0.0;

        switch (employeeType.toLowerCase()) {
            case "private":
                employeeContribution = salary * PRIVATE_EMPLOYEE_PENSION_EMPLOYEE;
                employerContribution = salary * PRIVATE_EMPLOYEE_PENSION_EMPLOYER;
                break;
            case "public":
                employeeContribution = salary * PUBLIC_EMPLOYEE_PENSION_EMPLOYEE;
                employerContribution = salary * PUBLIC_EMPLOYEE_PENSION_EMPLOYER;
                break;
            case "military":
            case "police":
                employeeContribution = salary * MILITARY_POLICE_PENSION_EMPLOYEE;
                employerContribution = salary * MILITARY_POLICE_PENSION_EMPLOYER;
                break;
            default:
                throw new IllegalArgumentException("Invalid employee type: " + employeeType);
        }

        return BigDecimal.valueOf(employeeContribution + employerContribution);
    }
    public DeductionResultDTO calculateDeductions(double salary, List<Deductions> deductions) {
        double totalDeductions = 0.0;
        List<DeductionSalary> deductionList = new ArrayList<>(); // List to hold deductions

        System.out.println(deductions); // Changed print to println for clarity

        // Iterate through each deduction and sum them up based on percentage
        for (Deductions deduction : deductions) {
            double deductionAmount = salary * (deduction.getPercentage() / 100);
            totalDeductions += deductionAmount;

            // Add the deduction details to the list
            deductionList.add(new DeductionSalary(deduction.getName(), deductionAmount));
        }

        // Return total deductions and the list of deduction details
        return new DeductionResultDTO(totalDeductions, deductionList);
    }

    public double getAbsentDeduction(List<AttendanceDto> attendance, double baseSalary) {
        // Define the expected working days
        int expectedWorkDays = 24;

        // Count the number of present days
        long presentDays = attendance.stream()
                .filter(a -> a.getStatus().equals("PRESENT"))
                .count();

        // Calculate absent days
        long absentDays = expectedWorkDays - presentDays;

        // Calculate the absent deduction
        double dailyDeductionRate = 0.01; // 1% per day
        double absentDeduction = absentDays * dailyDeductionRate * baseSalary;



        return absentDeduction;
    }

}