package com.green.miracle.service.impl;

import org.springframework.stereotype.Service;

import com.green.miracle.domain.dto.EmployeeUpdateDTO;
import com.green.miracle.domain.repository.EmployeeEntityRepository;
import com.green.miracle.service.MyPageService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MyPageServiceProcess implements MyPageService {
	
	private final EmployeeEntityRepository repository;
	
	public void updateProcess(EmployeeUpdateDTO dto) {
		
		repository.findByEmail(null);
		
	}

}
