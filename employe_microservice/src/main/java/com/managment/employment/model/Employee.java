package com.managment.employment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Long id;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "hired_date")
    private LocalDate hiredDate;

    @Column(name = "termination_date")
    private LocalDate terminationDate;
    @Column(name="Gender")
    private String gender;


    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EmploymentStatus status;

    public enum EmploymentStatus {
        ACTIVE,
        INACTIVE,
        TERMINATED
    }

    @Column(name = "salary")
    private Double salary;

    public enum PayFrequency {
        MONTHLY,
        WEEKLY,
        BIWEEKLY
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "pay_frequency")
    private PayFrequency payFrequency;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "fk_emp_id", referencedColumnName = "emp_id")
    private List<Address> addresses;

}
