package com.green.miracle.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.green.miracle.domain.dto.EmployeeUpdateDTO;
import com.green.miracle.domain.entity.DepartmentEntity;
import com.green.miracle.domain.entity.EmployeeEntity;
import com.green.miracle.domain.repository.EmployeeEntityRepository;
import com.green.miracle.security.CustomUserDetails;
import com.green.miracle.service.MyPageService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MyPageServiceProcess implements MyPageService {

	private final EmployeeEntityRepository repository;

	@Override
	@Transactional
	public void updateProcess(EmployeeUpdateDTO dto, CustomUserDetails user) {
		// 비밀번호를 가져온다고 가정하고, 이를 인코딩하여 저장하고자 할 때
		/* String encodedPassword = pe.encode(dto.getPassword()); */
		// 인코딩된 비밀번호를 DTO에 다시 설정할 수 있음 (예시로 적용)
		/* dto.setPassword(encodedPassword); */
		repository.findByEmail(user.getEmail()).orElseThrow().update(dto);
	}
	
	@Override
	public void readProcess(Model model, CustomUserDetails user) {
	    // 사용자의 이메일을 기반으로 EmployeeEntity를 조회합니다.
	    EmployeeEntity employee = repository.findByEmail(user.getEmail())
	            .orElseThrow(() -> new RuntimeException("Employee not found for email: " + user.getEmail()));

	    // EmployeeEntity에서 부서 정보를 가져옵니다.
	    DepartmentEntity department = employee.getDepartment();

	    // 부서 정보가 null인 경우 예외를 던집니다.
	    if (department == null) {
	        throw new RuntimeException("Department not found for employee with email: " + user.getEmail());
	    }

	    // 부서 코드를 기반으로 실제 부서 정보를 조회하고 모델에 추가합니다.
	    model.addAttribute("dep", department.getDepName());
	}



}
