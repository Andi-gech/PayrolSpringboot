package com.PayrollService.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employeedto {

    private Long id; // Optional for create; required for update

    @NotNull(message = "Department ID cannot be null")
    private Long departmentId;

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name should not exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name should not exceed 50 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phone;

    private String address;

    @NotNull(message = "Date of Birth cannot be null")
    private LocalDate dob;

    private LocalDate hiredDate;

    private LocalDate terminationDate;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotNull(message = "Status cannot be null")
    private EmploymentStatus status;

    @NotNull(message = "Salary cannot be null")
    private Double salary;

    private PayFrequency payFrequency;


}

// Define the enums used in the DTO as well
enum EmploymentStatus {
    ACTIVE,
    INACTIVE,
    TERMINATED
}

enum PayFrequency {
    MONTHLY,
    WEEKLY,
    BIWEEKLY
}
