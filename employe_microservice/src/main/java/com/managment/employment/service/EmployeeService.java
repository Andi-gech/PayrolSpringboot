package com.managment.employment.service;

import com.managment.employment.dto.Departmentdto;
import com.managment.employment.model.Employee;
import com.managment.employment.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class EmployeeService {

    @Autowired
    private RestTemplate restTemplate;  // Use RestTemplate

    private EmployeeRepository employeeRepository;

    public Employee addEmployee(Employee employee) {
        // Define the URL for the department service
        String departmentServiceUrl = "http://192.168.0.103:4040/api/departments/" + employee.getDepartmentId();

        // Use RestTemplate to get the Department from the department microservice
        Departmentdto department = restTemplate.getForObject(departmentServiceUrl, Departmentdto.class);

        // Check if the department exists
        if (department != null) {
            // Set the department ID in the employee object
            employee.setDepartmentId(department.getId());

            // Save the employee in the repository and return the saved entity
            return employeeRepository.save(employee);
        } else {
            // Throw an exception if the department was not found
            throw new RuntimeException("Department not found for ID: " + employee.getDepartmentId());
        }
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    public int getEmployeesByDepartment(Long departmentId) {
        return employeeRepository.countByDepartmentId(departmentId);
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public boolean deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
        return true;
    }
}
