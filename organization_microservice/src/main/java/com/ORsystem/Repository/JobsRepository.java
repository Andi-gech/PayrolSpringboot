package com.ORsystem.Repository;

import com.ORsystem.Model.JobModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobsRepository extends JpaRepository<JobModel, Long> {
}

