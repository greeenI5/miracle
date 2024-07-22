package com.green.miracle.service.impl;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.green.miracle.domain.dto.EmployeeSaveDTO;
import com.green.miracle.domain.entity.EmployeeEntity;
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

	@Override
	// ID로 EmployeeEntity를 조회하는 메서드
    public EmployeeEntity findById(Long empNo) {
        Optional<EmployeeEntity> optionalEmployee = repository.findById(empNo);
        return optionalEmployee.orElse(null);  // 없을 경우 null 반환, 예외 처리도 가능
    }
}
