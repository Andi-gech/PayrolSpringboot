package com.managment.employment.repository;

import com.managment.employment.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Query method to find employees by departmentId
    int countByDepartmentId(Long departmentId);
}
