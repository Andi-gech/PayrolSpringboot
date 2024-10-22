package com.ORsystem.Service;

import com.ORsystem.Model.JobGradeModel;

import com.ORsystem.Repository.JobGradeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobGradeService {

    private final JobGradeRepository jobGradeRepository;

    @Autowired
    public JobGradeService(JobGradeRepository jobGradeRepository) {
        this.jobGradeRepository = jobGradeRepository;
    }

    // Method to get all job grades
    public List<JobGradeModel> getAllJobGrades() {
        return jobGradeRepository.findAll();
    }

    // Method to get a job grade by ID
    public Optional<JobGradeModel> getJobGradeById(Long jobGradeId) {
        return jobGradeRepository.findById(jobGradeId);
    }

    // Method to create a new job grade
    public JobGradeModel createJobGrade(JobGradeModel jobGradeModel) {

        return jobGradeRepository.save(jobGradeModel);
    }

    // Method to update an existing job grade
    public JobGradeModel updateJobGrade(Long jobGradeId, JobGradeModel jobGradeDetails) {
        return jobGradeRepository.findById(jobGradeId)
                .map(existingJobGrade -> {
                    // Update properties using setters
                    if (jobGradeDetails.getJobGradeName() != null) {
                        existingJobGrade.setJobGradeName(jobGradeDetails.getJobGradeName());
                    }
                    if (jobGradeDetails.getDescription() != null) {
                        existingJobGrade.setDescription(jobGradeDetails.getDescription());
                    }
                    return jobGradeRepository.save(existingJobGrade);
                })
                .orElseThrow(() -> new RuntimeException("Job grade not found with id " + jobGradeId));
    }

    // Method to delete a job grade by ID
    public void deleteJobGrade(Long jobGradeId) {
        jobGradeRepository.deleteById(jobGradeId);
    }
}
