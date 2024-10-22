package com.ORsystem.Service;


import com.ORsystem.Model.Deduction;
import com.ORsystem.Repository.DeductionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeductionService {

    @Autowired
    private DeductionRepository deductionRepository;

    public List<Deduction> getAllDeductions() {
        return deductionRepository.findAll();
    }

    public Optional<Deduction> getDeductionById(Long id) {
        return deductionRepository.findById(id);
    }

    public Deduction saveDeduction(Deduction deduction) {
        return deductionRepository.save(deduction);
    }

    public Deduction updateDeduction(Long id, Deduction newDeductionData) {
        return deductionRepository.findById(id).map(deduction -> {
            deduction.setName(newDeductionData.getName());
            deduction.setPercentage(newDeductionData.getPercentage());
            return deductionRepository.save(deduction);
        }).orElseGet(() -> {
            newDeductionData.setId(id);
            return deductionRepository.save(newDeductionData);
        });
    }

    public void deleteDeduction(Long id) {
        deductionRepository.deleteById(id);
    }
}
