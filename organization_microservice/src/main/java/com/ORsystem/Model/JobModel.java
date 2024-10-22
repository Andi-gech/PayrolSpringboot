package com.ORsystem.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "job_tabless")
public class JobModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long jobId;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "job_name")
    private String jobName;

    @Column(name = "description")
    private String description;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "fk_id", referencedColumnName = "id")
    private List<JobGradeModel> jobGrades = new ArrayList<>();
}
