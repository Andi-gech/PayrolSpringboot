package com.PayrollService.Dto;

import com.PayrollService.Model.DeductionSalary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeductionResultDTO {
    private double totalDeductions;           // Total deductions amount
    private List<DeductionSalary> deductionList; // List of deduction details
}
