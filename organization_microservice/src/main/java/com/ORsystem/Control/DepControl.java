package com.ORsystem.Control;

import com.ORsystem.ORgdto.Depdto;
import com.ORsystem.Service.Depservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("api/departments")

public class DepControl {

    @Autowired
    private Depservice depservice;

    @GetMapping("/departments-with-employee-count")
    public List<Map<String, Object>> getDepartmentsWithEmployeeCount() {
        return depservice.getDepartmentsWithEmployeeCount();
    }
    @GetMapping
    public List<Depdto> getAllDepartments() {
        return depservice.getAllDepartments();
    }

    @GetMapping("/{id}")
    public Depdto getDepartmentById(@PathVariable Long id) {
        return depservice.getDepartmentById(id);
    }
@PutMapping("/{id}")
public Depdto updateDepartment(@PathVariable Long id, @RequestBody Depdto dto) {
        dto.setId(id);
        return depservice.updateDepartment(dto);
}
    @PostMapping
    public Depdto addDepartment(@RequestBody Depdto departmentDTO) {
        return depservice.addDepartment(departmentDTO);
    }
    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable Long id) {
        depservice.deleteDepartment(id);
    }
}