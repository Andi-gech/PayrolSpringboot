package com.PayrollService.Controller;

import com.PayrollService.Model.PayrollModel;
import com.PayrollService.Service.PayrollService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payrolls")
public class PayrollController {

    @Autowired
    private PayrollService payrollService;

    // Create a new payroll record
    @PostMapping
    public ResponseEntity<PayrollModel> createPayroll(@RequestBody PayrollModel payroll) {
        PayrollModel newPayroll = payrollService.createPayroll(payroll);
        return ResponseEntity.ok(newPayroll);
    }

    // Get payroll by ID
    @GetMapping("/{id}")
    public ResponseEntity<PayrollModel> getPayrollById(@PathVariable Long id) {
        PayrollModel payroll = payrollService.getPayrollById(id);
        return ResponseEntity.ok(payroll);
    }

    // Get all payroll records
    @GetMapping
    public ResponseEntity<List<PayrollModel>> getAllPayrolls() {
        List<PayrollModel> payrolls = payrollService.getAllPayrolls();
        return ResponseEntity.ok(payrolls);
    }

    // Get payrolls by employee ID
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<PayrollModel>> getPayrollsByEmployeeId(@PathVariable Long employeeId) {
        List<PayrollModel> payrolls = payrollService.getPayrollsByEmployeeId(employeeId);
        return ResponseEntity.ok(payrolls);
    }

    // Update payroll record by ID
    @PutMapping("/{id}")
    public ResponseEntity<PayrollModel> updatePayroll(@PathVariable Long id, @RequestBody PayrollModel payrollDetails) {
        PayrollModel updatedPayroll = payrollService.updatePayroll(id, payrollDetails);
        return ResponseEntity.ok(updatedPayroll);
    }

    // Delete payroll by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayroll(@PathVariable Long id) {
        payrollService.deletePayroll(id);
        return ResponseEntity.noContent().build();
    }
}
