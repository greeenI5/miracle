package com.green.miracle.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.green.miracle.domain.dto.PlanCreateDTO;
import com.green.miracle.domain.repository.PlanEntityRepository;
import com.green.miracle.service.Planservice;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PlanServiceProcess implements Planservice{

	private final PlanEntityRepository repository; 
	
	@Override
	public void findAllProcess(Model model) {
		model.addAttribute("plans",repository.findAll());
		
	}

	@Override
	public void saveProcess(PlanCreateDTO dto) {
		repository.save(dto.toEntity());
		
	}

}
