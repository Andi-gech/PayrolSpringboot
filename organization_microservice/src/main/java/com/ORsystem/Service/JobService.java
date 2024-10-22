package com.ORsystem.Service;

import com.ORsystem.Model.*;
import com.ORsystem.Repository.JobGradeRepository;
import com.ORsystem.Repository.JobsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    private JobsRepository jobsRepository;
    @Autowired
    private JobGradeRepository jobGradeRepository;


    @Autowired
    public JobService(JobsRepository jobsRepository) {
        this.jobsRepository = jobsRepository;
    }

    // Method to get all jobs
    public List<JobModel> getAllJobs() {
        return jobsRepository.findAll();
    }

    // Method to get a job by ID
    public Optional<JobModel> getJobById(Long jobId) {
        return jobsRepository.findById(jobId);
    }

    // Method to create a new job
    public JobModel createJob(JobModel jobModel) {
        return jobsRepository.save(jobModel);
    }

    // Method to update an existing job
    public JobModel updateJob(Long jobId, JobModel jobDetails) {
        return jobsRepository.findById(jobId)
                .map(existingJob -> {
                    existingJob.setDepartmentId(jobDetails.getDepartmentId());
                    existingJob.setJobName(jobDetails.getJobName());
                    existingJob.setDescription(jobDetails.getDescription());
                    return jobsRepository.save(existingJob);
                }).orElseThrow(() -> new RuntimeException("Job not found with id " + jobId));
    }

    // Method to delete a job by ID
    public void deleteJob(Long jobId) {
        jobsRepository.deleteById(jobId);
    }
    // Add or Update JobGrades for a JobModel
    public JobModel addOrUpdateJobGrades(Long jobId, List<JobGradeModel> jobGrades) {
        Optional<JobModel> optionalJob = jobsRepository.findById(jobId);
        if (optionalJob.isPresent()) {
            JobModel jobModel = optionalJob.get();
            jobModel.getJobGrades().addAll(jobGrades);
            return jobsRepository.save(jobModel);
        }
        return null;
    }
    public JobModel addJobgradeTojobs(Long jobId, JobGradeModel jobgrademodel) {
        Optional<JobModel> jobOptional = jobsRepository.findById(jobId);

        if (jobOptional.isPresent()) {
            JobModel job = jobOptional.get();

            // Ensure the PayGradeModel is saved first


            // Save the JobGradeModel
            JobGradeModel jobgrades = jobGradeRepository.save(jobgrademodel);

            // Print the saved JobGrade
            System.out.println("Saved JobGrade: " + jobgrades);

            // Add the JobGradeModel to the JobModel's list
            job.getJobGrades().add(jobgrades); // This should work now

            // Save the updated JobModel
            return jobsRepository.save(job);
        } else {
            throw new RuntimeException("Job not found");
        }
    }

    // Remove a JobGrade from a JobModel
    public JobModel removeJobGrade(Long jobId, Long jobGradeId) {
        Optional<JobModel> optionalJob = jobsRepository.findById(jobId);
        if (optionalJob.isPresent()) {
            JobModel jobModel = optionalJob.get();
            jobModel.getJobGrades().removeIf(grade -> grade.getId().equals(jobGradeId)); // Remove specific grade
            return jobsRepository.save(jobModel); // Save updated JobModel
        }
        return null; // Handle case if JobModel not found
    }
}

