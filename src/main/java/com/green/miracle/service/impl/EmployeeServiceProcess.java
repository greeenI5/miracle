package com.green.miracle.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.green.miracle.domain.dto.EmployeeSaveDTO;
import com.green.miracle.domain.repository.EmployeeEntityRepository;
import com.green.miracle.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceProcess implements EmployeeService{

	private final EmployeeEntityRepository repository;
	private final PasswordEncoder pe;
	
	//회원가입 - 롤에 따라 롤이 가변적으로 적용되야함
	@Override
	public void loginProcess(EmployeeSaveDTO dto) {
		repository.save(dto.toEntity(pe));
	}

}
