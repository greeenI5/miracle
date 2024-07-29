package com.green.miracle.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.green.miracle.domain.dto.PlanCreateDTO;
import com.green.miracle.domain.entity.EmployeeEntity;
import com.green.miracle.domain.entity.PerformancePlanEntity;
import com.green.miracle.domain.repository.EmployeeEntityRepository;
import com.green.miracle.domain.repository.PlanEntityRepository;
import com.green.miracle.security.CustomUserDetails;
import com.green.miracle.service.PlanService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlanServiceProcess implements PlanService {

    private final PlanEntityRepository repository;
    private final EmployeeEntityRepository empRepository;
    
    @Override
    public void findAllProcess(Model model) {
    	model.addAttribute("plans", repository.findAll());
    }
    
    @Transactional
    public void savePlan(PlanCreateDTO dto, CustomUserDetails user) {
        EmployeeEntity employee = empRepository.findByEmail(user.getEmail())
            .orElseThrow(() -> new RuntimeException("Employee not found"));
        PerformancePlanEntity plan = dto.toEntity();
        plan.setEmployee(employee); // Ensure employee is set
        repository.save(plan);
    }

    @Transactional
    public void saveFile(PlanCreateDTO dto, String filename, CustomUserDetails user) {
        PerformancePlanEntity plan = dto.toEntity();
        plan.setPerPoster(filename);
        plan.setEmployee(empRepository.findByEmail(user.getEmail())
            .orElseThrow(() -> new RuntimeException("Employee not found")));
        repository.save(plan);
    }

	@Override //id로 상세보기매핑
	public PerformancePlanEntity findPlanById(long planNo) {
		return repository.findById(planNo).orElse(null);
		
	}


}
