package com.PayrollService.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "payroll")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayrollModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;  // Unique payroll ID

    @Column(name = "employee_id")
    private Long employeeId;  // Foreign key linking to Employee

    @Column(name = "pay_period_start")
    private LocalDate payPeriodStart;  // Start date of the pay period

    @Column(name = "pay_period_end")
    private LocalDate payPeriodEnd;  // End date of the pay period

    @Column(name = "pension_deduction")
    private BigDecimal pensionDeduction; // Pension deduction amount

    @Column(name = "tax_deduction")
    private BigDecimal taxDeduction; // Tax deduction amount

    @Column(name = "overtime_hours")
    private BigDecimal overtimeHours;  // Overtime hours worked

    @Column(name = "overtime_pay")
    private BigDecimal overtimePay;  // Overtime pay amount

    @Column(name = "bonuses")
    private BigDecimal bonuses;  // Bonuses included in the payroll

    @Column(name = "total_deductions")
    private BigDecimal totalDeductions;  // Total deductions

    @Column(name = "gross_pay")
    private BigDecimal grossPay;  // Total gross pay (before deductions)

    @Column(name = "net_pay")
    private BigDecimal netPay;  // Total net pay (after deductions)

    @Column(name = "pay_date")
    private LocalDate payDate;  // Date the payment is processed

    @Column(name = "created_at")
    private LocalDateTime createdAt;  // Date the payroll was created

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column (name = "absentdeduction")
    private BigDecimal absentdeduction;// Last updated timestamp

    @ElementCollection
    @CollectionTable(name = "deduction_details", joinColumns = @JoinColumn(name = "payroll_id"))
    private List<DeductionSalary> deductionDetails; // List of deductions
}
