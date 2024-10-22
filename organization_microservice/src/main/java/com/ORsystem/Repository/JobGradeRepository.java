package com.ORsystem.Repository;

import com.ORsystem.Model.JobGradeModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobGradeRepository extends JpaRepository<JobGradeModel, Long> {
}

