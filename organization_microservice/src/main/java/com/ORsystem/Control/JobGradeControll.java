package com.ORsystem.Control;

import com.ORsystem.ErrorHandler.ErrorResponse;
import com.ORsystem.Model.JobGradeModel;
import com.ORsystem.Service.JobGradeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/job-grades")
public class JobGradeControll {

    private final JobGradeService jobGradeService;

    @Autowired
    public JobGradeControll(JobGradeService jobGradeService) {
        this.jobGradeService = jobGradeService;
    }

    // Get all job grades
    @GetMapping
    public List<JobGradeModel> getAllJobGrades() {
        return jobGradeService.getAllJobGrades();
    }

    // Get a job grade by ID
    @GetMapping("/{id}")
    public ResponseEntity<JobGradeModel> getJobGradeById(@PathVariable Long id) {
        Optional<JobGradeModel> jobGrade = jobGradeService.getJobGradeById(id);
        return jobGrade.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new job grade

    @PostMapping
    public ResponseEntity<?> createJobGrade(@RequestBody JobGradeModel jobGradeModel) {

        JobGradeModel createdJobGrade = jobGradeService.createJobGrade(jobGradeModel);
        return ResponseEntity.ok(createdJobGrade);
    }

    // Update a job grade by ID
    @PutMapping("/{id}")
    public ResponseEntity<JobGradeModel> updateJobGrade(@PathVariable Long id, @RequestBody JobGradeModel jobGradeDetails) {
        try {
            JobGradeModel updatedJobGrade = jobGradeService.updateJobGrade(id, jobGradeDetails);
            return ResponseEntity.ok(updatedJobGrade);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a job grade by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobGrade(@PathVariable Long id) {
        try {
            jobGradeService.deleteJobGrade(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

