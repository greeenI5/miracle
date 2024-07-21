package com.green.miracle.service.impl;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.green.miracle.domain.dto.PlanCreateDTO;
import com.green.miracle.domain.entity.PerType;
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
	public void savePlan(String perContent, String employee, String perTitle, PerType perType, LocalDate startAt,
			LocalDate finishAt, String location, LocalDate writeAt, MultipartFile perPoster) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void save(String perType, MultipartFile file) {
		// TODO Auto-generated method stub
		
	}

}
