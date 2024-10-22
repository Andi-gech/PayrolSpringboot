package com.PayrollService.utils;

import java.math.BigDecimal;

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




}