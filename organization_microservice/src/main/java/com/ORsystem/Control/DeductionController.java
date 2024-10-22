package com.ORsystem.Control;


import com.ORsystem.Model.Deduction;
import com.ORsystem.Service.DeductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/deductions")
public class DeductionController {

    @Autowired
    private DeductionService deductionService;

    @GetMapping
    public List<Deduction> getAllDeductions() {
        return deductionService.getAllDeductions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Deduction> getDeductionById(@PathVariable Long id) {
        Optional<Deduction> deduction = deductionService.getDeductionById(id);
        return deduction.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Deduction createDeduction(@RequestBody Deduction deduction) {
        return deductionService.saveDeduction(deduction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Deduction> updateDeduction(@PathVariable Long id, @RequestBody Deduction newDeductionData) {
        Deduction updatedDeduction = deductionService.updateDeduction(id, newDeductionData);
        return ResponseEntity.ok(updatedDeduction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeduction(@PathVariable Long id) {
        deductionService.deleteDeduction(id);
        return ResponseEntity.noContent().build();
    }
}
