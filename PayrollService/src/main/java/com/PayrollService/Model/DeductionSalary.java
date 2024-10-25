package com.PayrollService.Model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeductionSalary implements Serializable {
    private String name;    // Deduction name
    private double salary;  // Deduction amount
}
