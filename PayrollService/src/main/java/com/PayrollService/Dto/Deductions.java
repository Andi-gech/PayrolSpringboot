package com.PayrollService.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Deductions {
    private Long id;

    private String name;

    private double percentage;
}
