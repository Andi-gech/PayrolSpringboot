package com.ORsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jobgrade_tab")
public class JobGradeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "job_grade_name", nullable = false)
    private String jobGradeName;

    @Column(name = "description")
    private String description;

    @Column(name = "salary_step", nullable = false)
    @NotNull(message = "Salary step cannot be null")
    @Min(value = 1, message = "Salary step must be at least 1")
    private Integer salaryStep;

    @Column(name = "salary", nullable = false)
    @NotNull(message = "Salary cannot be null")
    @DecimalMin(value = "0.0", message = "Salary must be greater than or equal to 0")
    private Double salary;

}
