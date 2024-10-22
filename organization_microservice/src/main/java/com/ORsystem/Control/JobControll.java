package com.ORsystem.Control;

import com.ORsystem.Model.JobGradeModel;
import com.ORsystem.Model.JobModel;

import com.ORsystem.Service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/jobs")
public class JobControll {

    private final JobService jobService;

    @Autowired
    public JobControll(JobService jobService) {
        this.jobService = jobService;
    }

    // Get all jobs
    @GetMapping
    public List<JobModel> getAllJobs() {
        return jobService.getAllJobs();
    }

    // Get a job by ID
    @GetMapping("/{id}")
    public ResponseEntity<JobModel> getJobById(@PathVariable Long id) {
        Optional<JobModel> job = jobService.getJobById(id);
        return job.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new job
    @PostMapping
    public ResponseEntity<JobModel> createJob(@RequestBody JobModel jobModel) {
        JobModel createdJob = jobService.createJob(jobModel);
        return ResponseEntity.ok(createdJob);
    }

    // Update an existing job by ID
    @PutMapping("/{id}")
    public ResponseEntity<JobModel> updateJob(@PathVariable Long id, @RequestBody JobModel jobDetails) {
        try {
            JobModel updatedJob = jobService.updateJob(id, jobDetails);
            return ResponseEntity.ok(updatedJob);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{jobId}/grades")
    public ResponseEntity<JobModel> addOrUpdateJobGrades(@PathVariable Long jobId, @RequestBody JobGradeModel jobGrades) {
        JobModel updatedJob = jobService.addJobgradeTojobs(jobId,jobGrades);
        if (updatedJob != null) {
            return ResponseEntity.ok(updatedJob);
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE request to remove a specific JobGrade
    @DeleteMapping("/{jobId}/grades/{gradeId}")
    public ResponseEntity<JobModel> removeJobGrade(@PathVariable Long jobId, @PathVariable Long gradeId) {
        JobModel updatedJob = jobService.removeJobGrade(jobId, gradeId);
        if (updatedJob != null) {
            return ResponseEntity.ok(updatedJob); // Return updated JobModel
        }
        return ResponseEntity.notFound().build(); // Return 404 if job not found
    }
    // Delete a job by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        try {
            jobService.deleteJob(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

